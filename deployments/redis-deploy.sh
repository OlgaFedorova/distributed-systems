oc apply -f deployments/redis-template.yaml
oc process container-redis-template | oc create -f -