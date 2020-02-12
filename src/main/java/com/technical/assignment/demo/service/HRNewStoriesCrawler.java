package com.technical.assignment.demo.service;

import com.technical.assignment.demo.dto.Story;
import com.technical.assignment.demo.storage.StoryStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class HRNewStoriesCrawler implements NewStoriesCrawler {

    @Value("${hacker-rank.new.stories.path}")
    public String HACKER_RANK_NEW_STORIES_PATH;

    private static final String DOT_JSON = ".json";

    @Value("${hacker-rank.new.stories.number-of-batches}")
    private Integer numberOfBatches;

    @Autowired
    private Client client;
    @Autowired
    private StoryStorage storyStorage;

    @Scheduled(fixedRateString = "${hacker-rank.new.stories.refresh-period}")
    public void fetchNewStories() {
        List<Story> list = new ArrayList<>();
        for (int i = 1; i <= numberOfBatches; i++) {
            Invocation.Builder invocationBuilder =
                    client.target(HACKER_RANK_NEW_STORIES_PATH).path(i + DOT_JSON)
                            .request(MediaType.APPLICATION_JSON);

            Response response = invocationBuilder.accept(MediaType.APPLICATION_JSON_TYPE).get();

            list.addAll(Arrays.asList(response.readEntity(Story[].class)));
        }

        storyStorage.saveStories(list);
    }
}
