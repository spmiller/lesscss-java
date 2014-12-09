#!/bin/bash

function moan(){
  echo -e "$1" 1>&2
  exit 1
}

cd "$( dirname "$0" )"

export version=${BUILD_NUMBER:-'dev.build'}
mvn clean package

function prepare_for_publish() {
    cd target
    mkdir s3
    cp *.jar s3
}

prepare_for_publish || moan 'Failed to prepare_for_publish'