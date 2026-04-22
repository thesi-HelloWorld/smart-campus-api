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
