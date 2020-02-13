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

    private boolean offlineMode = false;

    public StoryStorageImpl(@Autowired SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    AbstractLinkedMap<String, StoryEntity> map = new LinkedMap<>();

    @Override
    public void saveStories(List<Story> stories) {
        if (stories == null || stories.size() == 0) {
            offlineMode = true;
            return;
        }

        offlineMode = false;
        for (Story story : stories) {
            if (map.containsKey(story.getTitle())) continue;
            if (map.size() >= STORAGE_CAPACITY) {
                map.remove(map.lastKey());
                sessionFactory.getCurrentSession().delete(map.get(map.lastKey()));
            }
            map.put(story.getTitle(), new StoryEntity(story.getId(), story.getTitle(), story.getTimeAgo(), story.getUrl()));
        }

        persist();
    }

    private void persist() {
        map.values().forEach(e -> sessionFactory.getCurrentSession().saveOrUpdate(new StoryEntity(e.getId(), e.getTitle(), e.getTimeAgo(), e.getUrl())));
    }

    @Override
    public List<Story> getStories() {
        List<Story> result = new ArrayList<>();
        if (offlineMode) {
            CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
            CriteriaQuery<StoryEntity> criteria = builder.createQuery(StoryEntity.class);
            criteria.from(StoryEntity.class);
            List<StoryEntity> data = sessionFactory.getCurrentSession().createQuery(criteria).getResultList();

            for (StoryEntity storyEntity : data) {
                result.add(new Story(storyEntity.getTitle(), storyEntity.getTimeAgo(), storyEntity.getUrl()));
            }
        } else {
            map.values().forEach(e -> result.add(new Story(e.getId(), e.getTitle(), e.getTimeAgo(), e.getUrl())));
        }
        return result;
    }
}
