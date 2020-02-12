package com.technical.assignment.demo.storage;

import com.technical.assignment.demo.dto.Story;
import org.apache.commons.collections4.map.AbstractLinkedMap;
import org.apache.commons.collections4.map.LinkedMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


@Repository
public class StoryStorageImpl implements StoryStorage {

    @Value("${hacker-rank.storage.new.stories.capacity:500}")
    public int STORAGE_CAPACITY;

    AbstractLinkedMap<String, Story> map = new LinkedMap<>();

    @Override
    public void saveStories(List<Story> stories) {

        for (Story story : stories) {
            if (map.containsKey(story.getTitle())) continue;
            if (map.size() >= STORAGE_CAPACITY) {
                map.remove(map.lastKey());
            }
            map.put(story.getTitle(), story);
        }
    }

    @Override
    public List<Story> getStories() {
        return new ArrayList<>(map.values());
    }
}
