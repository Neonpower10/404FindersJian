package nl.hu.sd.s2.sds2project2025404finders;

import jakarta.ws.rs.OPTIONS;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("{any: .*}")
public class OptionsResource {
    @OPTIONS
    public Response handlePreflight() {
        return Response.ok().build();
    }
}
