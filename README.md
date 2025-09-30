# RAGChatStorageApplication

RAGChatStorageApplication is a chat storage service built with Spring Boot, PostgreSQL, and Docker. It provides APIs for storing and retrieving chat data with logging and monitoring via Elasticsearch, Logstash, and Kibana.

## Setup and Running Instructions

### Prerequisites
- Docker & Docker Compose installed
- Java 17 or higher installed (for building Spring Boot application)
- PostgreSQL image ready in Docker

### Running the Application

1. **Clone the repository:**
   ```bash
   git clone https://github.com/yourusername/ms-chat-storage.git
   cd ms-chat-storage
Set up environment variables:
Copy the .env.example file to .env:

    cp .env.example .env
Build and start the services using Docker Compose:

    docker-compose up --build
This will build the application, start all services (PostgreSQL, Elasticsearch, Logstash, Kibana, and the Spring Boot application), and map necessary ports.

Accessing the services:

API (Spring Boot app): http://localhost:8096

Kibana (for monitoring logs): http://localhost:5601

PostgreSQL: Port 5432

Elasticsearch: Port 9200 (API available for search)

Stopping the Services
To stop the services, run:

    
    docker-compose down
    Docker Compose Configuration
PostgreSQL: Database for storing chat data

Elasticsearch: Used for indexing and searching chat logs

Logstash: Collects and processes logs from Spring Boot app and sends them to Elasticsearch

Kibana: Interface for visualizing Elasticsearch data

Spring Boot: The main backend service exposing APIs for interacting with chat data

Available APIs
1. POST /api/chat
Description: Save a new chat message.

Request Body:
    
    {
      "sender": "user1",
      "receiver": "user2",
      "message": "Hello!",
      "timestamp": "2025-10-01T02:35:37"
    }
Response:

    {
      "id": 1,
      "sender": "user1",
      "receiver": "user2",
      "message": "Hello!",
      "timestamp": "2025-10-01T02:35:37"
    }
2. GET /api/chat/{id}
Description: Retrieve a chat message by its ID.

Response:
    
    {
      "id": 1,
      "sender": "user1",
      "receiver": "user2",
      "message": "Hello!",
      "timestamp": "2025-10-01T02:35:37"
    }
3. GET /api/chats
Description: Retrieve all chat messages.

Response:
    
    [
      {
        "id": 1,
        "sender": "user1",
        "receiver": "user2",
        "message": "Hello!",
        "timestamp": "2025-10-01T02:35:37"
      },
      {
        "id": 2,
        "sender": "user2",
        "receiver": "user1",
        "message": "Hi!",
        "timestamp": "2025-10-01T02:36:10"
      }
    ]
API Documentation (Swagger)
If Swagger is implemented for your APIs, you can access the API documentation at:

    http://localhost:8096/swagger-ui.html
Troubleshooting
If you face any issues with the database connection, ensure that the PostgreSQL service is up and running, and the credentials in your .env file are correct.

Check the logs of each service by running docker-compose logs <service_name> to diagnose any issues.

License
This project is licensed under the MIT License - see the LICENSE file for details.

### 3. **Swagger Integration for API Docs**

Since you're already using Spring Boot, you can easily integrate Swagger for automatic API documentation. Here's how you can set it up:

1. Add the following dependencies to your `pom.xml`:

   <dependency>
       <groupId>org.springdoc</groupId>
       <artifactId>springdoc-openapi-ui</artifactId>
       <version>1.6.6</version>
   </dependency>
Add the Swagger configuration class to enable Swagger:


    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springdoc.core.models.GroupedOpenApi;
    import org.springdoc.core.models.SwaggerUiConfigParameters;
    
    @Configuration
    public class SwaggerConfig {
        @Bean
        public GroupedOpenApi publicApi() {
            return GroupedOpenApi.builder()
                    .group("public")
                    .pathsToMatch("/api/**")
                    .build();
        }
    }
Once this is set up, you'll be able to access the API documentation at http://localhost:8096/swagger-ui.html.


Health Check Endpoints

To ensure the health of the application, we have integrated health check endpoints. These endpoints are useful to monitor the application's status, whether it is up and running, and if the necessary dependencies (like the database or other services) are available.

1. Basic Health Check

To check the general health of the application, you can use the following endpoint:

    GET /actuator/health


This will return a basic health check status of the application. It checks the overall health, including memory and disk space usage, JVM metrics, and more.

2. Database Health Check

To ensure that the application is able to connect to the database, the health check endpoint will provide status on the database connection:

    GET /actuator/health/db

3. Custom Health Check

You can add additional custom health check endpoints depending on the dependencies your application uses. For example, if you rely on a specific service (like Redis, or a third-party API), you can configure and add specific health checks for those as well.
