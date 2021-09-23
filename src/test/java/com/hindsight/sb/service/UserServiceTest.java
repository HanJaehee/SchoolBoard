package com.hindsight.sb.service;

import com.hindsight.sb.dto.user.UserDetailResponse;
import com.hindsight.sb.dto.user.UserRequest;
import com.hindsight.sb.entity.DeptEntity;
import com.hindsight.sb.entity.UserEntity;
import com.hindsight.sb.entity.UserRole;
import com.hindsight.sb.exception.dept.DeptErrorResult;
import com.hindsight.sb.exception.dept.DeptException;
import com.hindsight.sb.exception.user.UserErrorResult;
import com.hindsight.sb.exception.user.UserException;
import com.hindsight.sb.repository.DeptRepository;
import com.hindsight.sb.repository.UserRepository;
import com.hindsight.sb.stub.DeptStubs;
import com.hindsight.sb.stub.UserStubs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoSettings;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@MockitoSettings
public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private DeptRepository deptRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserServiceImpl(userRepository, deptRepository);
    }

    @Test
    @DisplayName("유저 생성 실패 - Duplicate PhoneNo")
    void createUser_fail_duplicatePhoneNo() {
        // given
        UserRequest req = UserStubs.generateRequest(0, 1L, "000-0000-0000");
        doReturn(Optional.of(UserEntity.builder().build())).when(userRepository).findByPhoneNo(any(String.class));
        // when
        UserException userException = assertThrows(UserException.class, () -> userService.addUser(req));

        // then
        assertEquals(UserErrorResult.DUPLICATED_PHONE_NUMBER, userException.getErrorResult());
    }

    @Test
    @DisplayName("유저 생성 실패 - No Dept")
    void createUser_fail_noSuchDept() {
        // given
        UserRequest req = UserStubs.generateRequest(0, 1L, "000-0000-0000");
        doReturn(Optional.empty()).when(userRepository).findByPhoneNo(any(String.class));
        doReturn(Optional.empty()).when(deptRepository).findById(any(Long.class));
        // when
        DeptException exception = assertThrows(DeptException.class, () -> userService.addUser(req));

        // then
        assertEquals(DeptErrorResult.NO_SUCH_DEPT_ID, exception.getErrorResult());
    }

    @Test
    @DisplayName("유저 생성 성공")
    void addUser_success() {
        // given
        DeptEntity deptEntity = DeptStubs.generateStub();
        UserEntity userEntity = UserStubs.generateStub(UserRole.STUDENT, "000-0000-0000", deptEntity);
        UserRequest req = UserStubs.generateRequest(0, deptEntity.getId(), "000-0000-0000");
        doReturn(Optional.of(deptEntity)).when(deptRepository).findById(any(Long.class));
        doReturn(userEntity).when(userRepository).save(any(UserEntity.class));

        // when
        UserDetailResponse res = userService.addUser(req);

        // then
        assertNotNull(res.getId());
        assertEquals(req.getAddress(), res.getAddress());
        assertEquals(req.getDeptId(), res.getDept().getId());
        assertEquals(UserRole.STUDENT, res.getUserRole());
        assertEquals(req.getPhoneNo(), res.getPhoneNo());
        assertEquals(req.getBirth(), res.getBirth());
        assertEquals(req.getName(), res.getName());
    }
}
