oc delete all -l name=second-service-with-sleuth
oc delete -f deployments/second-service-with-sleuth-template.yaml
oc create -f deployments/second-service-with-sleuth-template.yaml
oc new-app --template="distributed-systems/container-second-service-with-sleuth-template"