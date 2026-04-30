# CupidLinks

CupidLinks is a Java web application designed as a safe and culturally aware dating platform for young adults in Nepal. The system allows users to register, log in, manage profiles, discover other users, save favourites, send interests, and view matches. Administrators can manage users, review reports, handle support tickets, and monitor dashboard analytics.

## Technologies Used

- Java
- Jakarta Servlet and JSP
- MySQL
- JDBC
- HTML and CSS
- Maven
- BCrypt password hashing

## Main Features

- User registration and login
- Password encryption using BCrypt
- Session-based authentication
- Role-based authorization for admin and users
- Admin dashboard with user management
- User profile creation and update
- Profile photo upload
- Discover/search/filter users
- Save favourite profiles
- Send interest and create matches
- Report users and manage support requests
- Error pages for invalid or unauthorized access

## Project Structure

```text
src/main/java/com/cupidlinks
+-- controller
+-- dao
+-- filter
+-- model
+-- service
+-- util

src/main/webapp
+-- css
+-- images
+-- WEB-INF/views
```

## Database Setup

1. Open MySQL Workbench.
2. Import the SQL file from the `database` folder.
3. Run the schema/dump script to create the `cupidlinks_db` database.
4. Update the database username and password in:

```text
src/main/java/com/cupidlinks/util/DBConnection.java
```

## Running the Project

1. Open the project in IntelliJ IDEA.
2. Configure a JDK and Apache Tomcat server.
3. Make sure MySQL is running.
4. Build and deploy the Maven WAR project.
5. Open the application in the browser through the configured Tomcat URL.

## Coursework Notes

This project follows MVC architecture using Servlet controllers, JSP views, service classes, DAO classes, model classes, utility classes, and filters. The system includes validation, exception handling, session management, cookies, file upload handling, and role-based access control.
