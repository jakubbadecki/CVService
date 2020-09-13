# CVService
CV Service is the REST service which allows creation and storage of CV data in the database.

## Prerequisites:
- Git
- Java 13
- Maven 3.6.3
- mongodb (https://github.com/mongodb/homebrew-brew)

## Building CVService
```mvn clean install```

## Running mongodb
Run mongodb with default configuration

```brew services start mongodb-community```
or
```mongod --config /usr/local/etc/mongod.conf```

## Running CVService
The executable jar file is located int the *'target'* directory.

```java -jar target/java -jar CVService-1.0.jar```

## Endpoints - Swagger
Swagger is an easy way to test and document the API of the service.
The swagger API documentation is under the following URL:
```http://localhost:8080/api/index.html```
To create user use not secured endpoint:
```POST http://localhost:8080/user/create```
All endpoints except from user creation (mentioned above) require Basic Authentication, to log in use `Authorize` button, or padlock icon next to the every endpoint.

