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
import com.hindsight.sb.stub.DeptStubs;
import com.hindsight.sb.stub.UserStubs;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockitoSettings
public class UserControllerTest {

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

    @Test
    @DisplayName("유저 생성 실패 - phoneNo 중복")
    void addUser_fail_duplicatePhoneNo() throws Exception {
        // given
        final String url = "/user";
        final UserRequest req = UserStubs.generateRequest(0, 1L, "000-0000-0000");
        doThrow(new UserException(UserErrorResult.DUPLICATED_PHONE_NUMBER)).when(userService).addUser(any(UserRequest.class));
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
        final UserRequest req = UserStubs.generateRequest(0, 1L, "000-000-0000");
        DeptResponse deptResponse = DeptStubs.generateResponse(1L);
        UserDetailResponse uds = UserStubs.generateDetailResponse(1L, "000-0000-0000", UserRole.STUDENT, deptResponse);
        doReturn(uds).when(userService).addUser(any(UserRequest.class));
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
                .andExpect(jsonPath("address").value(uds.getAddress()))
                .andExpect(jsonPath("phoneNo").value(uds.getPhoneNo()))
                .andExpect(jsonPath("birth").value(uds.getBirth()))
                .andExpect(jsonPath("userRole").value(uds.getUserRole().toString()))
                .andExpect(jsonPath("dept.id").value(uds.getDept().getId()))
                .andExpect(jsonPath("dept.name").value(uds.getDept().getName()))
                .andExpect(jsonPath("links[0].rel").value("self"))
                .andExpect(jsonPath("links[0].href").exists())
                .andExpect(jsonPath("links[1].rel").value("dept"))
                .andExpect(jsonPath("links[1].href").exists());
        ;
    }

}
