package br.com.ricas.infrastructure.http;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.File;

@Path("/")
@Produces(MediaType.TEXT_HTML)
public class RootResource {

    @GET
    public Response index() {
        return Response.ok(new File("META-INF/resources/index.html"))
                       .build();
    }
}
