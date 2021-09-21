package com.hindsight.sb.repository;

import com.hindsight.sb.entity.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class CourseRepositoryTest {

    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private DeptRepository deptRepository;

    DeptEntity deptEntity() {
        return DeptEntity.builder().name("정보보호학과").build();
    }

    UserEntity userEntity(DeptEntity dept, UserRole role, String name, String phoneNo) {
        return UserEntity.builder()
                .name(name)
                .userRole(role)
                .phoneNo(phoneNo)
                .deptEntity(dept)
                .birth(LocalDate.now())
                .address("경기도 의정부")
                .deptEntity(dept)
                .build();
    }

    SubjectEntity subjectEntity(UserEntity prof) {
        return SubjectEntity.builder()
                .prof(prof)
                .name("정보보호의 기초").build();
    }

    @Test
    @DisplayName("수강 내역 생성")
    void createCourse() {
        DeptEntity dept = deptEntity();
        deptRepository.save(dept);
        UserEntity student = userEntity(dept, UserRole.STUDENT, "학생", "000-0000-0000");
        UserEntity prof = userEntity(dept, UserRole.PROFESSOR, "교수", "111-1111-1111");
        userRepository.save(student);
        userRepository.save(prof);
        SubjectEntity subject = subjectEntity(prof);
        subjectRepository.save(subject);

        CourseSubjectEntity courseSubjectEntity = CourseSubjectEntity.builder()
                .subject(subject)
                .student(student)
                .build();

        assertEquals(courseSubjectEntity.getSubject(), subject);
        assertEquals(courseSubjectEntity.getStudent(), student);

    }
}