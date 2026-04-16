package org.example;
import jakarta.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/api/v1")
public class AppConfig extends ResourceConfig {
    public AppConfig(){
        packages("org.example");
    }
}