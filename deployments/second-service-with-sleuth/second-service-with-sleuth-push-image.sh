VERSION=$1

docker image tag olgafedorova/second-service-with-sleuth:$VERSION default-route-openshift-image-registry.apps-crc.testing/distributed-systems/second-service-with-sleuth:$VERSION
docker push default-route-openshift-image-registry.apps-crc.testing/distributed-systems/second-service-with-sleuth:$VERSION
