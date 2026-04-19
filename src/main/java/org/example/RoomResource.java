package org.example;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.util.*;

@Path("/rooms")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RoomResource {

    public static Map<String, Room> getRoomsMap() {
        return rooms;
    }

    public static Map<String, Room> rooms = new HashMap<>();

    @GET //shows room
    public Response getRooms() {
        return Response.ok(rooms.values()).build();
    }

    @POST
    public Response createRoom(Room room) {

        if (room.getId() == null || room.getName() == null) {
            throw new WebApplicationException("Invalid room data", 400);
        }

        if (rooms.containsKey(room.getId())) {
            throw new WebApplicationException("Room already exists", 409);
        }

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
            throw new RoomNotEmptyException("Room has sensors");
        }

        rooms.remove(id);
        return Response.ok("Room deleted").build();
    }

    @PUT
    @Path("/{id}")
    public Response updateRoom(@PathParam("id") String id, Room updatedRoom) {

        Room existingRoom = rooms.get(id);

        if (existingRoom == null) {
            throw new NotFoundException("Room not found");
        }

        if (updatedRoom.getName() == null) {
            throw new WebApplicationException("Invalid room data", 400);
        }

        existingRoom.setName(updatedRoom.getName());
        existingRoom.setCapacity(updatedRoom.getCapacity());

        return Response.ok(existingRoom).build();
    }

}