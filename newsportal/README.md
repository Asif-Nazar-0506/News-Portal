# News Portal Backend

A Spring Boot REST API for a news portal with automatic news generation and real-time updates.

## 🚀 Features

- **Real-time News Updates**: Poll for new articles without page reload
- **Admin News Generation**: Automated endpoint for creating news articles
- **RESTful API**: Clean and well-documented endpoints
- **In-memory Database**: H2 database for easy setup and testing
- **CORS Enabled**: Ready for frontend integration

## 📋 Prerequisites

- Java 17 or higher
- Maven 3.6+
- Git

## 🛠️ Installation & Setup

1. **Clone the repository**
```bash
   git clone https://github.com/YOUR_USERNAME/news-portal.git
   cd news-portal
```

2. **Build the project**
```bash
   mvn clean install
```

3. **Run the application**
```bash
   mvn spring-boot:run
```

4. **Access the application**
    - API Base URL: `http://localhost:8080/api`
    - H2 Console: `http://localhost:8080/h2-console`
        - JDBC URL: `jdbc:h2:mem:newsportal`
        - Username: `sa`
        - Password: (leave empty)

## 📡 API Endpoints

### Public Endpoints

#### Get All News
```http
GET /api/news
```

**Response:**
```json
[
  {
    "id": 1,
    "title": "Breaking: Major Development in Technology Industry",
    "content": "This is an automatically generated news article...",
    "author": "John Smith",
    "category": "Technology",
    "publishedAt": "2024-12-15T10:30:00"
  }
]
```

#### Get News Updates
```http
GET /api/news/updates?since=2024-12-15T10:00:00
```

**Parameters:**
- `since` (required): ISO 8601 timestamp - Returns only news published after this time

**Response:** Array of news articles published after the specified timestamp

### Admin Endpoints

#### Generate Single News Article
```http
POST /api/admin/generate-news
```

**Response:**
```json
{
  "success": true,
  "message": "News article generated successfully",
  "news": {
    "id": 1,
    "title": "Breaking: Major Development in Technology Industry",
    "content": "...",
    "author": "John Smith",
    "category": "Technology",
    "publishedAt": "2024-12-15T10:30:00"
  }
}
```

#### Generate Multiple News Articles
```http
POST /api/admin/generate-bulk?count=5
```

**Parameters:**
- `count` (optional): Number of articles to generate (1-20, default: 5)

**Response:**
```json
{
  "success": true,
  "message": "5 news articles generated successfully",
  "count": 5
}
```

## 🧪 Testing the API

### Using cURL
```bash
# Get all news
curl http://localhost:8080/api/news

# Get news updates since a specific time
curl "http://localhost:8080/api/news/updates?since=2024-12-15T10:00:00"

# Generate a single news article
curl -X POST http://localhost:8080/api/admin/generate-news

# Generate 5 news articles
curl -X POST "http://localhost:8080/api/admin/generate-bulk?count=5"
```

### Using Postman

1. Import the API endpoints into Postman
2. Set base URL to `http://localhost:8080`
3. Test each endpoint as described above

## 💡 Frontend Integration

### Initial Load
```javascript
fetch('http://localhost:8080/api/news')
  .then(response => response.json())
  .then(news => {
    displayNews(news);
    lastUpdateTime = new Date().toISOString();
  });
```

### Auto-Refresh (Polling)
```javascript
setInterval(() => {
  fetch(`http://localhost:8080/api/news/updates?since=${lastUpdateTime}`)
    .then(response => response.json())
    .then(newArticles => {
      if (newArticles.length > 0) {
        prependNews(newArticles);
        lastUpdateTime = new Date().toISOString();
      }
    });
}, 10000); // Poll every 10 seconds
```

## 🗄️ Database Schema

### News Table

| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| id | BIGINT | PRIMARY KEY, AUTO_INCREMENT | Unique identifier |
| title | VARCHAR(255) | NOT NULL | News title |
| content | VARCHAR(2000) | NOT NULL | News content |
| author | VARCHAR(255) | NOT NULL | Author name |
| category | VARCHAR(255) | NOT NULL | News category |
| published_at | TIMESTAMP | NOT NULL | Publication timestamp |
| created_at | TIMESTAMP | NOT NULL | Creation timestamp |

## 🏗️ Architecture
┌─────────────────┐
│   Controller    │ ← REST API Layer
├─────────────────┤
│    Service      │ ← Business Logic
├─────────────────┤
│   Repository    │ ← Data Access
├─────────────────┤
│   Database      │ ← H2 In-Memory
└─────────────────┘

## 🔧 Technologies Used

- **Spring Boot 4.0** - Application framework
- **Spring Data JPA** - Database access
- **H2 Database** - In-memory database
- **Lombok** - Boilerplate code reduction
- **Maven** - Dependency management

## 📝 Design Decisions

1. **Polling vs WebSocket**: Used polling approach for simplicity and broader compatibility
2. **In-Memory Database**: H2 for easy setup without external dependencies
3. **Random News Generation**: Implemented for testing without external APIs
4. **DTO Pattern**: Separates internal models from API responses
5. **CORS Enabled**: Allows frontend integration from any origin

## 🚀 Future Enhancements

- [ ] PostGreSQL/MySQL for production
- [ ] JWT authentication for admin endpoints
- [ ] Pagination for large datasets
- [ ] Full-text search capability
- [ ] Image upload support
- [ ] News categories management
- [ ] User comments and reactions

## 📄 License

This project is created for interview assessment purposes.

## 👤 Author

**Asif Nazar**
- LinkedIn: https://www.linkedin.com/in/asif-nazar-25a3a81a9/
- Email: asif.nazar0407@gmail.com

**Note**: This is a backend-only implementation. Frontend can be built separately using this API.