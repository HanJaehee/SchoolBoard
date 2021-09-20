package com.hindsight.sb.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hindsight.sb.dto.subject.SubjectRequest;
import com.hindsight.sb.dto.subject.SubjectResponse;
import com.hindsight.sb.exception.GlobalExceptionHandler;
import com.hindsight.sb.service.SubjectService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockitoSettings
public class SubjectControllerTest {

    private final Long subjectId = 1L;
    private final String subjectName = "정보보호의 기초";
    @InjectMocks
    private SubjectController subjectController;
    @Mock
    private SubjectService subjectService;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    SubjectRequest subjectRequest() {
        return SubjectRequest.builder().name(subjectName).build();
    }

    SubjectResponse subjectResponse() {
        return SubjectResponse.builder().id(subjectId).name(subjectName).build();
    }

    @BeforeEach
    void init() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(subjectController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("과목 추가 실패 - 잘못된 요청")
    void addSubject_fail_invalidRequest() throws Exception {
        // given
        final String uri = "/subject";
        SubjectRequest req = SubjectRequest.builder().build();
//        doThrow().when(subjectService).addSubject(any(SubjectRequest.class));
        // when
        ResultActions perform = mockMvc.perform(
                post(uri)
                        .content(objectMapper.writeValueAsString(req))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print());
        // then
        perform
                .andExpect(status().isBadRequest());

    }

    @Test
    @DisplayName("과목 추가 성공")
    void addSubject_success() throws Exception {
        // given
        final String uri = "/subject";
        SubjectRequest req = subjectRequest();
        SubjectResponse res = subjectResponse();
        doReturn(res).when(subjectService).addSubject(any(SubjectRequest.class));
        // when
        ResultActions perform = mockMvc.perform(
                post(uri)
                        .content(objectMapper.writeValueAsString(req))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print());
        // then
        perform
                .andExpect(jsonPath("id").value(subjectId))
                .andExpect(jsonPath("name").value(subjectName))
                .andExpect(jsonPath("links[0].rel").exists())
                .andExpect(jsonPath("links[0].href").exists())
        ;
    }
}