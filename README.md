# Car Information API

## Description
This is a RESTful API built with Spring Boot. It performs CRUD operations on a SQLite database containing car information such as car name and type.

## Getting Started

### Prerequisites
- Java 8 or later
- Maven 3.0+

### Dependencies
This project uses the following dependencies:
- Spring Boot Starter Data JPA
- Spring Boot Starter Security
- Spring Boot Starter Web
- SQLite JDBC
- Hibernate Community Dialects
- Spring Boot DevTools
- Lombok
- Spring Boot Starter Test
- Spring Restdocs MockMvc
- Spring Security Test
- ModelMapper

### Installation
1. Clone the repository
    ```bash
    git clone https://github.com/FernandoFalcone-dev/Carros_API.git
    ```
2. Navigate to the project directory
    ```bash
    cd Carros_API
    ```
3. Build the project
    ```bash
    mvn clean install
    ```

## Usage
Run the project using the following command:
```bash
mvn spring-boot:run
```

## API Endpoints

`GET /api/v1/carros`: Fetch all cars<br/>
`GET /api/v1/carros/{id}`: Fetch a car by its ID<br/>
`GET /api/v1/carros/tipo/{tipo}`: Fetch cars by type<br/>
`POST /api/v1/carros`: Add a new car<br/>
`PUT /api/v1/carros/{id}`: Update a car by its ID<br/>
`DELETE /api/v1/carros/{id}`: Delete a car by its ID<br/>
## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

## License
MIT
