package org.example;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.util.*;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DiscoveryResource {

    @GET
    public Response getApiInfo() {
        
        Map<String, Object> response = new HashMap<>();
        
        response.put("version", "v1");
        response.put("developer", "Your Name");
        
        Map<String, Object> endpoints = new HashMap<>();
        
        endpoints.put("rooms", "/api/v1/rooms");
        endpoints.put("sensors", "/api/v1/sensors");
        
        response.put("endpoints", endpoints);
        
        return Response.ok(response).build();
    }
}