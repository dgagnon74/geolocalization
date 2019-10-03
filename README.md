# Geolocalization Service
This Micro-Service is responsible to handle requests related to positioning. 
Initial requirements are to provide the timezone given a position defined in longitude & latitude.
This service implement a basic access control and has a dependency on the ApiKey service 
(available at: https://github.com/dgagnon74/apikey)

## Interface Documentation
API Interface is described using Swagger. Which is available online : 
- Swagger console is available at http://localhost:8080/swagger-ui.html
- Swagger File is available at http://localhost:8080/v2/api-docs

## Build Instruction
Execute: _mvnw clean package_

## Running the service locally
Execute: `mvnw spring-boot:run`

## Docker Image Instruction
The Dockerfile is included within in the maven project so run:
_docker build -t airgraft-assigment/geolocalization-service ._

## Use the service
Please refer to swagger for details about documentation.

Note: In order to use that service you must first obtain an api_key from the Api Key servive.

Has example, for Montreal, Quebec, Canada the latitude is 45.508888, and the longitude is -73.561668,  

_curl --request GET "http://127.0.0.1:8080/api/localization/v1/timezones?latitude=45.508888&longitude=-73.561668&api_key=awe"_
The JSON response is :
_{"longitude":-73.561668,"latitude":45.508888,"timezoneId":"America/Toronto"}_

# Tradeoffs/decisions I made during development and their reasoning
Here are discussion points as requested in the assignment.

## Access Control Implementation
We did it using a filter which similar to the way it will implemented in the Api Gateway (using Zuul for example).
It uses the Simple Api Key Service we developed to validate the api key. 
Also Keys caching is provided that the Instance level in order to avoid calling the Api Key Service on each request. 
For production we can consider caching it at the cluster level instead. 
That said for the assigment we stick to a simple implementation.

## Data in memory
Timezone data are kept in memory and provided by the timeshape library. 
So a change in Timezone data requires a new deployment but the good thing is that those data don't change often ..
 
## We used a different URL than the one provided within the assigment
The assignment request us to use this url:  _http://api.domain.tld/time_zone?lat=48.8567&lng=2.348692&api_key=CONSUMER_API_KEY_

We decide that this was not an hard requirement, and use this instead url instead :
_http://127.0.0.1:8080/api/localization/v1/timezones?latitude=45.508888&longitude=-73.561668&api_key=awe_, because
- it ease api key verification (i.e.: everything under /api is protected, other like /swagger* or /healtcheck are not) 
- it introduces versioning

## Use of an existing model has example
We did start with Initializ to get a basic Maven Spring Boot working project, but all the rest have 
been added piece by piece. Of course having a complete working project has an example would allow 
me to move faster and but would have - in my opinion - failed the assigment.

## This service should be able to scale horizontally, please explain how this impacts your choices.
Since this implementation holds an in-memory database then the need to add a cache at the cluster level will essentially 
depends on the performance of the service ... which I haven't check but think is ok. 

The other concern is about API KEYs. Since our implementation depends on the Api Key Service for validation then 
caching keys at the cluster level will avoid each instance having to call the service when receiving a request with 
the same API KEY.

## Dependency between services
The Api Key Service URL is provided has localhost. For deployment, this must be change to a http://api-key.airgraft.xyz enabling auto-discovery.

## a suggestion how your solution could be deployed in production from a devops perspective
A CI Pipeline will create maven version of that API. Then will create a Docker Image to be deployed in a lower environment to 
be automatically tested.

Then deploying in production is done the same way. Automatically if your are supporting Continuous Delivery.

## When to stop the assigment
There still more work to make it better! But enough - I believe - in order for your
to have an understanding of what I know and taste of the code I can produce :-) 
Basically I timeboxed the assigment to few hours.
