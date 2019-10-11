# Start with a base image containing Java runtime
FROM openjdk:8-jdk-alpine

# Add Maintainer Info
LABEL maintainer="rakeshv.com@gmail.com"

# Add a volume pointing to /tmp
VOLUME /tmp

# Make port 8080 available to the world outside this container
EXPOSE 8080

ARG JAR_FILE=target/cloudstack-elasticsearch-0.0.1.jar

# Add the application's jar to the container
ADD ${JAR_FILE} cloudstack-elasticsearch.jar

# Run the jar file
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/cloudstack-elasticsearch.jar"]
