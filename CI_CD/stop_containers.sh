#!/bin/bash
docker container stop $(docker container ls -a | awk '{print $1}' | grep -v CONTAINER)
docker rm -f $(docker ps -aq)