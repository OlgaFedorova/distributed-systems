oc apply -f first-service-with-sleuth-template.yaml
oc process container-first-service-with-sleuth-template -p VERSION=$1 | oc apply -f -