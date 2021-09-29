package com.hindsight.sb.stub;

import com.hindsight.sb.dto.subject.SubjectBriefResponse;
import com.hindsight.sb.dto.subject.SubjectDetailResponse;
import com.hindsight.sb.dto.subject.SubjectRequest;
import com.hindsight.sb.dto.user.UserBriefResponse;
import com.hindsight.sb.entity.SubjectEntity;
import com.hindsight.sb.entity.UserEntity;
import org.springframework.test.util.ReflectionTestUtils;

public class SubjectStubs {

    private static long subjectId = 1L;
    private static String subjectName = "정보보호의 기초";

    public static SubjectEntity generateEntity(UserEntity prof) {
        return SubjectEntity.builder()
                .name(subjectName)
                .prof(prof)
                .build();
    }

    public static SubjectEntity generateStub(UserEntity prof) {
        SubjectEntity subjectEntity = generateEntity(prof);
        ReflectionTestUtils.setField(subjectEntity, "id", subjectId++);
        return subjectEntity;
    }

    public static SubjectRequest generateRequest(Long profId) {
        return SubjectRequest.builder()
                .profId(profId)
                .name(subjectName)
                .build();
    }

    public static SubjectDetailResponse generateResponse(UserBriefResponse prof, Long id) {
        return SubjectDetailResponse.builder()
                .id(id)
                .name(subjectName)
                .prof(prof)
                .build();
    }

    public static SubjectBriefResponse generateBriefResponse(Long id, String name) {
        return SubjectBriefResponse.builder()
                .id(id)
                .name(subjectName)
                .build();
    }
}
