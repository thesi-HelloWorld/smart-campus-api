package org.example;

import jakarta.ws.rs.core.*;
import jakarta.ws.rs.ext.*;

@Provider
public class LinkedResourceNotFoundExceptionMapper implements ExceptionMapper<LinkedResourceNotFoundException> {
    
    @Override
    public Response toResponse(LinkedResourceNotFoundException ex){
        return Response.status(422)
                .entity(ex.getMessage())
                .build();
    }
}
