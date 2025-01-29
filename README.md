
# Store Management API

A RESTful API for managing products in a store system, built with Spring Boot and connected to an H2 in-memory database. This project allows adding, modifying, finding, and deleting products, as well as securing certain endpoints with JWT authentication.

## Technologies Used:
- **Spring Boot** for building the web application
- **Spring Data JPA** for persistence in an H2 database
- **Spring Security** for authentication and protecting endpoints
- **JWT** for token-based authentication
- **H2 Database** for in-memory data storage
- **JUnit** and **Mockito** for unit testing

---

## Description:

This API provides basic functionality for managing products in a store. Users can add, find, update, and delete products. Authentication is handled via **JWT**, and security is implemented using **Spring Security**.

### Available Operations:
1. **GET /api/products** - Retrieve a list of all products
2. **POST /api/products** - Add a new product
3. **GET /api/products/{id}** - Retrieve a product by ID
4. **PUT /api/products/{id}** - Update an existing product
5. **DELETE /api/products/{id}** - Delete an existing product

### Authentication:
All endpoints are protected by **JWT** authentication. The **login** endpoint provides a JWT token that must be included in the **Authorization Header** for subsequent requests.

---

## Installation and Running:

### 1. Clone the project
Clone this repo to your local machine:
```bash
git clone https://github.com/razvandobre-costin/store-management-api.git
cd store-management-api
```

### 2. Install dependencies
Ensure you have **JDK 17** installed. You can check your Java version by running:
```bash
java -version
```

Install necessary dependencies:
```bash
mvn clean install
```

### 3. Run the application
Start the application with:
```bash
mvn spring-boot:run
```

Access the H2 console at:
```
http://localhost:8080/h2-console
```

**H2 Console Configuration:**
- **JDBC URL**: `jdbc:h2:mem:testdb`
- **Username**: `sa`
- **Password**: (leave it blank)

### 4. Testing Authentication
To obtain a JWT token, send a `POST` request to:
```
POST http://localhost:8080/auth/login
```
Body:
```json
{
  "username": "admin",
  "password": "password"
}
```
In the JSON response, you will receive the JWT token:
```json
{
  "token": "<JWT_TOKEN>"
}
```

### 5. Testing the API Endpoints
To test the endpoints, use **Postman** or **cURL** and include the JWT token in the `Authorization` header:
```bash
Authorization: Bearer <JWT_TOKEN>
```

Example request to get all products:
```bash
GET http://localhost:8080/api/products
Authorization: Bearer <JWT_TOKEN>
```

---

## Testing

The application includes unit and integration tests.

### Running tests:
To run the tests, use:
```bash
mvn test
```

---

## License

This project is licensed under the **MIT License** - see [LICENSE.md](LICENSE.md) for details.

---

## Contributing

Contributions are welcome! If you'd like to contribute, please open a **pull request** with your changes. Make sure that tests have passed before contributing.
