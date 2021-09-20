package com.hindsight.sb.repository;

import com.hindsight.sb.entity.SubjectEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class SubjectRepositoryTest {

    @Autowired
    private SubjectRepository subjectRepository;

    @Test
    @DisplayName("과목 생성")
    void createSubejct() {
        SubjectEntity subject = SubjectEntity.builder()
                .name("정보보호의 기초")
                .build();

        SubjectEntity savedSubject = subjectRepository.save(subject);

        assertEquals(subject, savedSubject);
    }

}