package com.technical.assignment.demo.service;

import com.technical.assignment.demo.dto.Story;
import com.technical.assignment.demo.storage.StoryStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoryProvider {

    private final StoryStorage storyStorage;

    public StoryProvider(@Autowired StoryStorage storyStorage) {
        this.storyStorage = storyStorage;
    }

    public List<Story> getNewStories() {
        List<Story> stories = storyStorage.getStories();
        return stories;
    }
}
