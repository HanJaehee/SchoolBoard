package com.hindsight.sb.repository;

import com.hindsight.sb.entity.DeptEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class DeptRepositoryTest {

    @Autowired
    private DeptRepository deptRepository;

    @Test
    @DisplayName("전공 생성")
    void createDept() {
        DeptEntity deptEntity = DeptEntity.builder()
                .name("정보보호학과")
                .build();

        DeptEntity savedDept = deptRepository.save(deptEntity);

        assertEquals(savedDept, deptEntity);
    }


}