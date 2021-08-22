package me.nui.mp.database;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

@Path("/hello")
public class HelloResource {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response doGreeting(@QueryParam("name") String name) {
        System.out.println("name = " + name);
        if(name == null || name.isEmpty()) {
            return Response.ok("Hi").build();
        }

        return Response.ok("Hello " + name).build();
    }
}
