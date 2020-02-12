package com.technical.assignment.demo.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/service")
public interface Endpoint {

    @GET
    @Path("/new-stories")
    @Produces(MediaType.APPLICATION_JSON)
    Response newStories();

}
