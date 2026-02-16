# Rewards API

A Spring Boot REST API that calculates customer reward points based on transactions from the last 3 months.

---

## 🚀 Features

- Calculate reward points per customer
- Monthly reward aggregation
- Total reward points calculation
- RESTful API design
- Global exception handling
- H2 in-memory database
- Unit tests for Controller and Service
- Clean layered architecture

---

## 🛠 Tech Stack

- Java 17+
- Spring Boot 3+
- Spring Web
- Spring Data JPA
- H2 Database (In-memory)
- Lombok
- JUnit 5
- Mockito
- Maven

---

## 📌 Reward Calculation Logic

For each transaction:

- 2 points for every dollar spent over $100
- 1 point for every dollar spent between $50 and $100

### Example:

| Transaction Amount | Points |
|-------------------|--------|
| $120              | 90     |
| $80               | 30     |
| $40               | 0      |

---

## 📂 Project Structure

```
com.rewards
 ├── controller
 │     └── RewardController
 ├── service
 │     ├── RewardService
 │     └── RewardCalculator
 ├── repository
 │     ├── CustomerRepository
 │     └── TransactionRepository
 ├── entity
 │     ├── Customer
 │     └── Transaction
 ├── dto
 │     └── RewardResponseDTO
 ├── exception
 │     ├── CustomerNotFoundException
 │     └── GlobalExceptionHandler
 └── RewardsApiApplication
```

---

## ⚙️ Configuration

### application.yaml

```yaml
spring:
  datasource:
    url: jdbc:h2:mem:rewardsdb
    driver-class-name: org.h2.Driver
    username: sa
    password:

  h2:
    console:
      enabled: true

  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    defer-datasource-initialization: true

  sql:
    init:
      mode: always
```

---

## 🗄 Sample Data

Create a file:

```
src/main/resources/data.sql
```

```sql
INSERT INTO customer (id, name) VALUES (1, 'Srilatha');

INSERT INTO transactions (id, customer_id, amount, transaction_date)
VALUES (1, 1, 120, '2026-01-10');

INSERT INTO transactions (id, customer_id, amount, transaction_date)
VALUES (2, 1, 80, '2026-02-05');
```

---

## ▶️ How to Run

### 1️⃣ Clone Repository

```bash
git clone https://github.com/your-username/rewards-api.git
```

### 2️⃣ Navigate to project

```bash
cd rewards-api
```

### 3️⃣ Run Application

```bash
mvn spring-boot:run
```

Or run `RewardsApiApplication` from IDE.

---

## 🌐 API Endpoint

### Get Rewards for Customer

```
GET /api/rewards/{customerId}
```

Example:

```
GET http://localhost:8080/api/rewards/1
```

---

## ✅ Successful Response

```json
{
  "customerId": 1,
  "monthlyPoints": {
    "JANUARY": 90,
    "FEBRUARY": 30
  },
  "totalPoints": 120
}
```

---

## ❌ Error Response (Customer Not Found)

```json
{
  "timestamp": "2026-02-16T11:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Customer not found: 1"
}
```

---

## 🧪 Running Tests

```bash
mvn test
```

Includes:

- Controller test using MockMvc
- Service test using Mockito

---

## 🔎 H2 Console

Open in browser:

```
http://localhost:8080/h2-console
```

JDBC URL:
```
jdbc:h2:mem:rewardsdb
```

Username:
```
sa
```

Password:
(empty)
```

---

## 🏗 Architecture Overview

- Controller layer handles REST endpoints
- Service layer contains business logic
- Repository layer interacts with database
- DTO layer isolates API response
- Exception handling via @RestControllerAdvice

---


## 👩‍💻 Author

Developed as a Spring Boot REST API demonstration project.

---


