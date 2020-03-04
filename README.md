# IQVIA

1. Create a mysql database named as iqiva_test
2. Open application.properties file at src/main/resources and update "username&password" with your database "username&password".
3. After updating application.properties file run "mvn clean install" in the terminal or import the project into your eclipse workspace and run the project as a Spring Boot Application.
4. After successfully running the project, please open this link: http://localhost:8080/swagger-ui.html
5. http://localhost:8080/api/scheduler/ pass two parameter "{\"deliveryTime\":\"03-12-2020 04:31:38\", \"message\" : \"Message to print\"}".
