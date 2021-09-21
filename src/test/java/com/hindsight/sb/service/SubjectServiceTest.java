package com.hindsight.sb.service;

import com.hindsight.sb.dto.subject.SubjectRequest;
import com.hindsight.sb.dto.subject.SubjectResponse;
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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
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
    @Mock
    private UserRepository userRepository;

    DeptEntity deptEntity() {
        DeptEntity dept = DeptEntity.builder()
                .name("정보보호학과").build();
        ReflectionTestUtils.setField(dept, "id", 1L);
        return dept;
    }

    UserEntity userEntity(DeptEntity dept) {
        UserEntity user = UserEntity.builder()
                .address("경기도 의정부시")
                .deptEntity(dept)
                .userRole(UserRole.PROFESSOR)
                .birth(LocalDate.now())
                .phoneNo("000-0000-0000")
                .name("김교수")
                .build();
        ReflectionTestUtils.setField(user, "id", 1L);
        return user;
    }

    SubjectRequest subjectRequest() {
        return SubjectRequest.builder()
                .superId(1L)
                .name(subjectName)
                .build();
    }

    SubjectEntity subjectEntity(UserEntity supervisor) {
        SubjectEntity entity = SubjectEntity.builder()
                .supervisor(supervisor)
                .name(subjectName).build();
        ReflectionTestUtils.setField(entity, "id", 1L);
        return entity;
    }

    @Test
    @DisplayName("과목 생성 실패 - No Supervisor")
    void addSubject_fail_NoSupervisor() {
        // given
        SubjectRequest req = subjectRequest();
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
        SubjectRequest req = subjectRequest();
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
        SubjectRequest req = subjectRequest();
        DeptEntity dept = deptEntity();
        UserEntity supervisor = userEntity(dept);
        SubjectEntity entity = subjectEntity(supervisor);

        doReturn(Optional.of(supervisor)).when(userRepository).findById(any(Long.class));
        doReturn(entity).when(subjectRepository).save(any(SubjectEntity.class));

        // when
        SubjectResponse res = subjectService.addSubject(req);
        // then
        assertNotNull(res.getId());
        assertEquals(res.getName(), req.getName());
    }
}
