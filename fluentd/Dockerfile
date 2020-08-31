# fluentd/Dockerfile
FROM fluent/fluentd:v1.6-debian-1
USER root
COPY ./conf/fluent.conf /fluentd/etc/fluent.conf
RUN ["gem", "install", "fluent-plugin-elasticsearch", "--no-document", "--version", "3.5.2"]
USER fluent
