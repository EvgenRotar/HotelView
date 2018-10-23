#!/usr/bin/env bash
mvn clean install
cd  controller/
mvn jetty:run