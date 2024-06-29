#!/bin/bash

# Define ANSI color codes
DARK_ORANGE='\033[0;33;91m'  # Combining red and yellow
NC='\033[0m' # No Color

# Usage message
usage() {
    echo -e "${DARK_ORANGE}Usage:${NC} $0 /tmp/mt_jars/ /tmp/docker_imgaes/"
    exit 1
}

# Check if all required arguments are provided
if [ "$#" -ne 2 ]; then
    usage
fi

# Assign command-line arguments to variables
JAR_OUTPUT_DIR="$1"
DOCKER_OUTPUT_DIR="$2"
docker_image_list=""
[[ -d $DOCKER_OUTPUT_DIR ]] || mkdir $DOCKER_OUTPUT_DIR
# Create Docker container for each JAR file
for jar_file in $JAR_OUTPUT_DIR*.jar; do
    dockerfile="$JAR_OUTPUT_DIR$(basename $jar_file .jar).Dockerfile"
    echo "FROM openjdk:22-ea-21-slim-bullseye" > $dockerfile
    echo "WORKDIR /app" >> $dockerfile
    echo "COPY $(basename $jar_file) /app" >> $dockerfile
    echo "CMD java -jar /app/$(basename $jar_file .jar).jar" >> $dockerfile
    echo "Building Docker container for $jar_file"
    docker build --no-cache -t "$(basename $jar_file .jar | tr '[:upper:]' '[:lower:]')" -f $dockerfile $JAR_OUTPUT_DIR

    docker_image_list+="$(basename "$jar_file" .jar | tr '[:upper:]' '[:lower:]') \n"
done

docker image prune -f

echo -e "following images build: \n$docker_image_list"
