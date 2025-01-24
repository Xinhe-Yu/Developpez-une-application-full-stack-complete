# Mdd App
MDD (Monde de Dév) is an French social networking platform to connect developers, facilitate their job search, and foster peer collaboration, while serving as a talent pool for companies seeking technical profiles.


## 1. Installation Guide

Clone the repository by:

`git clone https://github.com/Xinhe-Yu/Developpez-une-application-full-stack-complete`

or

`gh repo clone Xinhe-Yu/Developpez-une-application-full-stack-complete`

### Back-end
Ensure that you have :

- Java 17 (check it with `java -version`)
- Apache Maven 3.x.x (check it with `mvn -v`)
- MySQL (version 8.0.39 or higher is recommended, please also check if it is running and accessible, `sudo systemctl status mysql` for Linux, `brew services list | grep mysql` for macOS brew and directly open `services.msc` on Windows.)

Get into the back-end folder by:

`cd back`

1. Install Dependencies with Maven

`mvn clean install -DskipTests`

2. Configure environment variables

Locate the `env_template` file. Make a copy in the same folder and rename it `.env`. Add your MySQL root's username and password, and a Encryption Key for encrypting and decrypting the JWT.

3. Initialize the Database Schema With MySQL installed and running, load the initial database schema:

`mysql -u root -p < ./src/main/resources/schema.sql`

You will be prompted for the MySQL root password.

4. (Optional) If you want to populate your database with initial data, you can use the seeding feature:

`mysql -u root -p < ./src/main/resources/seeds.sql`

You will be prompted for the MySQL root password.

5. Run the application:

`mvn spring-boot:run`

The application will start on the port 8001 (localhost). should see output in the terminal indicating the application has started successfully.
After starting the application, you can view the API documentation at:

API Documentation: http://localhost:8001/api/swagger-ui/index.html

**Test endpoints with JWT Token :**

1. Get a JWT Token by endpoint POST /auth/register or POST /auth/login

2. Paste the JWT Token in the input box after clicking "Authorize" at the top right corner of the page

### Front-end
Ensure that you have :
- Node.js 18.19.1 or higher (check it with `node -v`)
- Angular 19.0.0 higher (check it with `ng version`)

Get out from the back-end folder and get into the front-end folder by:

`cd ../front`

1. Install dependencies:

`npm install`

2. (Optional) Run the application:

`ng serve`

Open your browser and navigate to http://localhost:4200/

## 2. Libraries and Packages Overview
### Back-end

#### Spring Security with OAuth2 and JWT
Provides robust authentication and authorization mechanisms for the entire application.

#### Spring Data JPA and MySQL Connector
Simplifies database interaction by managing Object-Relational Mapping (ORM) between Java objects and database tables.

#### Lombok and MapStruct
Reduces boilerplate code and simplify object mapping: Lombok generates common code like getters and setters through annotations; MapStruct automates the creation of type-safe bean mapping code.

#### Springdoc OpenAPI
Generates interactive API documentation.

#### Java-Dotenv
Protects sensitive information by enabling secure configuration with environment variables.

### Front-end
#### RxJS
Provides a powerful way to work with events, asynchronous calls, and data streams

#### Material UI
A comprehensive UI component library for Angular that offers a customizable theming system, with the deeppurple-amber theme, featuring a rich deep purple primary color and a warm amber accent color, creating a sophisticated and stylish look ideal for modern web applications.
