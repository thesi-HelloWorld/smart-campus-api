package org.example;

import jakarta.ws.rs.core.*;
import jakarta.ws.rs.ext.*;

@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Throwable> {
    
    @Override
    public Response toResponse(Throwable ex){
        return Response.status(500)
                .entity("Internal Server Error")
                .build();
    }
    
}
