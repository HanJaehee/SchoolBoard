package com.hindsight.sb.controller;

import com.google.gson.Gson;
import com.hindsight.sb.dto.dept.DeptRequest;
import com.hindsight.sb.dto.dept.DeptResponse;
import com.hindsight.sb.service.DeptService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    private Gson gson;

    private DeptResponse deptResponse() {
        return DeptResponse.builder()
                .id(-1L)
                .name(name)
                .build();
    }

    @BeforeEach
    void init() {
        gson = new Gson();
        mockMvc = MockMvcBuilders.standaloneSetup(deptController).build();
    }

    @Test
    @DisplayName("등록 실패 - 전공 이름이 Null")
    void addDept_fail_nameIsNull() throws Exception {
        // given
        final String url = "/dept";
        DeptRequest request = DeptRequest.builder().build();
        // when
        ResultActions resultActions = mockMvc.perform(
                post(url)
                        .content(gson.toJson(request))
                        .contentType(MediaType.APPLICATION_JSON)
        );
        // then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("등록 성공")
    void addDept_success() throws Exception {
        // given
        final String url = "/dept";
        DeptRequest request = DeptRequest.builder().name(name).build();
        doReturn(deptResponse()).when(deptService).addDept(any(DeptRequest.class));
        // when
        ResultActions resultActions = mockMvc.perform(
                post(url)
                        .content(gson.toJson(request))
                        .contentType(MediaType.APPLICATION_JSON)
        );
        // then
        resultActions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("name").value(name))
                .andExpect(jsonPath("id").exists())
        ;
    }

}
