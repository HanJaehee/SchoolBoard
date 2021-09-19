package com.hindsight.sb.repository;

import com.hindsight.sb.entity.DeptEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeptRepository extends JpaRepository<DeptEntity, Long> {

    Optional<DeptEntity> findByName(String name);
}
