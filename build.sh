#!/usr/bin/env bash
function traverse {
  echo "Cleaning $1 in $(pwd)"
  cd "./$1"
  build
  cd ..
}

function build {
  jenv exec mvn clean install -U -DskipTests=true
}

services=(
  laganini-cloud-dependencies

  laganini-cloud-parent

  laganini-cloud-common
  laganini-cloud-validation

  laganini-cloud-exception
  laganini-cloud-logging
  laganini-cloud-metrics-parent
  laganini-cloud-test-suite

  laganini-cloud-rmi-parent

  laganini-cloud-storage-parent

  laganini-cloud-storage-audit-parent
)
for i in "${services[@]}"
do
   traverse $i
done
