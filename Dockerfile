# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-alpine

# Set the working directory to /app
WORKDIR /app

# Copy the JAR file into the container at /app
COPY target/taskManagement-0.0.1-SNAPSHOT.jar /app/taskManagement-0.0.1-SNAPSHOT.jar

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Run the JAR file
CMD ["java", "-jar", "-Dserver.address=0.0.0.0", "-Dserver.port=8080", "taskManagement-0.0.1-SNAPSHOT.jar"]
