package com.hindsight.sb.repository;

import com.hindsight.sb.entity.CourseSubjectEntity;
import com.hindsight.sb.entity.SubjectEntity;
import com.hindsight.sb.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<CourseSubjectEntity, Long> {

    List<CourseSubjectEntity> findAllByStudent(UserEntity student);

    List<CourseSubjectEntity> findAllBySubject(SubjectEntity subject);
}
