package com.hindsight.sb.repository;

import com.hindsight.sb.entity.CourseSubject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<CourseSubject, Long> {
}
