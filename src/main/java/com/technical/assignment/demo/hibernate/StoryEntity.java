package com.technical.assignment.demo.hibernate;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class StoryEntity {

    @Id
    @Column(name = "id", length = 64)
    private String id;

    public StoryEntity(String id, String title, String timeAgo, String url) {
        this.id = id;
        this.title = title;
        this.timeAgo = timeAgo;
        this.url = url;
    }

    public StoryEntity() {
    }

    private String title;

    @JsonProperty("time_ago")
    private String timeAgo;

    private String url;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getTimeAgo() {
        return timeAgo;
    }

    public String getUrl() {
        return url;
    }
}
