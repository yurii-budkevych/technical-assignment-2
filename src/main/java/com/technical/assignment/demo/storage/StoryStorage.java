package com.technical.assignment.demo.storage;

import com.technical.assignment.demo.dto.Story;

import java.util.List;

public interface StoryStorage {
    void saveStories(List<Story> stories);
    List<Story> getStories();
}
