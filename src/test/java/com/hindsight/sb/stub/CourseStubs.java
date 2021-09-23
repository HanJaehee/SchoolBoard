package com.hindsight.sb.stub;

import com.hindsight.sb.dto.course.CourseRequest;
import com.hindsight.sb.entity.CourseSubjectEntity;
import com.hindsight.sb.entity.SubjectEntity;
import com.hindsight.sb.entity.UserEntity;
import org.springframework.test.util.ReflectionTestUtils;

public class CourseStubs {

    private static long courseId = 1L;

    public static CourseSubjectEntity generateEntity(UserEntity student, SubjectEntity subject) {
        CourseSubjectEntity entity = CourseSubjectEntity.builder()
                .student(student)
                .subject(subject)
                .build();
        ReflectionTestUtils.setField(entity, "id", courseId++);
        return entity;
    }

    public static CourseRequest generateRequest(Long studentId, Long subjectId) {
        return CourseRequest.builder()
                .studentId(studentId)
                .subjectId(subjectId)
                .build();
    }
}
