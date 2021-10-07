package com.hindsight.sb.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hindsight.sb.dto.dept.DeptRequest;
import com.hindsight.sb.dto.dept.DeptResponse;
import com.hindsight.sb.exception.GlobalExceptionHandler;
import com.hindsight.sb.exception.dept.DeptErrorResult;
import com.hindsight.sb.exception.dept.DeptException;
import com.hindsight.sb.service.DeptService;
import com.hindsight.sb.stub.DeptStubs;
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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.LongStream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockitoSettings
public class DeptControllerTest {

    private final String name = "정보보호학과";
    @InjectMocks
    private DeptController deptController;
    @Mock
    private DeptService deptService;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void init() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders
                .standaloneSetup(deptController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .addFilters(new CharacterEncodingFilter("UTF-8", true)).build();
    }

    @Test
    @DisplayName("등록 실패 - 전공 이름 중복")
    void addDept_fail_duplicateDeptName() throws Exception {
        // given
        final String url = "/dept";
        doThrow(new DeptException(DeptErrorResult.DUPLICATED_NAME)).when(deptService).addDept(any(DeptRequest.class));
        DeptRequest request = DeptStubs.generateRequest();
        // when
        ResultActions perform = mockMvc.perform(post(url)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
        // then
        perform
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value(DeptErrorResult.DUPLICATED_NAME.getMessage()))
        ;

    }

    @Test
    @DisplayName("전공 등록 실패 - 전공 이름이 Null")
    void addDept_fail_nameIsNull() throws Exception {
        // given
        final String url = "/dept";
        DeptRequest request = DeptRequest.builder().build();
        // when
        ResultActions resultActions = mockMvc.perform(
                post(url)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
        );
        // then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("전공 등록 성공")
    void addDept_success() throws Exception {
        // given
        final String url = "/dept";
        DeptRequest request = DeptStubs.generateRequest();
        DeptResponse response = DeptStubs.generateResponse(1L);
        doReturn(response).when(deptService).addDept(any(DeptRequest.class));
        // when
        ResultActions resultActions = mockMvc.perform(
                post(url)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print());
        // then
        resultActions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("name").value(name))
                .andExpect(jsonPath("id").exists())
        ;
    }

    @Test
    @DisplayName("아이디로 전공 조회 성공")
    void getDeptById_success() throws Exception {
        // given
        final String uri = "/dept/1";
        DeptResponse response = DeptStubs.generateResponse(1L);
        doReturn(response).when(deptService).getDeptById(any(Long.class));
        // when
        ResultActions perform = mockMvc
                .perform(
                        get(uri)
                                .accept(MediaType.APPLICATION_JSON)
                ).andDo(print());
        // then
        perform
                .andExpect(jsonPath("id").value(1L))
                .andExpect(jsonPath("name").value(response.getName()))
                .andExpect(jsonPath("links[0].rel").value("self"))
                .andExpect(jsonPath("links[0].href").exists())
        ;
    }

    @Test
    @DisplayName("이름으로 전공 조회 성공")
    void getAllDeptByName_success() throws Exception {
        // given
        final String uri = "/dept/search";
        List<DeptResponse> deptResponseList = new ArrayList<>();
        LongStream.range(1, 10).forEach(x -> deptResponseList.add(DeptStubs.generateResponse(x)));
        doReturn(deptResponseList).when(deptService).getAllDeptByName(any(String.class));
        // when
        ResultActions perform = mockMvc.perform(
                get(uri)
                        .param("keyword", "정보보호학")
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(print());
        // then
        perform
                .andExpect(jsonPath("content").exists())
                .andExpect(jsonPath("content[0].links[0].rel").value("self"))
                .andExpect(jsonPath("content[0].links[0].href").exists())
                .andExpect(jsonPath("links[0].rel").value("self"))
                .andExpect(jsonPath("links[0].href").exists())
        ;
    }


}
