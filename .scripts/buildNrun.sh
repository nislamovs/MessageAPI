#!/usr/bin/env bash


#Cleanup
./stop_n_remove_containers.sh ;
./docker_total_cleanup.sh ;


#Stop on error
set -e ;

#Go to project root folder
cd .. ;


#Build msgProducer microservice and its docker image
cd msgProducer ;
./gradlew clean build docker ;
cd - ;


#Launch all docker chain
docker-compose up ;
