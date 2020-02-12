package com.technical.assignment.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Story {

    private String id;

    private String title;

    @JsonProperty("time_ago")
    private String timeAgo;

    private String url;

    public Story(String title, String timeAgo, String url) {
        this.title = title;
        this.timeAgo = timeAgo;
        this.url = url;
    }

    @Override
    public String toString() {
        return "Story{" +
                "title='" + title + '\'' +
                ", timeAgo='" + timeAgo + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
