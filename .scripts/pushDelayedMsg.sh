#!/usr/bin/env bash

curl -vvv -XPOST \
-H "Content-type: application/json" \
-H "Accept: application/json" \
--data '{"msg":"message delay for slack","fileList": ["MTExMTExMTExMTExMTExMTExMTExMTExMTExMTExMTEx", "MDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAw"], "createdAt": "2020-12-28T21:16:56.358673Z", "createdBy": "admin"}' \
"http://localhost:8081/api/v1/message/msg_with_delay?retry=3&delayms=5000"

#http://localhost:8081/api/v1/message/slack

#http://localhost:8081/api/v1/message/email

#http://localhost:8081/api/v1/message/sms




