package com.technical.assignment.demo.controller;

import com.technical.assignment.demo.dto.Story;
import com.technical.assignment.demo.storage.StoryStorage;
import com.technical.assignment.demo.storage.StoryStorageImpl;
import org.json.JSONArray;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EndpointImplTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private StoryStorage storyStorage;

    private List<Story> test_suit_one;

    @BeforeEach
    public void setUp() {
        test_suit_one = Arrays.asList(new Story("Hack the world","1 minute ago","https://domain/serivce/1"),
                new Story("How to cook","5 minute ago","https://domain/serivce/2"),
                new Story("10 tips to pass the interview","1 hour ago","https://domain/serivce/3"));
    }
    @Test
    void newStoriesShouldReturnPleaseComeLater() {
        assertThat(restTemplate.getForObject("http://localhost:" + port + "service/new-stories",
                String.class)).contains(EndpointImpl.PLEASE_COME_LATER);
    }

    @Test
    void newStoriesShouldReturnExpectedSize() {

        storyStorage.saveStories(test_suit_one);

        assertThat(restTemplate.getForObject("http://localhost:" + port + "service/new-stories",
                Object[].class).length).isEqualTo(3);
    }
}