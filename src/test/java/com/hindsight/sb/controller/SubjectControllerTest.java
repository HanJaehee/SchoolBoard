package com.hindsight.sb.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hindsight.sb.dto.course.CourseRequest;
import com.hindsight.sb.dto.course.CourseResponse;
import com.hindsight.sb.dto.subject.SubjectRequest;
import com.hindsight.sb.dto.subject.SubjectResponse;
import com.hindsight.sb.dto.user.UserBriefResponse;
import com.hindsight.sb.exception.GlobalExceptionHandler;
import com.hindsight.sb.exception.subject.SubjectErrorResult;
import com.hindsight.sb.exception.subject.SubjectException;
import com.hindsight.sb.exception.user.UserErrorResult;
import com.hindsight.sb.exception.user.UserException;
import com.hindsight.sb.service.CourseService;
import com.hindsight.sb.service.SubjectService;
import com.hindsight.sb.stub.CourseStubs;
import com.hindsight.sb.stub.SubjectStubs;
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
public class SubjectControllerTest {

    @InjectMocks
    private SubjectController subjectController;
    @Mock
    private SubjectService subjectService;
    @Mock
    private CourseService courseService;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

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
        SubjectRequest req = SubjectStubs.generateRequest(1L);
        UserBriefResponse prof = UserStubs.generateBriefResponse("한유저", 1L);
        SubjectResponse res = SubjectStubs.generateResponse(prof, 1L);
        doReturn(res).when(subjectService).addSubject(any(SubjectRequest.class));
        // when
        ResultActions perform = mockMvc.perform(
                post(uri)
                        .content(objectMapper.writeValueAsString(req))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print());
        // then
        perform
                .andExpect(jsonPath("id").value(res.getId()))
                .andExpect(jsonPath("name").value(res.getName()))
                .andExpect(jsonPath("prof").exists())
                .andExpect(jsonPath("links[0].rel").exists())
                .andExpect(jsonPath("links[0].href").exists())
        ;
    }


    @Test
    @DisplayName("수강 신청 성공")
    void enrollCourse_success() throws Exception {
        // given
        final String uri = "/subject/course";
        CourseRequest req = CourseStubs.generateRequest(1L, 1L);
        UserBriefResponse prof = UserStubs.generateBriefResponse("한유저", 1L);

        List<SubjectResponse> subjectList = new ArrayList<>();
        LongStream.range(1L, 10L).forEach(x -> subjectList.add(SubjectStubs.generateResponse(prof, x)));
        CourseResponse res = CourseStubs.generateResponse(subjectList);
        doReturn(res).when(courseService).enrollCourse(any(CourseRequest.class));
        // when
        ResultActions perform = mockMvc.perform(
                post(uri)
                        .content(objectMapper.writeValueAsString(req))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print());
        // then
        perform
                .andExpect(jsonPath("subjectList").exists())
                .andExpect(jsonPath("links[0].rel").exists())
                .andExpect(jsonPath("links[0].href").exists())
        ;
    }


    @Test
    @DisplayName("수강 신청 실패 - Invalid Request")
    void enrollCourse_fail_invalidRequest() throws Exception {
        // given
        final String uri = "/subject/course";
        CourseRequest req = CourseRequest.builder()
                .build();
        // when
        ResultActions perform = mockMvc.perform(
                post(uri)
                        .content(objectMapper.writeValueAsString(req))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print());
        // then
        perform
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    @DisplayName("수강 신청 실패 - Not Exist Student")
    void enrollCourse_fail_NotExistStudent() throws Exception {
        // given
        final String uri = "/subject/course";
        CourseRequest req = CourseStubs.generateRequest(1L, 1L);
        doThrow(new UserException(UserErrorResult.NOT_EXISTS_USER)).when(courseService).enrollCourse(any(CourseRequest.class));
        // when
        ResultActions perform = mockMvc.perform(
                post(uri)
                        .content(objectMapper.writeValueAsString(req))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print());
        // then
        perform
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value(UserErrorResult.NOT_EXISTS_USER.getMessage()));
    }

    @Test
    @DisplayName("수강 신청 실패 - Not Exist Subject")
    void enrollCourse_fail_NotExistSubject() throws Exception {
        final String uri = "/subject/course";
        CourseRequest req = CourseStubs.generateRequest(1L, 1L);
        doThrow(new SubjectException(SubjectErrorResult.NOT_EXISTS_SUBJECT)).when(courseService).enrollCourse(any(CourseRequest.class));
        // when
        ResultActions perform = mockMvc.perform(
                post(uri)
                        .content(objectMapper.writeValueAsString(req))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print());
        // then
        perform
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value(SubjectErrorResult.NOT_EXISTS_SUBJECT.getMessage()));
    }

    @Test
    @DisplayName("과목을 수강하는 학생리스트 조회 성공")
    void getStudentListOfSubject_success() throws Exception {
        // given
        final String uri = "/subject/users/1";
        List<UserBriefResponse> studentList = new ArrayList<>();
        LongStream.range(1, 10).forEach(x -> studentList.add(UserStubs.generateBriefResponse("한학생", x)));
        doReturn(studentList).when(courseService).getStudentsOfSubject(any(Long.class));
        // when
        ResultActions perform = mockMvc.perform(
                get(uri).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
        ).andDo(print());
        // then
        perform
                .andExpect(jsonPath("content").isArray())
                .andExpect(jsonPath("links[0].rel").exists())
        ;
    }


}
