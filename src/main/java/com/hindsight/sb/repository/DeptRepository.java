package com.hindsight.sb.repository;

import com.hindsight.sb.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeptRepository extends JpaRepository<Department, Long> {
}
