# Start with a base image containing Java runtime
FROM openjdk:17-jdk-alpine

# Add Maintainer Info
LABEL maintainer="huyddse6319@fpt.edu.vn"

# Add a volume pointing to /tmp
VOLUME /tmp

# Make port 8080 available to the world outside this container
EXPOSE 8181

# The application's jar file
ARG JAR_FILE=target/bmi_tracker-0.0.1-SNAPSHOT.jar

# Add the application's jar to the container
ADD ${JAR_FILE} bmi_tracker.jar

# Run the jar file 
ENTRYPOINT ["java","-jar","/bmi_tracker.jar"]