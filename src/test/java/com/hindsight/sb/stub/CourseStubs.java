package com.hindsight.sb.stub;

import com.hindsight.sb.dto.course.CourseRequest;
import com.hindsight.sb.dto.course.CourseResponse;
import com.hindsight.sb.dto.subject.SubjectResponse;
import com.hindsight.sb.entity.CourseSubjectEntity;
import com.hindsight.sb.entity.SubjectEntity;
import com.hindsight.sb.entity.UserEntity;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

public class CourseStubs {

    private static long courseId = 1L;

    public static CourseSubjectEntity generateEntity(UserEntity student, SubjectEntity subject) {
        return CourseSubjectEntity.builder()
                .student(student)
                .subject(subject)
                .build();
    }

    public static CourseSubjectEntity generateStub(UserEntity student, SubjectEntity subject) {
        CourseSubjectEntity entity = generateEntity(student, subject);
        ReflectionTestUtils.setField(entity, "id", courseId++);
        return entity;
    }

    public static CourseRequest generateRequest(Long studentId, Long subjectId) {
        return CourseRequest.builder()
                .studentId(studentId)
                .subjectId(subjectId)
                .build();
    }

    public static CourseResponse generateResponse(List<SubjectResponse> subjectList) {
        return CourseResponse.builder()
                .subjectList(subjectList)
                .build();
    }
}
