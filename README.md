# ND035-P02-VehiclesAPI-Project

Project repository for JavaND Project 2, where students implement a Vehicles API using Java and Spring Boot that can communicate with separate location and pricing services.

## Instructions

Check each component to see its details and instructions. Note that all three applications
should be running at once for full operation. Further instructions are available in the classroom.

- [Vehicles API](vehicles-api/README.md)
- [Pricing Service](pricing-service/README.md)
- [Boogle Maps](boogle-maps/README.md)

## Dependencies

The project requires the use of Maven and Spring Boot, along with Java v11.

## Run the applications
Note that the Boogle Maps, Pricing Service and Vehicles API applications must all be running for all operations to perform correctly, although they should be able to launch on their own.

You can either use these in separate windows in your favorite IDE, or use the below commands:

``$ mvn clean package`` (run this in each directory containing the separate applications)

Boogle Maps: ``$ java -jar target/boogle-maps-0.0.1-SNAPSHOT.jar``
The service is available by default on port 9191. You can check it on the command line by using $ curl http://localhost:9191/maps\?lat\=20.0\&lon\=30.0

Pricing Service: ``$ java -jar target/pricing-service-0.0.1-SNAPSHOT.jar``

Vehicles API: ``$ java -jar target/vehicles-api-0.0.1-SNAPSHOT.jar``

When the Swagger API documentation is implemented, it should be available at: http://localhost:8080/swagger-ui.html
