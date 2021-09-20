package com.hindsight.sb.service;

import com.hindsight.sb.dto.user.UserRequest;
import com.hindsight.sb.dto.user.UserResponse;
import com.hindsight.sb.entity.DeptEntity;
import com.hindsight.sb.entity.UserEntity;
import com.hindsight.sb.entity.UserRole;
import com.hindsight.sb.exception.dept.DeptErrorResult;
import com.hindsight.sb.exception.dept.DeptException;
import com.hindsight.sb.exception.user.UserErrorResult;
import com.hindsight.sb.exception.user.UserException;
import com.hindsight.sb.repository.DeptRepository;
import com.hindsight.sb.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@MockitoSettings
public class UserServiceTest {

    private final LocalDate birth = LocalDate.now();
    private final String name = "한재희";
    private final String address = "의정부시 장금로";
    private final String phoneNo = "000-0000-0000";
    private final String deptName = "정보보호학과";
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private DeptRepository deptRepository;

    UserEntity userEntity(DeptEntity deptEntity) {
        UserEntity userEntity = UserEntity.builder()
                .address(address)
                .name(name)
                .phoneNo(phoneNo)
                .birth(birth)
                .userRole(UserRole.STUDENT)
                .deptEntity(deptEntity)
                .build();
        ReflectionTestUtils.setField(userEntity, "id", 1L);
        return userEntity;
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserServiceImpl(userRepository, deptRepository);
    }

    @Test
    @DisplayName("유저 생성 실패 - Duplicate PhoneNo")
    void createUser_fail_duplicatePhoneNo() {
        // given
        doReturn(Optional.of(UserEntity.builder().build())).when(userRepository).findByPhoneNo(any(String.class));
        final UserRequest req = UserRequest.builder()
                .address(address)
                .name(name)
                .phoneNo(phoneNo)
                .birth(birth.toString())
                .type(0)
                .deptId(1L)
                .build();
        // when
        UserException userException = assertThrows(UserException.class, () -> userService.addUser(req));

        // then
        assertEquals(UserErrorResult.DUPLICATED_PHONE_NUMBER, userException.getErrorResult());
    }

    @Test
    @DisplayName("유저 생성 실패 - No Dept")
    void createUser_fail_noSuchDept() {
        // given
        doReturn(Optional.empty()).when(userRepository).findByPhoneNo(any(String.class));
        doReturn(Optional.empty()).when(deptRepository).findById(any(Long.class));
        final UserRequest req = UserRequest.builder()
                .address(address)
                .name(name)
                .phoneNo(phoneNo)
                .birth(birth.toString())
                .type(0)
                .deptId(-1L)
                .build();
        // when
        DeptException exception = assertThrows(DeptException.class, () -> userService.addUser(req));

        // then
        assertEquals(DeptErrorResult.NO_SUCH_DEPT_ID, exception.getErrorResult());
    }

    @Test
    @DisplayName("유저 생성 성공")
    void addUser_success() {
        // given
        final Long deptId = 1L;
        DeptEntity deptEntity = DeptEntity.builder()
                .name(deptName).build();
        ReflectionTestUtils.setField(deptEntity, "id", deptId);

        doReturn(Optional.of(deptEntity)).when(deptRepository).findById(any(Long.class));
        doReturn(userEntity(deptEntity)).when(userRepository).save(any(UserEntity.class));

        // when
        final UserRequest req = UserRequest.builder()
                .address(address)
                .name(name)
                .phoneNo(phoneNo)
                .birth(birth.toString())
                .type(0)
                .deptId(deptId)
                .build();
        UserResponse res = userService.addUser(req);

        // then
        assertNotNull(res.getId());
        assertEquals(req.getAddress(), res.getAddress());
        assertEquals(req.getDeptId(), res.getDept().getId());
        assertEquals(UserRole.STUDENT, res.getUserRole());
        assertEquals(req.getPhoneNo(), res.getPhoneNo());
        assertEquals(req.getBirth(), res.getBirth().toString());
        assertEquals(req.getName(), res.getName());
    }
}
