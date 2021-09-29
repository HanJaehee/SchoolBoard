package com.hindsight.sb.service;

import com.hindsight.sb.dto.subject.SubjectDetailResponse;
import com.hindsight.sb.dto.subject.SubjectRequest;
import com.hindsight.sb.entity.DeptEntity;
import com.hindsight.sb.entity.SubjectEntity;
import com.hindsight.sb.entity.UserEntity;
import com.hindsight.sb.entity.UserRole;
import com.hindsight.sb.exception.subject.SubjectErrorResult;
import com.hindsight.sb.exception.subject.SubjectException;
import com.hindsight.sb.exception.user.UserErrorResult;
import com.hindsight.sb.exception.user.UserException;
import com.hindsight.sb.repository.SubjectRepository;
import com.hindsight.sb.repository.UserRepository;
import com.hindsight.sb.stub.DeptStubs;
import com.hindsight.sb.stub.SubjectStubs;
import com.hindsight.sb.stub.UserStubs;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@MockitoSettings
public class SubjectServiceTest {

    @InjectMocks
    private SubjectServiceImpl subjectService;
    @Mock
    private SubjectRepository subjectRepository;
    @Mock
    private UserRepository userRepository;

    @Test
    @DisplayName("과목 생성 실패 - No Supervisor")
    void addSubject_fail_NoSupervisor() {
        // given
        SubjectRequest req = SubjectStubs.generateRequest(1L);
        doReturn(Optional.empty()).when(userRepository).findById(any(Long.class));

        // when
        UserException userException = assertThrows(UserException.class, () -> subjectService.addSubject(req));
        // then
        assertEquals(UserErrorResult.NOT_EXISTS_USER, userException.getErrorResult());
    }

    @Test
    @DisplayName("과목 생성 실패 - 과목 이름 중복")
    void addSubject_fail_duplicateName() {
        // given
        SubjectRequest req = SubjectStubs.generateRequest(1L);
        doReturn(Optional.of(SubjectEntity.builder().build())).when(subjectRepository).findByName(any(String.class));

        // when
        SubjectException subjectException = assertThrows(SubjectException.class, () -> subjectService.addSubject(req));

        // then
        assertEquals(SubjectErrorResult.DUPLICATE_NAME, subjectException.getErrorResult());
    }

    @Test
    @DisplayName("과목 생성 성공")
    void addSubject_success() {
        // given
        DeptEntity dept = DeptStubs.generateStub();
        UserEntity prof = UserStubs.generateStub(UserRole.PROFESSOR, "000-0000-0000", dept);
        SubjectEntity entity = SubjectStubs.generateStub(prof);
        SubjectRequest req = SubjectStubs.generateRequest(prof.getId());

        doReturn(Optional.of(prof)).when(userRepository).findById(any(Long.class));
        doReturn(entity).when(subjectRepository).save(any(SubjectEntity.class));

        // when
        SubjectDetailResponse res = subjectService.addSubject(req);
        // then
        assertNotNull(res.getId());
        assertEquals(res.getName(), req.getName());
    }

    @Test
    @DisplayName("과목 조회 성공")
    void getSubject_success() {
        // given
        DeptEntity dept = DeptStubs.generateStub();
        UserEntity prof = UserStubs.generateStub(UserRole.PROFESSOR, "000-0000-0000", dept);
        SubjectEntity subject = SubjectStubs.generateStub(prof);
        doReturn(Optional.of(subject)).when(subjectRepository).findById(any(Long.class));

        // when
        SubjectDetailResponse subjectRes = subjectService.getSubject(prof.getId());

        // then
        assertEquals(subjectRes.getId(), prof.getId());
    }

    @Test
    @DisplayName("과목 조회 실패 - 일치하는 아이디 없음")
    void getSubject_fail_NoSuchElement() {
        // given
        doReturn(Optional.empty()).when(subjectRepository).findById(any(Long.class));

        // when, then
        assertThrows(NoSuchElementException.class, () -> subjectService.getSubject(1L));

    }
}
