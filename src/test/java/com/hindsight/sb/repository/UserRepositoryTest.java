package com.hindsight.sb.repository;

import com.hindsight.sb.entity.DeptEntity;
import com.hindsight.sb.entity.UserEntity;
import com.hindsight.sb.entity.UserRole;
import com.hindsight.sb.stub.DeptStubs;
import com.hindsight.sb.stub.UserStubs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DeptRepository deptRepository;

    DeptEntity baseDept;
    @BeforeEach
    void insertDept() {
        baseDept = DeptStubs.generateEntity();
        deptRepository.save(baseDept);
    }

    @Test
    @DisplayName("Repository Null 아님")
    void repository_notNull() {
        assertNotNull(userRepository);
    }

    @Test
    @DisplayName("유저(학생) 생성")
    void createStudentOfUser() {
        DeptEntity dept = deptRepository.findByName(baseDept.getName()).orElseThrow(NoSuchElementException::new);
        UserEntity student = UserStubs.generateEntity(UserRole.STUDENT, "000-0000-0000", dept);

        UserEntity savedStudent = userRepository.save(student);

        System.out.println(savedStudent.getId() + " " + student.getId());
        assertEquals(savedStudent, student);
    }

    @Test
    @DisplayName("유저(교수) 생성")
    void createProfessorOfUser() {
        DeptEntity dept = deptRepository.findByName(baseDept.getName()).orElseThrow(NoSuchElementException::new);
        UserEntity prof = UserStubs.generateEntity(UserRole.PROFESSOR, "000-0000-0000", dept);

        UserEntity savedProf = userRepository.save(prof);

        assertEquals(savedProf, prof);
    }
}