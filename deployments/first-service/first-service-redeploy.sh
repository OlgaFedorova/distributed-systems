oc apply -f first-service-template.yaml
oc process container-first-service-template -p VERSION=$1 \
 TEST_ENV_VARIABLE='Hello from environment variables!'\
 TEST_CONF_MAP1='Hello from TEST_CONF_MAP1' \
 TEST_CONF_MAP2='Hello from TEST_CONF_MAP2' \
 TEST_SECRET_CONF_MAP1='SGVsbG8gZnJvbSBURVNUX1NFQ1JFVF9DT05GX01BUDE=' \
 TEST_SECRET_CONF_MAP2='SGVsbG8gZnJvbSBURVNUX1NFQ1JFVF9DT05GX01BUDI=' \
| oc apply -f -