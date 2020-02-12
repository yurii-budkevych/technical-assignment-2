package com.technical.assignment.demo;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {"hacker-rank.storage.new.stories.capacity=500"})
class DemoApplicationTests {

    @Test
    void contextLoads() {
    }
}
