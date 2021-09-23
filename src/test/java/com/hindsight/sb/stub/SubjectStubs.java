package com.hindsight.sb.stub;

import com.hindsight.sb.dto.subject.SubjectRequest;
import com.hindsight.sb.entity.SubjectEntity;
import com.hindsight.sb.entity.UserEntity;
import org.springframework.test.util.ReflectionTestUtils;

public class SubjectStubs {

    private static long subjectId = 1L;

    public static SubjectEntity generateSubject(UserEntity prof) {

        SubjectEntity subjectEntity = SubjectEntity.builder()
                .name("정보보호의 기초")
                .prof(prof)
                .build();
        ReflectionTestUtils.setField(subjectEntity, "id", subjectId++);
        return subjectEntity;
    }

    public static SubjectRequest generateSubjectRequest(Long profId) {
        return SubjectRequest.builder()
                .profId(profId)
                .name("정보보호의 기초")
                .build();
    }
}
