VERSION=0.0.3
mvn clean install docker:build -Drevision=$VERSION

oc login -u developer -p developer https://api.crc.testing:6443
docker login -u developer -p $(oc whoami -t) default-route-openshift-image-registry.apps-crc.testing

cd deployments

cd first-service
sh first-service-push-image.sh $VERSION
cd ..

#cd second-service
#sh second-service-push-image.sh $VERSION
#cd ..
#
#cd first-service-with-sleuth
#sh first-service-with-sleuth-push-image.sh $VERSION
#cd ..
#
#cd second-service-with-sleuth
#sh second-service-with-sleuth-push-image.sh $VERSION
#cd ..

cd ..

