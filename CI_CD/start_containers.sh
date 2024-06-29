#!/bin/bash

# Function to check if the provided argument is a valid integer
is_valid_integer() {
    # Use regular expression to check if the argument consists of digits only
    if [[ $1 =~ ^[0-9]+$ ]]; then
        return 0  # Return 0 if argument is a valid integer
    else
        return 1  # Return 1 otherwise
    fi
}

# Check if the number of arguments provided is correct
if [ "$#" -ne 2 ]; then
    echo "Usage: $0 <DB_WRITER_INSTANCES> <DB_READER_INSTANCES>"
    exit 1
fi

# Check if the provided arguments are valid integers
if ! is_valid_integer "$1" || ! is_valid_integer "$2"; then
    echo "Error: Arguments must be valid integers."
    exit 1
fi


DB_WRITER_INSTANCES="$1"
DB_READER_INSTANCES="$2"

# Function to get the newest image for a given service
get_newest_image() {
    image_name=$1
    # Use docker images command to list available images, filter based on the image name pattern, and sort by creation time to get the newest one
    newest_image=$(docker images --format '{{.Repository}}:{{.Tag}}' | grep "$image_name" | sort -r | head -n 1)
    echo "$newest_image"
}

# Get the newest image for each service
gateway_service_newest=$(get_newest_image gateway-service)
registration_discovery_service_newest=$(get_newest_image registration-discovery-service)
monitoring_server_newest=$(get_newest_image monitoring-server)
database_writer_newest=$(get_newest_image database-writer)
database_reader_newest=$(get_newest_image database-reader)

#define Network
#docker network create --subnet=172.18.0.0/16 my_network

# Start registration-discovery-service container with internal port 8080 mapped to external port 8001
docker run -d --name registration-discovery-service --network host -p 8761:8761 "$registration_discovery_service_newest"

# Start monitoring-server container with internal port 8080 mapped to external port 8002
docker run -d --name monitoring-server --network host -p 8080:8080 "$monitoring_server_newest"

# Start gateway-service container with internal port 8080 mapped to external port 8003
docker run -d --name gateway-service --network host -p 8181:8181 "$gateway_service_newest"

# Prompt for password
read -sp 'Enter db_writer password: ' DB_WRITER_PASSWORD
echo # To move to a new line after password input
# Start database-writer containers with random external port
for ((i=1; i<=$DB_WRITER_INSTANCES; i++))
do
    docker run -d --name database-writer-$i --network host -P -e DB_PASS="$DB_WRITER_PASSWORD" "$database_writer_newest"
done

# Prompt for password
read -sp 'Enter db_reader password: ' DB_READER_PASSWORD
echo # To move to a new line after password input
# Start database-reader containers with random external port
for ((i=1; i<=$DB_READER_INSTANCES; i++))
do
    docker run -d --name database-reader-$i --network host -P -e DB_PASS="$DB_READER_PASSWORD" "$database_reader_newest"
done
