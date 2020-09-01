VERSION=$1

docker image tag olgafedorova/second-service:$VERSION default-route-openshift-image-registry.apps-crc.testing/distributed-systems/second-service:$VERSION
docker push default-route-openshift-image-registry.apps-crc.testing/distributed-systems/second-service:$VERSION
