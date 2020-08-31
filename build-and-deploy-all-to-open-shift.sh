oc delete all --all

sh deployments/redis-deploy.sh
sh deployments/jaeger-deploy.sh

VERSION=0.0.3
mvn clean install docker:build -Drevision=$VERSION

oc login -u developer -p developer https://api.crc.testing:6443
docker login -u developer -p $(oc whoami -t) default-route-openshift-image-registry.apps-crc.testing

docker image tag olgafedorova/first-service:$VERSION default-route-openshift-image-registry.apps-crc.testing/distributed-systems/first-service:0.0.2
docker push default-route-openshift-image-registry.apps-crc.testing/distributed-systems/first-service:$VERSION

docker image tag olgafedorova/second-service:$VERSION default-route-openshift-image-registry.apps-crc.testing/distributed-systems/second-service:0.0.2
docker push default-route-openshift-image-registry.apps-crc.testing/distributed-systems/second-service:$VERSION

docker image tag olgafedorova/first-service-with-sleuth:$VERSION default-route-openshift-image-registry.apps-crc.testing/distributed-systems/first-service-with-sleuth:0.0.2
docker push default-route-openshift-image-registry.apps-crc.testing/distributed-systems/first-service-with-sleuth:$VERSION

docker image tag olgafedorova/second-service-with-sleuth:$VERSION default-route-openshift-image-registry.apps-crc.testing/distributed-systems/second-service-with-sleuth:0.0.2
docker push default-route-openshift-image-registry.apps-crc.testing/distributed-systems/second-service-with-sleuth:$VERSION


cd deployments
cd first-service
sh first-service-deploy.sh $VERSION
cd ..

cd second-service
sh second-service-deploy.sh $VERSION
cd ..

cd first-service-with-sleuth
sh first-service-with-sleuth-deploy.sh $VERSION
cd ..

cd second-service-with-sleuth
sh second-service-with-sleuth-deploy.sh $VERSION
cd ..

cd ..
