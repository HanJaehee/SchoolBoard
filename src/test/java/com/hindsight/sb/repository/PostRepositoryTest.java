package com.hindsight.sb.repository;

import com.hindsight.sb.entity.PostEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Test
    @DisplayName("게시글 작성")
    void createPost() {
        PostEntity postEntity = PostEntity.builder()
                .title("오늘점심은?ð")
                .content("짜장면")
                .subject(null)
                .user(null)
                .build();

        PostEntity savedPost = postRepository.save(postEntity);

        assertEquals(postEntity, savedPost);
    }
}