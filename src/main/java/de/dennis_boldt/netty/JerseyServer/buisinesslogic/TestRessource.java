package de.dennis_boldt.netty.JerseyServer.buisinesslogic;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/test/{id}")
public class TestRessource {

    @DELETE
    public Response deleteContact(@PathParam("id") Integer id) {
        System.out.println("DELETE on " + id);
        return Response.ok().build();
	}

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Object getContact(@PathParam("id") Integer id) {
        if(id > 1000) return null;
        System.out.println("GET on " + id);
        Test test = new Test(id);
        return test;
    }

    @POST
    public Response postContact(@PathParam("id") Integer id) {
        System.out.println("POST on " + id);
        return Response.ok().build();
    }

    @PUT
    public Response putContact(@PathParam("id") Integer id) {
        System.out.println("PUT on " + id);
        return Response.ok().build();
    }
}
