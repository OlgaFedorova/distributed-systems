VERSION=$1

docker image tag olgafedorova/first-service-with-sleuth:$VERSION default-route-openshift-image-registry.apps-crc.testing/distributed-systems/first-service-with-sleuth:$VERSION
docker push default-route-openshift-image-registry.apps-crc.testing/distributed-systems/first-service-with-sleuth:$VERSION
