mvn clean install docker:build
docker push olgafedorova/first-service
docker push olgafedorova/first-service-with-sleuth
docker push olgafedorova/second-service
docker push olgafedorova/second-service-with-sleuth
sh deploy-all-to-open-shift.sh