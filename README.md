# 🚀 Spring Boot Products API

---

## 📖 Overview

This project consists of building a RESTful API using **Spring Boot**, focused on products, users, providers, persistence, queries, and dynamic filtering.

The application uses:

- ✨ **Spring Web** for REST endpoints
- ✨ **Spring Data JPA** for persistence
- ✨ **H2 Database** as an in-memory database
- ✨ **Lombok** to reduce boilerplate code
- ✨ **MapStruct** for object mapping
- ✨ **DTOs** for data transfer between layers
- ✨ **Custom exceptions** for error handling
- ✨ **Criteria API** for dynamic queries

📂 Initial data files:

```bash
src/main/resources/Products.json
src/main/resources/Users.json
🧩 Functional Requirements
🌐 1. Spring Web
📦 1.1 Entities

The project must include the following entities:

Product
User
Provider

Each entity must:

Match the structure of the provided JSON files
Use JPA annotations to map the entity to database tables
Include validations where necessary
🔗 Entity Relationships
Each Product must be associated with one Provider
One Provider can have many Product
Each Product must be associated with one User
One User can have many Product

Required relationships:

@ManyToOne
@OneToMany
⚠️ Unique Constraint

There cannot be more than one product with the same name for the same provider.

This must be guaranteed using a unique constraint in the entity and/or database level.

🎯 1.2 ProductController

Base path:

@RequestMapping("/api/v1/web/products")
🔸 Required Endpoints

➤ 1. Load products from JSON

Receive Products.json as a parameter
Process and parse the file
Save the list of products into the H2 database

➤ 2. Get all products

Return all products stored in H2
Used to verify that the insertion was successful

➤ 3. Calculate total product price

Receive Products.json as a parameter
Read the products from the file
Return the total sum of product prices

➤ 4. Count products by category

Receive Products.json as a parameter
Filter products by the specified category
Return the total number of matching products
👤 1.3 UserController

Base path:

@RequestMapping("/api/v1/web/users")
🔸 Required Endpoints

➤ 1. Load users from JSON

Receive Users.json as a parameter
Process and parse the file
Save the list of users into the H2 database

➤ 2. Get all users

Return all users stored in H2
Used to verify that the insertion was successful
🗄️ 2. Spring Data
🔎 2.1 ProductQueryController

Base path:

@RequestMapping("/api/v1/query/products")

These endpoints must be implemented using @Query.

🔸 Required Endpoints

➤ 1. Find product by name

Search and return a product using its name

➤ 2. Count products by category

Return the total number of products stored in the database for a given category

➤ 3. Find product by name and category

Search a product using both name and category

➤ 4. Get product prices ordered ascending

Return all prices for a product
Consider all providers
Sort prices from lowest to highest

➤ 5. Get lowest price product by name

Receive a product name
Return the product with the lowest price for that name
Must be implemented using @Query
Aggregation or subquery must be used
⚙️ 2.2 ProductCustomController

Base path:

@RequestMapping("/api/v1/custom/products")

These endpoints must be implemented using Criteria API and CriteriaBuilder.

🔸 Required Endpoints

➤ 1. Filter products by query parameters

Filter products using optional query params:
name
category

➤ 2. Get products below a price

Receive a price parameter
Return products with a price lower than the provided value

➤ 3. Filter by expiration date range

Receive:
startDate
endDate
Return products whose expiration date is between both dates
Sort results by id in descending order

➤ 4. Get products by user email

Receive a user email
Return all products associated with that user
Optional filters:
category
brand

➤ 5. Get products from the oldest users

Receive a number N as a parameter
Find the N oldest users in the system
Return the products related to those users

➤ 6. Advanced dynamic search with pagination

Implement an endpoint with optional filters:
name
category
brand
price range
expirationDate range
providerId
Include pagination parameters:
page
size
Include sorting parameters:
sortBy
direction
Use Criteria API to dynamically build predicates
Return a paginated Page result
🧪 Database

The project uses H2 Database as an in-memory database to store products, users, and providers during execution.

🏗️ Project Architecture

The application must follow a layered architecture:

Controller
Service
Repository
DTO
Mapper
Entity
Exception
✅ Evaluation Criteria

The project must include:

✔ Required configuration in the properties file
✔ Spring Data JPA for persistence operations
✔ Lombok to reduce repetitive code
✔ MapStruct for object mapping
✔ H2 Database configuration
✔ Entity and input validations
✔ Layered architecture
✔ Custom exception handling
✔ DTO classes
✔ All required controllers and endpoints
✔ Postman collection exported as collection.json
✔ Application running on port 8090

Example port configuration:

server.port=8090