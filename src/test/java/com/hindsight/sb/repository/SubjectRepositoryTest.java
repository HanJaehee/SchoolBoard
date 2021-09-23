package com.hindsight.sb.repository;

import com.hindsight.sb.entity.DeptEntity;
import com.hindsight.sb.entity.SubjectEntity;
import com.hindsight.sb.entity.UserEntity;
import com.hindsight.sb.entity.UserRole;
import com.hindsight.sb.stub.DeptStubs;
import com.hindsight.sb.stub.UserStubs;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class SubjectRepositoryTest {

    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DeptRepository deptRepository;

    @Test
    @DisplayName("과목 생성 및 조회")
    void createSubject() {
        DeptEntity dept = DeptStubs.generateEntity();
        deptRepository.save(dept);
        UserEntity prof = UserStubs.generateEntity(UserRole.PROFESSOR, "000-0000-0000", dept);
        userRepository.save(prof);
        SubjectEntity subject = SubjectEntity.builder()
                .name("정보보호의 기초")
                .prof(prof)
                .build();

        SubjectEntity savedSubject = subjectRepository.save(subject);

        assertEquals(subject, savedSubject);
        assertEquals(subject.getProf(), prof);
    }


}