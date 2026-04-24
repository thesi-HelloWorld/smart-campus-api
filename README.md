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

### Part 2
**Question  01**

Returning just the room IDs reduces the size of the response, which would make initial API call faster and save network bandwidth. However, it introduces the “N+1 problem” for the client, as the client might require more information such as room name or capacity. Thus, increasing latency and adding complexity to the client-side logic. Conversely, returning full room objects increases the playload size but provides all the required information in a single response This will decrease the number of API calls and make it simpler for clients to process and display data efficiently. In most scenarios, returning full objects is more convenient for the client despite the slightly higher bandwidth usage.

**Question 02**

The DELETE operation is idempotent. Idempotency guarantees that performing the same request many times results in the same final state on the server. In this particular implementation, if a client deletes a room (for ex: DELETE /rooms/CS-101), the very first request successfully removes the room from the map and returns a 200 OK. If the request is performed again, the server checks the map, fails and returns a 404 Not Found Exception. The state of the system will remain unchanged, the room is still deleted. Thus, the DELETE operation satisfies the definition of idempotency.

