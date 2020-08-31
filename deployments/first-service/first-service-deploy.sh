oc apply -f first-service-template.yaml
oc process container-first-service-template -p VERSION=$1 | oc create -f -