VERSION=0.0.3
mvn clean install docker:build -Drevision=$VERSION

oc login -u developer -p developer https://api.crc.testing:6443
docker login -u developer -p $(oc whoami -t) default-route-openshift-image-registry.apps-crc.testing

docker image tag olgafedorova/first-service:$VERSION default-route-openshift-image-registry.apps-crc.testing/distributed-systems/first-service:$VERSION
docker push default-route-openshift-image-registry.apps-crc.testing/distributed-systems/first-service:$VERSION

docker image tag olgafedorova/second-service:$VERSION default-route-openshift-image-registry.apps-crc.testing/distributed-systems/second-service:$VERSION
docker push default-route-openshift-image-registry.apps-crc.testing/distributed-systems/second-service:$VERSION

docker image tag olgafedorova/first-service-with-sleuth:$VERSION default-route-openshift-image-registry.apps-crc.testing/distributed-systems/first-service-with-sleuth:$VERSION
docker push default-route-openshift-image-registry.apps-crc.testing/distributed-systems/first-service-with-sleuth:$VERSION

docker image tag olgafedorova/second-service-with-sleuth:$VERSION default-route-openshift-image-registry.apps-crc.testing/distributed-systems/second-service-with-sleuth:$VERSION
docker push default-route-openshift-image-registry.apps-crc.testing/distributed-systems/second-service-with-sleuth:$VERSION

oc process container-first-service-template -p VERSION=$VERSION | oc apply -f -
oc process container-second-service-template -p VERSION=$VERSION | oc apply -f -
oc process container-first-service-with-sleuth-template -p VERSION=$VERSION | oc apply -f -
oc process container-second-service-with-sleuth-template -p VERSION=$VERSION | oc apply -f -
