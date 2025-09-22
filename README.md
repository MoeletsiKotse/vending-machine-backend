# Vending Machine API

## Project Description
This project is a Proof of Concept (POC) for a vending machine API built using Spring Boot. It provides endpoints for purchasing items, validating transactions, and managing inventory. The application uses an H2 in-memory database for simplicity.

## Features
- Purchase items from the vending machine.
- Validate payment and provide change.
- Update product inventory after a purchase.
- Handle exceptions for invalid requests.

## Assumptions
## How to Run
1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd vending-machine

## Build the project:
   ```bash
   ./mvnw clean install
   ```

## Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```

## Access the API:
1. **Base URL**: http://localhost:8080/api/purchase
2. **Swagger UI**: http://localhost:8080/swagger-ui.html

## Assumptions
1. **Authentication**: The application use Spring security with the username and password for authentication or authorization for simplicity.
2. **Inventory Management**: The inventory is preloaded and updated after each purchase.
3. **Change Calculation**: The vending machine provides change based on available cash inventory.
4. **Frontend Integration**: The API assumes a frontend application will handle user interactions.
5. **Validation**: Basic validations are implemented for purchase requests (e.g., sufficient funds, valid product quantities).

## Noteworthy Contributions
- **Exception Handling**: Custom exception handling is implemented using `@RestControllerAdvice` to return meaningful error messages.
- **CORS Configuration**: Global CORS configuration is added to allow frontend integration.
- **Transaction Management**: The `@Transactional` annotation ensures database consistency during purchases.
- **Logging**: Added logging for debugging and monitoring purposes.


## Additional Documentation

### Sequence Diagram
Refer to the ![database-designjpg.jpg](src%2Fmain%2Fresources%2Fdocs%2Fdatabase-designjpg.jpg) for a detailed sequence diagram illustrating the interaction between the components of the vending machine API.

### Solution Design
The solution is designed with flexibility and simplicity in mind, adhering to clean code principles and leveraging Spring Boot's modular architecture. For more details, refer to the ![Solution.jpg](src%2Fmain%2Fresources%2Fdocs%2FSolution.jpg).

### Database Design
The database schema is designed to support the vending machine's operations efficiently. Refer to the ![database-designjpg.jpg](src%2Fmain%2Fresources%2Fdocs%2Fdatabase-designjpg.jpg) for the ER diagram and schema details.