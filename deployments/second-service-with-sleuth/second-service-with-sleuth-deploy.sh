oc apply -f second-service-with-sleuth-template.yaml
oc process container-second-service-with-sleuth-template -p VERSION=$1 | oc create -f -