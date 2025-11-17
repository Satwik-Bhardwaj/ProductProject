# **Product**

## **Overview**
Product is a Spring Boot–based RESTful API for managing product data. It provides endpoints to create, read, update, and delete products. The application follows a layered architecture consisting of controllers, services, repositories, DTOs, and centralized exception handling.  
Swagger is integrated for API documentation and testing.

---

## **Features**
- Create, read, update, and delete products (CRUD)
- Custom exception handling with meaningful error responses
- API documentation using Swagger (springdoc-openapi)
- Layered and maintainable package structure

---

## **Prerequisites**
- Java **17+**
- Maven
- A relational database (MySQL recommended)

---

## **Getting Started**

### **1. Clone the Repository**
```bash
git clone https://github.com/Satwik-Bhardwaj/ProductProject.git
cd product
```

---

### **2. Configure the Database**

Update `src/main/resources/application.yaml` with your database settings:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/productdb
    username: your_db_user
    password: your_db_password
    driver-class-name: com.mysql.cj.jdbc.Driver
```

Ensure your database server is running.

---

### **3. Optional: Using a `.env` File**
You may externalize database credentials:

**.env**
```
SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/productdb
DB_USERNAME=your_db_user
DB_PASSWORD=your_db_password
```

Use a library like `dotenv-java` or configure your IDE to load them into environment variables.

---

### **4. Build and Run the Project**

Using Maven Wrapper (recommended):

```bash
./mvnw clean install
./mvnw spring-boot:run
```

Using Maven directly:

```bash
mvn clean install
mvn spring-boot:run
```

---

## **Accessing Swagger UI**

After the application starts, open:

```
http://localhost:8081/swagger-ui/index.html
```

Default server port: **8081**  
You can change it via:

```yaml
server:
  port: 8081
```

---

## **Using the API**
- Use **Swagger UI** for interactive API testing.
- Alternatively, you can use Postman, Insomnia, or cURL for API calls.

---
