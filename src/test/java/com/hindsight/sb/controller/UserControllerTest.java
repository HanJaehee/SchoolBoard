package com.hindsight.sb.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hindsight.sb.dto.dept.DeptResponse;
import com.hindsight.sb.dto.user.UserDetailResponse;
import com.hindsight.sb.dto.user.UserRequest;
import com.hindsight.sb.entity.UserRole;
import com.hindsight.sb.exception.GlobalExceptionHandler;
import com.hindsight.sb.exception.user.UserErrorResult;
import com.hindsight.sb.exception.user.UserException;
import com.hindsight.sb.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockitoSettings
public class UserControllerTest {

    private final Long userId = 1L;
    private final String name = "한재희";
    private final String address = "의정부시 장금로";
    private final String phoneNo = "000-0000-0000";
    private final String birth = LocalDate.of(2021, 11, 25).toString();
    private final Long deptId = 1L;
    private final String deptName = "정보보호학과";
    @InjectMocks
    private UserController userController;
    @Mock
    private UserService userService;
    private ObjectMapper objectMapper;
    private MockMvc mockMvc;

    @BeforeEach
    void init() {
        objectMapper = new ObjectMapper();

        mockMvc = MockMvcBuilders
                .standaloneSetup(userController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .addFilters(new CharacterEncodingFilter("UTF-8", true)).build();
    }

    DeptResponse deptResponse() {
        return DeptResponse.builder().name(deptName).id(deptId).build();
    }

    private UserDetailResponse userResponse(DeptResponse deptResponse) {
        return UserDetailResponse.builder()
                .id(userId)
                .userRole(UserRole.STUDENT)
                .address(address)
                .name(name)
                .phoneNo(phoneNo)
                .dept(deptResponse)
                .birth(birth)
                .build();
    }

    @Test
    @DisplayName("유저 생성 실패 - phoneNo 중복")
    void addUser_fail_duplicatePhoneNo() throws Exception {
        // given
        final String url = "/user";
        doThrow(new UserException(UserErrorResult.DUPLICATED_PHONE_NUMBER)).when(userService).addUser(any(UserRequest.class));
        final UserRequest req = UserRequest.builder()
                .name(name)
                .address(address)
                .phoneNo(phoneNo)
                .birth(birth)
                .type(0)
                .deptId(deptId)
                .build();
        // when
        ResultActions perform = mockMvc.perform(
                post(url)
                        .content(objectMapper.writeValueAsString(req))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print());
        // then
        perform
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value(UserErrorResult.DUPLICATED_PHONE_NUMBER.getMessage()))
        ;

    }

    @Test
    @DisplayName("유저(학생) 등록 성공")
    void addUser_success() throws Exception {
        // given
        final String url = "/user";
        final UserRequest req = UserRequest.builder()
                .name(name)
                .address(address)
                .phoneNo(phoneNo)
                .birth(birth)
                .type(0)
                .deptId(deptId)
                .build();
        doReturn(userResponse(deptResponse())).when(userService).addUser(any(UserRequest.class));
        // when
        ResultActions perform = mockMvc.perform(post(url)
                        .content(objectMapper.writeValueAsString(req))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print());

        // then
        perform
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("address").value(address))
                .andExpect(jsonPath("phoneNo").value(phoneNo))
                .andExpect(jsonPath("birth").value(birth))
                .andExpect(jsonPath("userRole").value(UserRole.STUDENT.toString()))
                .andExpect(jsonPath("dept.id").value(deptId))
                .andExpect(jsonPath("dept.name").value(deptName))
                .andExpect(jsonPath("links[0].rel").value("self"))
                .andExpect(jsonPath("links[0].href").exists())
                .andExpect(jsonPath("links[1].rel").value("dept"))
                .andExpect(jsonPath("links[1].href").exists());
        ;
    }

}
