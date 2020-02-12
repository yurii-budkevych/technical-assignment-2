package com.technical.assignment.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.technical.assignment.demo.dto.Story;
import com.technical.assignment.demo.service.StoryProvider;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.core.Response;
import java.util.List;

@Controller
public class EndpointImpl implements Endpoint {

    public static final String PLEASE_COME_LATER = "Please come later";

    @Autowired
    private StoryProvider storyProvider;

    @Override
    public Response newStories() {
        List<Story> stories = storyProvider.getNewStories();
        return prepareResponse(stories);
    }

    private Response prepareResponse(List<Story> stories) {
        if (stories.size() == 0) return Response.status(Response.Status.OK).entity(PLEASE_COME_LATER).build();
        ObjectMapper mapper = new ObjectMapper();

        JSONArray result = new JSONArray(stories);

        return Response.status(Response.Status.OK).entity(result.toString()).build();
    }
}
