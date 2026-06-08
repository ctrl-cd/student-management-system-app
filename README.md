# Student Management System App

A REST API for managing students, courses, and admins, built with **Java**, **Spring Boot**, **Spring Security (JWT)**,
and **MySQL**.

## Tech Stack

- Java 21
- Spring Boot 3.4.5 (Web, JPA, Security, Validation, Actuator)
- MySQL
- MapStruct, Lombok, Password4j (Argon2)
- SpringDoc OpenAPI (Swagger UI)

## Prerequisites

- JDK version = 21
- MySQL version = 8.x

## Project Setup

### 1. Clone the repository

### 2. Create the MySQL database

student_management_system_dev_db

Tables are created automatically on first run of the project.

### 3. Configure environment variables

The app reads configuration from **environment variables** (not from committed `.env` files). Set them in your Intellij
Idea run configuration, before starting the app.

After open the project in Intellij Idea Add .env, .end.dev, .end.prod files in the "Edit Configurations" ->
Applicatoin -> Select application -> Environment variables = Select .env and .end.dev for the development environment

Please find the .env files for the project at the following link:
https://drive.google.com/file/d/19vGdrKnP6qZLxHNSu3Rsd2nUhFyIxZee/view?usp=sharing
https://drive.google.com/file/d/1eGY5VCbrpyXdv7p28wgykqMzXOza17R_/view?usp=sharing
https://drive.google.com/file/d/1zfMB6hfvhQAlpK7BwL2Hcw6LIT7wGDkW/view?usp=sharing

### 4. Open and Run the project

Open IntelliJ IDEA -> Select File from menu -> Open and choose the project -> Select project folder -> Click on
pom.xml = Let IDEA import the Maven project.

Run the application:

First time = Open
src/main/java/com/studentmanagementsystem/student_management_system_app/StudentManagementSystemAppApplication.java file
in the Intellij Idea -> Click on first run button(icon)

Second time = Click on run button(icon)

### 5. Explore the API

Please find the Postman Collection and Postman Environment links attached:
https://drive.google.com/file/d/1CJysyS8Ivv6tK5ATnOuREMUxjWDZZthX/view?usp=sharing
https://drive.google.com/file/d/1PyuBiukcl3kM4FOnwMN77ErfqCR4eVmo/view?usp=sharing

OR

Open Swagger UI for interactive documentation and to try endpoints with the bearer token.

Swagger API Documentation Link (Run the project locally to access):

http://localhost:8080/v3/api-docs

Swagger API UI Link (Run the project locally to access):

http://localhost:8080/swagger-ui/index.html


