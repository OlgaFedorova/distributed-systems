oc delete all -l name=redis
oc delete -f deployments/redis-template.yaml
oc create -f deployments/redis-template.yaml
oc new-app --template="distributed-systems/container-redis-template"