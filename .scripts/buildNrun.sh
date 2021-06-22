#!/usr/bin/env bash

export JAVA_HOME=/usr/lib/jvm/java-15-oracle

#Cleanup
./stop_n_remove_containers.sh ;
./docker_total_cleanup.sh ;


#Stop on error
set -e ;

#Go to project root folder
cd .. ;


#Build msgProducer microservice and its docker image
cd msgProducer ;
./gradlew clean build docker -x test;
cd - ;

#Build slackConsumer microservice and its docker image
cd slackConsumer ;
./gradlew clean build docker -x test;
cd - ;

#Build smsConsumer microservice and its docker image
cd smsConsumer ;
./gradlew clean build docker -x test;
cd - ;

#Build emailConsumer microservice and its docker image
cd mailingConsumer ;
./gradlew clean build docker -x test;
cd - ;

#Launch all docker chain
docker-compose up ;
