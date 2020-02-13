package com.technical.assignment.demo.service;

import com.technical.assignment.demo.dto.Story;
import com.technical.assignment.demo.storage.StoryStorageImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.FieldSetter;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class StoryProviderTest {


    SessionFactory mockedSessionFactory;
    Session mockedSession;

    StoryStorageImpl storyStorage;
    StoryProvider storyProvider;

    private List<Story> test_suit_one;

    @Before
    public void setUp() throws NoSuchFieldException {
        mockedSessionFactory = Mockito.mock(SessionFactory.class);
        mockedSession = Mockito.mock(Session.class);

        Mockito.when(mockedSessionFactory.getCurrentSession()).thenReturn(mockedSession);

        storyStorage = new StoryStorageImpl(mockedSessionFactory);
        storyProvider = new StoryProvider(storyStorage);
        FieldSetter.setField(storyStorage, storyStorage.getClass().getDeclaredField("offlineMode"), true);

        storyStorage.STORAGE_CAPACITY = 500;
        test_suit_one = Arrays.asList(new Story("Hack the world","1 minute ago","https://domain/serivce/1"),
                new Story("How to cook","5 minute ago","https://domain/serivce/2"),
                new Story("10 tips to pass the interview","1 hour ago","https://domain/serivce/3"));
    }
    @Test
    public void getNewStoriesShouldReturnExpectedStories() {
        storyStorage.saveStories(test_suit_one);

        List<Story> stories = storyProvider.getNewStories();
        assertThat(stories.get(0).getTitle()).isEqualTo("Hack the world");
        assertThat(stories.get(1).getTitle()).isEqualTo("How to cook");
        assertThat(stories.get(2).getTitle()).isEqualTo("10 tips to pass the interview");
    }

    @Test
    public void getNewStoriesShouldPreserveExpectedSize() {
        List<Story> list = new ArrayList<>();

        for (int i = 0; i < 1000; i++) list.add(new Story("title#" + i, "",""));
        storyStorage.saveStories(list);

        List<Story> stories = storyProvider.getNewStories();
        assertThat(stories.size()).isEqualTo(500);
    }

    @Test
    public void getNewStoriesShouldNotContainsDuplicates() {
        List<Story> list = new ArrayList<>();

        for (int i = 0; i < 1000; i++) list.add(new Story("sameTitle", "",""));
        storyStorage.saveStories(list);

        List<Story> stories = storyProvider.getNewStories();
        assertThat(stories.size()).isEqualTo(1);
    }

    @Test
    public void getNewStoriesShouldPreserveOrder() {
        List<Story> list = new ArrayList<>();

        for (int i = 0; i < 10; i++) list.add(new Story("title#" + i, "",""));
        storyStorage.saveStories(list);

        List<Story> stories = storyProvider.getNewStories();
        for (int i = 0; i < 10; i++) {
            assertThat(stories.get(i).getTitle()).isEqualTo("title#" + i);
        }
    }
}