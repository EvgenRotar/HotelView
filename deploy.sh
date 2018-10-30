#!/usr/bin/env bash
rm -rf ./targetDeploy/*

mvn clean install -DskipTests
cp ./controller/target/*.war -d targetDeploy/ROOT.war