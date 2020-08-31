oc apply -f second-service-template.yaml
oc process container-second-service-template -p VERSION=$1 | oc create -f -