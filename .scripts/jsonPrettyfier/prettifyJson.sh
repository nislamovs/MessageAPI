#!/usr/bin/env bash

cat ../../.docker/rabbitmq/volumes/etc/definitions.json | jq . > ./definitions.json