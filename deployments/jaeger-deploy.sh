oc delete all -l name=jaeger
oc delete -f deployments/jaeger-template.yaml
oc create -f deployments/jaeger-template.yaml
oc new-app --template="distributed-systems/container-jaeger-template"