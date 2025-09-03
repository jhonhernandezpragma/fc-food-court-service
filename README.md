<br />
<div align="center">
<h3 align="center">Food Curt Service</h3>
  <p align="center">
    This microservice provides full functionality for managing restaurants, dishes, and orders
  </p>
</div>

### Built With

* ![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white)
* ![Spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
* ![Gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white)
* ![PostgreSQL](https://img.shields.io/badge/PostgreSQL-00000F?style=for-the-badge&logo=postgresql&logoColor=white)

## Getting Started

To get a local copy up and running follow these steps.

## Installation

1. Clone the repo
2. Change directory
   ```sh
   cd fc-food-curt-service
   ```
3. Create a new database in postgreSQL called users
4. Update the database connection settings 
   ```yml
   # src/main/resources/application.yml   
   spring:
      datasource:
          username: <username>
          password: <password>
   ```

## Usage

1. Right-click the class FoodCurtServiceApplication and choose Run
2. Open [http://localhost:8082/swagger-ui/index.html](http://localhost:8081/swagger-ui/index.html) in your web browser for documentation (OpenApi UI)
3. Open [http://localhost:8082/v3/api-docs](http://localhost:8081/v3/api-docs) in your web browser for documentation (OpenApi JSON)

## Tests

- Right-click the test folder and choose Run tests with coverage


