#!/usr/bin/env bash

#curl -X POST -H 'Content-type: application/json' --data '{"text":"Hellocdgfgfdgfdgfvxcv, World!"}' $SLACK_URL


curl -vvv -XPOST  -H 'Content-type: application/json' http://localhost:8084/testsms