#!/usr/bin/env bash
rm -rf ./targetDeploy/*

gradle build
cp controller/build/libs/*.war -d targetDeploy/ROOT.war