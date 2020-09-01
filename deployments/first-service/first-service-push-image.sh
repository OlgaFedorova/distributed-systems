VERSION=$1

docker image tag olgafedorova/first-service:$VERSION default-route-openshift-image-registry.apps-crc.testing/distributed-systems/first-service:$VERSION
docker push default-route-openshift-image-registry.apps-crc.testing/distributed-systems/first-service:$VERSION
