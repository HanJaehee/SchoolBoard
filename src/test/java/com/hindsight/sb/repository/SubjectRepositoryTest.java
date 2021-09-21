package com.hindsight.sb.repository;

import com.hindsight.sb.entity.DeptEntity;
import com.hindsight.sb.entity.SubjectEntity;
import com.hindsight.sb.entity.UserEntity;
import com.hindsight.sb.entity.UserRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class SubjectRepositoryTest {

    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DeptRepository deptRepository;

    DeptEntity deptEntity() {
        return DeptEntity.builder()
                .name("정보보호학과").build();
    }

    UserEntity userEntity(DeptEntity dept) {
        return UserEntity.builder()
                .address("경기도 의정부시")
                .deptEntity(dept)
                .userRole(UserRole.PROFESSOR)
                .birth(LocalDate.now())
                .phoneNo("000-0000-0000")
                .name("김교수")
                .build();
    }

    @Test
    @DisplayName("과목 생성")
    void createSubject() {
        DeptEntity dept = deptEntity();
        deptRepository.save(dept);
        UserEntity prof = userEntity(dept);
        userRepository.save(prof);
        SubjectEntity subject = SubjectEntity.builder()
                .name("정보보호의 기초")
                .supervisor(prof)
                .build();

        SubjectEntity savedSubject = subjectRepository.save(subject);

        assertEquals(subject, savedSubject);
        assertEquals(subject.getSupervisor(), prof);
    }

}