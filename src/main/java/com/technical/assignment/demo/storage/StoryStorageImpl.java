package com.technical.assignment.demo.storage;

import com.technical.assignment.demo.dto.Story;
import com.technical.assignment.demo.hibernate.StoryEntity;
import org.apache.commons.collections4.map.AbstractLinkedMap;
import org.apache.commons.collections4.map.LinkedMap;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@Repository
@Transactional
public class StoryStorageImpl implements StoryStorage {

    @Value("${hacker-rank.storage.new.stories.capacity:500}")
    public int STORAGE_CAPACITY;

    SessionFactory sessionFactory;

    public StoryStorageImpl(@Autowired SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

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

        persist();
    }

    private void persist() {
        map.values().forEach(e -> sessionFactory.getCurrentSession().saveOrUpdate(new StoryEntity(e.getId(), e.getTitle(), e.getTimeAgo(), e.getUrl())));
    }

    @Override
    public List<Story> getStories() {
        CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<StoryEntity> criteria = builder.createQuery(StoryEntity.class);
        criteria.from(StoryEntity.class);
        List<StoryEntity> data = sessionFactory.getCurrentSession().createQuery(criteria).getResultList();

        List<Story> result = new ArrayList<>();
        for (StoryEntity storyEntity : data) {
            result.add(new Story(storyEntity.getTitle(), storyEntity.getTimeAgo(), storyEntity.getUrl()));
        }
        return result;
    }
}
