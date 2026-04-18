package org.example;

import jakarta.ws.rs.core.*;
import jakarta.ws.rs.ext.*;

@Provider
public class RoomNotEmptyExceptionMapper implements ExceptionMapper<RoomNotEmptyException> {
    @Override
    public Response toResponse(RoomNotEmptyException ex) {
        return Response.status(409)
                .entity(ex.getMessage())
                .build();
    }
}
