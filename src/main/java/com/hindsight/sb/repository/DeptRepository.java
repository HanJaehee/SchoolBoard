package com.hindsight.sb.repository;

import com.hindsight.sb.entity.DeptEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeptRepository extends JpaRepository<DeptEntity, Long> {
}
