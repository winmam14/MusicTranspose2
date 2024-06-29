FROM debian:bullseye-slim
RUN apt-get update && apt-get install -y openjdk-21-jdk
COPY $jar_file /app.jar
CMD ['java', '-jar', '/app.jar']