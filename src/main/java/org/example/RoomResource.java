package org.example;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.util.*;

@Path("/rooms")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RoomResource {

    private static Map<String, Room> rooms = new HashMap<>();

    private static Map<String, Sensor> sensors = new HashMap<>();

    @GET //shows room
    public Response getRooms() {
        return Response.ok(rooms.values()).build();
    }

    @POST //adds room
    public Response createRoom(Room room) {
        rooms.put(room.getId(), room);
        return Response.status(Response.Status.CREATED).entity(room).build();
    }

    @GET
    @Path("/{id}")
    public Response getRoom(@PathParam("id") String id) {
        Room room = rooms.get(id);

        if (room == null) {
            throw new NotFoundException("Room not found");
        }

        return Response.ok(room).build();
    }

    @DELETE //removes room
    @Path("/{id}")
    public Response deleteRoom(@PathParam("id") String id) {
        Room room = rooms.get(id);

        if (room == null) {
            throw new NotFoundException("Room not found");
        }

        if (!room.getSensorIds().isEmpty()) {
            throw new WebApplicationException("Room has sensors", 409);
        }

        rooms.remove(id);
        return Response.ok("Room deleted").build();
    }

    @POST
    @Path("/{id}/sensors")
    public Response addSensor(@PathParam("id") String roomId, Sensor sensor) {

        Room room = rooms.get(roomId);

        if (room == null) {
            throw new NotFoundException("Room not found");
        }

        sensors.put(sensor.getId(), sensor);
        room.getSensorIds().add(sensor.getId());

        return Response.status(Response.Status.CREATED).entity(sensor).build();
    }

    @GET
    @Path("/{id}/sensors")
    public Response getSensors(@PathParam("id") String roomId) {

        Room room = rooms.get(roomId);

        if (room == null) {
            throw new NotFoundException("Room not found");
        }

        List<Sensor> roomSensors = new ArrayList<>();

        for (String sensorId : room.getSensorIds()) {
            roomSensors.add(sensors.get(sensorId));
        }

        return Response.ok(roomSensors).build();
    }
}