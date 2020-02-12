package com.technical.assignment.demo.service;

import com.technical.assignment.demo.dto.Story;
import com.technical.assignment.demo.storage.StoryStorage;
import com.technical.assignment.demo.storage.StoryStorageImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class StoryProviderTest {

    StoryStorageImpl storyStorage;
    StoryProvider storyProvider;

    private List<Story> test_suit_one;

    @BeforeEach
    void setUp() {
        storyStorage = new StoryStorageImpl();
        storyProvider = new StoryProvider(storyStorage);

        storyStorage.STORAGE_CAPACITY = 500;
        test_suit_one = Arrays.asList(new Story("Hack the world","1 minute ago","https://domain/serivce/1"),
                new Story("How to cook","5 minute ago","https://domain/serivce/2"),
                new Story("10 tips to pass the interview","1 hour ago","https://domain/serivce/3"));
    }
    @Test
    void getNewStoriesShouldReturnExpectedStories() {
        storyStorage.saveStories(test_suit_one);

        List<Story> stories = storyProvider.getNewStories();
        assertThat(stories.get(0).getTitle()).isEqualTo("Hack the world");
        assertThat(stories.get(1).getTitle()).isEqualTo("How to cook");
        assertThat(stories.get(2).getTitle()).isEqualTo("10 tips to pass the interview");
    }

    @Test
    void getNewStoriesShouldPreserveExpectedSize() {
        List<Story> list = new ArrayList<>();

        for (int i = 0; i < 1000; i++) list.add(new Story("title#" + i, "",""));
        storyStorage.saveStories(list);

        List<Story> stories = storyProvider.getNewStories();
        assertThat(stories.size()).isEqualTo(500);
    }

    @Test
    void getNewStoriesShouldNotContainsDuplicates() {
        List<Story> list = new ArrayList<>();

        for (int i = 0; i < 1000; i++) list.add(new Story("sameTitle", "",""));
        storyStorage.saveStories(list);

        List<Story> stories = storyProvider.getNewStories();
        assertThat(stories.size()).isEqualTo(1);
    }

    @Test
    void getNewStoriesShouldPreserveOrder() {
        List<Story> list = new ArrayList<>();

        for (int i = 0; i < 10; i++) list.add(new Story("title#" + i, "",""));
        storyStorage.saveStories(list);

        List<Story> stories = storyProvider.getNewStories();
        for (int i = 0; i < 10; i++) {
            assertThat(stories.get(i).getTitle()).isEqualTo("title#" + i);
        }
    }
}