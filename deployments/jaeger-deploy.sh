oc apply -f deployments/jaeger-template.yaml
oc process container-jaeger-template | oc create -f -