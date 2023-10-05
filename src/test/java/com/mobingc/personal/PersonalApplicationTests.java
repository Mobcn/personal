package com.mobingc.personal;

import com.mobingc.personal.config.mapdb.MapDB;
import com.mobingc.personal.model.entity.Tag;
import com.mobingc.personal.repository.TagRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PersonalApplicationTests {

    @Autowired
    private MapDB mapDB;

    @Autowired
    private TagRepository tagRepository;

    @Test
    void contextLoads() throws InterruptedException {
        System.out.println(tagRepository.findById(null));
    }

}
