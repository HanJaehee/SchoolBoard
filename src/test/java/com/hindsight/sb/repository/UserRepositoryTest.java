package com.hindsight.sb.repository;

import com.hindsight.sb.entity.DeptEntity;
import com.hindsight.sb.entity.UserEntity;
import com.hindsight.sb.entity.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDate;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class UserRepositoryTest {

    private final LocalDate birth = LocalDate.now();
    private final String name = "한재희";
    private final String address = "의정부시 장금로";
    private final String phoneNo = "000-0000-0000";
    private final String deptName = "정보보호학과";
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DeptRepository deptRepository;

    @BeforeEach
    void insertDept() {
        DeptEntity baseDept = DeptEntity.builder()
                .name(deptName)
                .build();
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
        DeptEntity dept = deptRepository.findByName(deptName).orElseThrow(NoSuchElementException::new);
        UserEntity student = UserEntity.builder()
                .name(name)
                .address(address)
                .birth(birth)
                .userRole(UserRole.STUDENT)
                .phoneNo(phoneNo)
                .deptEntity(dept)
                .build();

        UserEntity savedStudent = userRepository.save(student);

        assertEquals(savedStudent, student);
    }

    @Test
    @DisplayName("유저(교수) 생성")
    void createProfessorOfUser() {
        DeptEntity dept = deptRepository.findByName(deptName).orElseThrow(NoSuchElementException::new);
        UserEntity prof = UserEntity.builder()
                .name(name)
                .address(address)
                .birth(birth)
                .userRole(UserRole.STUDENT)
                .phoneNo(phoneNo)
                .deptEntity(dept)
                .build();

        UserEntity savedProf = userRepository.save(prof);

        assertEquals(savedProf, prof);
    }

    @Test
    @DisplayName("Dept Not Nullable")
    void testName() {
        // given
        UserEntity userEntity = UserEntity.builder()
                .deptEntity(null)
                .name(name)
                .phoneNo(phoneNo)
                .userRole(UserRole.STUDENT)
                .birth(birth)
                .address(null)
                .build();
        // when, then
        assertThrows(DataIntegrityViolationException.class,
                () -> userRepository.save(userEntity));
    }

}