# smart-campus-api

## 1. API Overview
The smart campus API is a RESTful web service to manage university infrastructure. Built using **Java 17 & JAX-RS (Jersey)**, running on **Grizzly HTTP server**.

### Key features include:
* Full CRUD operations for Rooms and Sensors.
* Deep nesting for sensor reading history using the Sub-Resource Locator pattern.
* Advanced exception mapping to provide clean, semantic HTTP error responses.
* In-memory data structures strictly simulating a database environment.
------

## 2. Build and launch instructions
**Requirements**
* Java 17 or higher installed
* Apache Maven installed

**Step-by-step guide to launch**
1. Clone repository - Open termnal and clone project to local of your device.
    git clone https://github.com/thesi-HelloWorld/smart-campus-api
    cd smart-campus-api

2. Build project - Compile java code and download necessary dependencies using Maven.
   nvm clean install

3. Launch server - Using an IDE such as NetBeans, simple open "Main.java" file and run it.

4. Verify the server is running - Open Postman or web browser which navigates to the discovery endpoint: http://localhost:8080/api/v1/  
------

## 3. Sample cURL commands
5 example commands are demonstrated successful interactions with different parts of the API. Run these in a new terminal window while the server is running:

**1. View API Discovery Info (root)**

curl -X GET http://localhost:8080/api/v1/


**2. Creating a new room**

curl -X POST http://localhost:8080/api/v1/rooms \
-H "Content-Type: application/json" \
-d '{"id": "CS-101", "name": "Computer Science Lab", "capacity": 30}'


**3. Register a new sensor to the same room**

curl -X POST http://localhost:8080/api/v1/sensors \
-H "Content-Type: application/json" \
-d '{"id": "TEMP-001", "type": "Temperature", "status": "ACTIVE", "roomId": "CS-101"}'


**4. Filter sensor by type**

curl -X GET "http://localhost:8080/api/v1/sensors?type=Temperature"


**5. Add a reading to sensor**

curl -X POST http://localhost:8080/api/v1/sensors/TEMP-001/readings \
-H "Content-Type: application/json" \
-d '{"id": "reading-123", "timestamp": 1713980000, "value": 22.5}'


**6. Retrieve a specific room's details**

curl -X GET http://localhost:8080/api/v1/rooms/CS-101


**7. Delete a sensor**

curl -X DELETE http://localhost:8080/api/v1/sensors/TEMP-001


**8. View all historical readings for different sensors**

curl -X GET http://localhost:8080/api/v1/sensors/TEMP-001/readings

----

## 4. Conceptual report
### Part 1
**Question 01**

Originally JAX-RS uses a “per-request” lifecycle by default, meaning an instance of a resource class is created for every incoming HTTP request and destroyed after a response has been sent. Thus any instance variable such as a regular HashMap or ArrayList would be removed, resulting in data loss. To maintain data across multiple requests must be declared as static. 
However, as many requests may be accessed at the same moment, this increases the risk of race conditions. As a solution we acquire thread-safe collections or synchronization blocks to ensure data consistency.

**Question 02**

HATEOS transforms an API from a static directory into a dynamic state machine. It is considered a hallmark of advanced RESTful design as the server embeds URls to related resources and actions directly within JSON response. This gives advantages to the client developers as the need for hardcoding specific URLs based on static documentation. If the backend developer wants to change the URL structure, the application will not break, as endpoints are resolved at runtime directly from server’s responses.
