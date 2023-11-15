# Task Management System

## Overview

Welcome to the Task Management System, a robust Java Spring Boot application designed to streamline task creation, assignment, and tracking. This README provides essential technical information about the project, explaining its architecture, design decisions, and how to get started.

# Microservices Architecture

The project embraces a Microservices Architecture, promoting modularity, scalability, and resilience. Each aspect of task management, from creation to assignment, is encapsulated within independent, loosely coupled microservices.

![mermaid diagram](https://i.imgur.com/LX3k9FC.png)

## Project Structure

The project follows a modular structure to enhance maintainability and scalability:

- **`src/main/java`**: Java source code.
- **`src/main/resources`**: Configuration files.
- **`docker-compose.yml`**: Docker Compose configuration.


## Technologies Used

- **Java Spring Boot**: A powerful and flexible framework for building Java-based enterprise applications.
- **Docker**: Containerization for easy deployment and scalability.
- **RabbitMQ**: Asynchronous messaging for efficient task assignment.
- **PostgreSQL Database**: Utilizes a relational database for storing task information.
- **Asynchronous Processing with @Async from Spring Boot**: Implements concurrency and parallelism for efficient task processing.
- **NGINX**: Efficiently handles load balancing and acts as a reverse proxy for the microservices.


## Getting Started

To run the Task Management System locally, follow these steps:

### Building and Docker

Dockerize the application for simplified deployment:

1. Build the jar file: `mvn clean install`
2. Build the Docker image: `docker build -t task-management-system .`

### Running with full system with Docker

1. Run the docker containers: `docker compose up`
2. Access the application at [http://localhost:8080](http://localhost:8080)

### Running application locally

1. Put PostgreSQL and RabbitMQ containers up: `docker-compose up postgres rabbitmq` 
2. Run the Spring Boot application: `./mvnw spring-boot:run`
3. Access the application at [http://localhost:8080](http://localhost:8080)

## Asynchronous Communication with RabbitMQ

RabbitMQ is integrated into the system to handle asynchronous communication. Task assignment events are processed efficiently, ensuring responsiveness.

## Concurrency and Parallelism

Task processing is optimized through concurrent mechanisms. Utilizing Spring's `@Async` to parallelize task processing, enhancing system performance.

## Design and Architecture Patterns

The project adheres to SOLID principles, applying design patterns such as Factory, Repository, and Observer where necessary. DTOs facilitate clean separation of concerns for API communication.


## License

This project is licensed under the [MIT License](LICENSE.md). Feel free to use, modify, and distribute the code as per the terms of the license.

Happy coding! 🚀
