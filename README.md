****Assignment: RECIPE MANAGEMENT****


**[DESCRIPTION]:** This is a sample spring-boot project to manage user recipes. 
                   Contains ReST APIs in order to Create, Get, Update and Delete recipe from the database.


**Requirements**
1) Java 8
2) Apache Maven 3.5.0 or higher


**How to Run?**

**Running as a Packaged Application**:
1) java -jar target/managingrecipes-0.0.1-SNAPSHOT.jar
	
**Using the Maven Plugin**:
1) Clone the project
2) Build the project: **mvn clean install**
3) Run spring boot application with: **mvn spring-boot:run**

**Used tools and frameworks:**

1) Spring Boot (Including the dependencies)
2) Junit
3) H2 Database: To access the Database use the below url:  **http://localhost:8081/h2-console/** 
4) Maven
5) Slf4J
6) SWAGGER: Swagger configured.  url : **http://localhost:8081/swagger-ui.html**
7) Mockito: Mockito framework used for unit testing.

**Functionalities:**

1) View all the recipe present in database.
2) Add a new recipe.
3) Delete the recipe by its ID.
4) Search recipe.
5) Add ingredients to existing recipe.
6) Delete ingredients of existing recipe.

**Flow Architecture:**

The user makes an HTTP request (GET, POST, DELETE etc.)
The HTTP request is forwarded to the Controller. The controller maps the request. It processes the handles and calls the server logic.
The business logic is performed in the Service layer. The spring boot performs all the logic over the data of the database which is mapped to the spring boot model class through Java Persistence Library(JPA) and stored in the in memory H2 database.
The JSON content is returned as Response from the controller.


![image](https://user-images.githubusercontent.com/100201305/196172240-dd60ddcf-a191-40c9-b44d-2c98ce3c47c3.png)

