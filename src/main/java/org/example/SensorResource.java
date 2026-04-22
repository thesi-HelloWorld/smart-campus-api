package org.example;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.util.*;

@Path("/sensors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorResource {

    public static Map<String, Sensor> getSensorsMap() {
        return sensors;
    }

    private static Map<String, Sensor> sensors = new HashMap<>();
    private static Map<String, Room> rooms = RoomResource.getRoomsMap();

    @POST
    public Response createSensor(Sensor sensor) {

        Room room = rooms.get(sensor.getRoomId());

        if (room == null) {
            throw new LinkedResourceNotFoundException("Room not found");
        }

        sensors.put(sensor.getId(), sensor);
        room.getSensorIds().add(sensor.getId());

        return Response.status(Response.Status.CREATED).entity(sensor).build();
    }

    @GET
    public Response getSensors(@QueryParam("type") String type) {

        List<Sensor> result = new ArrayList<>();

        for (Sensor s : sensors.values()) {
            if (type == null || s.getType().equalsIgnoreCase(type)) {
                result.add(s);
            }
        }

        return Response.ok(result).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteSensor(@PathParam("id") String id) {

        Sensor sensor = sensors.get(id);

        if (sensor == null) {
            throw new NotFoundException("Sensor not found");
        }

        if (sensor.getRoomId() != null) {
            Room room = rooms.get(sensor.getRoomId());

            if (room != null && room.getSensorIds() != null) {
                room.getSensorIds().remove(id);
            }
        }

        sensors.remove(id);

        return Response.ok("Sensor deleted").build();
    }

    @Path("/{sensorId}/readings")
    public SensorReadingResource getSensorReadingResource(@PathParam("sensorId") String sensorId) {
        return new SensorReadingResource(sensorId);
    }

}