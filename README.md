# Geolocalization Service
This Micro-Service is responsible to handle requests related to longitude & latitude. 
Initial requirements are to provide the timezone related to a position defined in longitude & latitude.

# Interface Documentation
Interface is described using Swagger. Note that the Swagger file is used to generate the data model and has an input 
for the corresponding API Gateway (enabling Authentication & QoS support).

http://localhost:8080/swagger-ui.html

# Build
mvnw clean install

# Run
mvnw clean install



# Use
For example, for Montreal, Quebec, Canada latitude is 45.508888, and the longitude is -73.561668
So using CURL

curl --request GET "http://127.0.0.1:8080/api/localization/v1/timezones?latitude=45.508888&longitude=-73.561668&api_key=awe"


JSON returned:
{"longitude":-73.561668,"latitude":45.508888,"timezoneId":"America/Toronto"}

# Development
tradeoffs/decisions you made during development and their reasoning
how to run your solution locally as a developer
a suggestion how your solution could be deployed in production from a devops perspective
v


http://api.domain.tld/time_zone?lat=48.8567&lng=2.348692&api_key=CONSUMER_API_KEY
