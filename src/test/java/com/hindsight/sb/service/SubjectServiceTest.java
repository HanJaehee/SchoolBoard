package com.hindsight.sb.service;

import com.hindsight.sb.dto.subject.SubjectRequest;
import com.hindsight.sb.dto.subject.SubjectResponse;
import com.hindsight.sb.entity.SubjectEntity;
import com.hindsight.sb.exception.subject.SubjectErrorResult;
import com.hindsight.sb.exception.subject.SubjectException;
import com.hindsight.sb.repository.SubjectRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@MockitoSettings
public class SubjectServiceTest {

    private final String subjectName = "정보보호의 기초";
    @InjectMocks
    private SubjectServiceImpl subjectService;
    @Mock
    private SubjectRepository subjectRepository;

    SubjectRequest subjectRequest() {
        return SubjectRequest.builder().name(subjectName).build();
    }

    SubjectEntity subjectEntity() {
        SubjectEntity entity = SubjectEntity.builder().name(subjectName).build();
        ReflectionTestUtils.setField(entity, "id", 1L);
        return entity;
    }

    @Test
    @DisplayName("과목 생성 실패 - 과목 이름 중복")
    void addSubject_fail_duplicateName() {
        // given
        doReturn(Optional.of(SubjectEntity.builder().build())).when(subjectRepository).findByName(any(String.class));

        // when
        SubjectException subjectException = assertThrows(SubjectException.class, () -> subjectService.addSubject(subjectRequest()));

        // then
        assertEquals(SubjectErrorResult.DUPLICATE_NAME, subjectException.getErrorResult());
    }

    @Test
    @DisplayName("과목 생성 성공")
    void addSubject_success() {
        // given
        SubjectRequest req = subjectRequest();
        SubjectEntity entity = subjectEntity();
        doReturn(entity).when(subjectRepository).save(any(SubjectEntity.class));
        // when
        SubjectResponse res = subjectService.addSubject(req);
        // then
        assertNotNull(res.getId());
        assertEquals(res.getName(), req.getName());
    }
}
