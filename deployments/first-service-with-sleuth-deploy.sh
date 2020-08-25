oc delete all -l name=first-service-with-sleuth
oc delete -f deployments/first-service-with-sleuth-template.yaml
oc create -f deployments/first-service-with-sleuth-template.yaml
oc new-app --template="distributed-systems/container-first-service-with-sleuth-template"