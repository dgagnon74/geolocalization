# Geolocalization Service
This Micro-Service is responsible to handle requests related to longitude & latitude. 
Initial requirements are to provide the timezone given a position defined in longitude & latitude.

This service implement a basic access control and has a dependency on the ApiKey service.

## Interface Documentation
API Interface is described using Swagger. Which is available online: 
- Swagger console is available at http://localhost:8080/swagger-ui.html
- Swagger File is available at http://localhost:8080/v2/api-docs

## Build Instruction
Execute: _mvnw clean package_

## Running the service locally
Execute: `mvnw spring-boot:run`

## Docker Image Instruction
The Dockerfile is included within in the maven project so run:
_docker build -t airgraft-assigment/geolocalization-service_ .

## Use the service
Please refer to swagger for details about documentation, but has example for Montreal, Quebec, Canada 
latitude is 45.508888, and the longitude is -73.561668, using 

So using CURL: _curl --request GET "http://127.0.0.1:8080/api/localization/v1/timezones?latitude=45.508888&longitude=-73.561668&api_key=awe"_
The JSON response is :
_{"longitude":-73.561668,"latitude":45.508888,"timezoneId":"America/Toronto"}_

# Tradeoffs/decisions I made during development and their reasoning
The good thing about geoloc data is that it is quite stable so 


## Access Control Implementation
We did it using a filter

## Use a different URL than the one provided within the assigment
The assignment request to use this url:  _http://api.domain.tld/time_zone?lat=48.8567&lng=2.348692&api_key=CONSUMER_API_KEY_

We decide that this was not an hard requirement, and 
- choose a path in order to ease api key verification (i.e.: everything under /api is protected, other like /swagger* or /healtcheck is not ..) 
- choose a path that introduce versioning

## When to stop
There still more work to make it better! But enough - I believe - in order for your
to have an understanding of what I know and taste of the code I can produce :-) 

## Use of existing models has example
We did start with Initializ to get a basic Maven Spring Boot working project, but all the rest have 
been added piece by piece. Of course having a complete working project has an example would allow 
me to move faster and but would have - in my opinion - failed the assigment.




## This service should be able to scale horizontally, please explain how this impacts your choices.
bla bla bla
