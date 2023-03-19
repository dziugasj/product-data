# Product date CRUD application

Prerequisites:

- JAVA 17 JDK
- Maven

How to:

- How to build: `mvn clean package spring-boot:repackage`
- How to run: `java -jar target/demo-0.0.1-SNAPSHOT.jar`

# Remaining tasks

Write unit tests
Complete remaining integration tests
Complete missing endpoint: 4. Exposes a REST endpoint to allow for updating of any of the fields of a single product
Fix issue with @PostConstruct and integration tests
Endpoint namings can be improved: /products/byName and /products/byCategory

# Rest endpoints

### By name

GET http://localhost:8080/api/v1/products/byName?name=Handmade Plastic Fish

### By category (with inStock)

GET http://localhost:8080/api/v1/products/byCategory?category=Books&inStock=false

### By category (without inStock)

GET http://localhost:8080/api/v1/products/byCategory?category=Books

### Update stock level

PUT http://localhost:8080/api/v1/products?name=Handmade Plastic Fish&stockLevel=44


