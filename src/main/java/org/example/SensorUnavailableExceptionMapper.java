package org.example;

import jakarta.ws.rs.core.*;
import jakarta.ws.rs.ext.*;

@Provider
public class SensorUnavailableExceptionMapper implements ExceptionMapper<SensorUnavailableException> {
    
    @Override
    public Response toResponse(SensorUnavailableException ex) {
        return Response.status(403)
                .entity(ex.getMessage())
                .build();
    }
}
