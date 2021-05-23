#!/usr/bin/env bash

#curl -X POST -H 'Content-type: application/json' --data '{"text":"Hellocdgfgfdgfdgfvxcv, World!"}' $SLACK_URL

curl -vvv -XPOST \
-H "Content-type: application/json" \
-H "Accept: application/json" \
--data '{"msg":"message for slack","fileList": ["MTExMTExMTExMTExMTExMTExMTExMTExMTExMTExMTEx", "MDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAw"], "createdAt": "2020-12-28T21:16:56.358673Z", "createdBy": "admin"}' \
http://localhost:8081/api/v1/message/slack
