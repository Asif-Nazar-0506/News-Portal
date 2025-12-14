news-portal/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── newsportal/
│   │   │           ├── NewsPortalApplication.java
│   │   │           ├── controller/
│   │   │           │   ├── NewsController.java
│   │   │           │   └── AdminController.java
│   │   │           ├── service/
│   │   │           │   └── NewsService.java
│   │   │           ├── repository/
│   │   │           │   └── NewsRepository.java
│   │   │           ├── model/
│   │   │           │   └── News.java
│   │   │           └── dto/
│   │   │               └── NewsDTO.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/
├── pom.xml
└── README.md

Features
1. News Display Endpoints

GET /api/news - Get all news articles (ordered by newest first)
GET /api/news/updates?since={timestamp} - Get only news published after a specific timestamp

2. Admin Endpoints

POST /api/admin/generate-news - Generate a single random news article
POST /api/admin/generate-bulk?count={number} - Generate multiple news articles (1-20)

3. Auto-Refresh Support
   The frontend can poll the /api/news/updates endpoint at regular intervals with the last timestamp to get only new articles without reloading the entire list.
   How to Run

Prerequisites

JDK 17 or higher
Maven 3.6+


Build the project

bash   mvn clean install

Run the application

bash   mvn spring-boot:run

Access the application

API Base URL: http://localhost:8080/api
H2 Console: http://localhost:8080/h2-console



API Usage Examples
Get all news
bashcurl http://localhost:8080/api/news
Get news updates since a timestamp
bashcurl "http://localhost:8080/api/news/updates?since=2024-12-15T10:00:00"
Generate a single news article (Admin)
bashcurl -X POST http://localhost:8080/api/admin/generate-news
Generate 5 news articles (Admin)
bashcurl -X POST "http://localhost:8080/api/admin/generate-bulk?count=5"


Database Schema
News Table
ColumnTypeDescriptionidBIGINTPrimary key (auto-increment)titleVARCHAR(255)News titlecontentVARCHAR(2000)News contentauthorVARCHAR(255)Author namecategoryVARCHAR(255)News categorypublished_atTIMESTAMPPublication timestampcreated_atTIMESTAMPCreation timestamp
Technologies Used

Spring Boot 3.2.0
Spring Data JPA
H2 Database (in-memory)
Lombok
Maven

Key Implementation Details

Polling-based updates: The frontend polls the /api/news/updates endpoint with a timestamp parameter to fetch only new articles.
Efficient querying: Uses JPA queries to fetch only articles published after a specific timestamp.
Auto-generation: Admin endpoints can generate random news articles with realistic data.
CORS enabled: All endpoints have CORS enabled for easy frontend integration.
In-memory database: Uses H2 database for quick setup and testing.

Notes for Production
For a production environment, consider:

Replace H2 with PostgreSQL/MySQL
Implement proper authentication/authorization for admin endpoints
Add input validation and error handling
Implement WebSocket or Server-Sent Events (SSE) for real-time updates instead of polling
Add pagination for large news datasets
Implement caching strategies
Add logging and monitoring