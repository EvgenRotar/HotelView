#!/usr/bin/env bash

current=`pwd`
docker run --rm -d --name hotel-view --hostname hotel-view -p 8080:8080 -v ${current}/targetDeploy:/var/lib/jetty/webapps jetty