#!/usr/bin/env bash
native-image --no-fallback --no-server \
  -cp ./build/libs/jsonpath-graalvm-1.0-SNAPSHOT.jar \
  -H:Name=jsonpath-graalvm-test -H:Class=org.example.App \
  --report-unsupported-elements-at-runtime