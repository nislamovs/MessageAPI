#!/usr/bin/env bash

curl -vvv -XPOST \
-H "Content-type: application/json" \
-H "Accept: application/json" \
--data '{"msg":"x2324234yz","fileList": ["MTExMTExMTExMTExMTExMTExMTExMTExMTExMTExMTEx", "MDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAw"], "createdAt": "2020-12-28T21:16:56.358673Z", "createdBy": "admin"}' \
http://localhost:8081/api/v1/message/all
