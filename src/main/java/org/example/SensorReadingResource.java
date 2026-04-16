package org.example;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.util.*;

public class SensorReadingResource {

    private static Map<String, List<SensorReading>> readings = new HashMap<>();
    private static Map<String, Sensor> sensors = SensorResource.getSensorsMap();

    private String sensorId;

    public SensorReadingResource(String sensorId) {
        this.sensorId = sensorId;
    }

    @GET
    public Response getReadings() {
        List<SensorReading> list = readings.getOrDefault(sensorId, new ArrayList<>());
        return Response.ok(list).build();
    }

    @POST
    public Response addReading(SensorReading reading) {

        if (!sensors.containsKey(sensorId)) {
            throw new NotFoundException("Sensor not found");
        }

        readings.putIfAbsent(sensorId, new ArrayList<>());
        readings.get(sensorId).add(reading);

        sensors.get(sensorId).setCurrentValue(reading.getValue());

        return Response.status(Response.Status.CREATED).entity(reading).build();
    }
}
