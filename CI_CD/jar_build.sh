#!/bin/bash
CUSTOM_INFO_COLOR='\033[0;33;91m'
CUSTOM_ERROR_COLOR='\033[0;31m'
# Check if all required arguments are provided
if [ "$#" -ne 3 ]; then
    echo -e "${CUSTOM_ERROR_COLOR}Usage: ./jar_build.sh https://git-iit.fh-joanneum.at/swd22-startup-project/musictranspose2.git /tmp/mt_repo/ /tmp/mt_jars/"
    exit 1
fi

# Assign command-line arguments to variables
REPO_URL="$1"
CLONE_DIR="$2"
JAR_OUTPUT_DIR="$3"

# Clone the repository using username and access token for authentication
[[ -d $CLONE_DIR ]] && echo -e "[${CUSTOM_INFO_COLOR}INFO\033[0m] removing Folder $CLONE_DIR" && rm -rf $CLONE_DIR
git clone $REPO_URL $CLONE_DIR

# Check if the cloning was successful
if [ $? -ne 0 ]; then
    echo -e "${CUSTOM_ERROR_COLOR}Failed to clone repository."
    exit 1
fi

# Navigate to the /backend directory
cd $CLONE_DIR/Backend/musicTranspose/
# Checkout the main branch
git checkout main

# Check if checkout was successful
if [ $? -ne 0 ]; then
    echo -e "${CUSTOM_ERROR_COLOR}Failed to switch to the main branch."
    exit 1
fi

echo -e "[${CUSTOM_INFO_COLOR}INFO\033[0m] in branch" && git branch --show-current

# Build the Java project
echo -e "[${CUSTOM_INFO_COLOR}INFO\033[0m] Start Maven build"
mvn clean package -DskipTests

# Check if the build was successful
if [ $? -ne 0 ]; then
    echo -e "${CUSTOM_ERROR_COLOR}Failed to build Java project."
    exit 1
fi

[[ -d $JAR_OUTPUT_DIR ]] || mkdir $JAR_OUTPUT_DIR
[[ -d $JAR_OUTPUT_DIR ]] && echo -e "[${CUSTOM_INFO_COLOR}INFO\033[0m] removing files in $JAR_OUTPUT_DIR" && rm -rf $JAR_OUTPUT_DIR/*
# Find all JAR files in the modules' target directories and copy them to the output directory
find . -name "*.jar" -type f -exec cp {} $JAR_OUTPUT_DIR \;

# Check if any JAR files were found and copied
if [ $? -eq 0 ]; then
    echo -e "[${CUSTOM_INFO_COLOR}INFO\033[0m] All JAR files copied to $JAR_OUTPUT_DIR."
else
    echo -e "${CUSTOM_ERROR_COLOR}No JAR files found or failed to copy."
    exit 1
fi

exit 0