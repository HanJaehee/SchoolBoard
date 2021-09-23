package com.hindsight.sb.service;

import com.hindsight.sb.dto.course.CourseRequest;
import com.hindsight.sb.dto.course.CourseResponse;
import com.hindsight.sb.dto.user.UserBriefResponse;
import com.hindsight.sb.entity.*;
import com.hindsight.sb.exception.subject.SubjectErrorResult;
import com.hindsight.sb.exception.subject.SubjectException;
import com.hindsight.sb.exception.user.UserErrorResult;
import com.hindsight.sb.exception.user.UserException;
import com.hindsight.sb.repository.CourseRepository;
import com.hindsight.sb.repository.SubjectRepository;
import com.hindsight.sb.repository.UserRepository;
import com.hindsight.sb.stub.CourseStubs;
import com.hindsight.sb.stub.DeptStubs;
import com.hindsight.sb.stub.SubjectStubs;
import com.hindsight.sb.stub.UserStubs;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@MockitoSettings
public class CourseServiceTest {

    @InjectMocks
    private CourseServiceImpl courseService;
    @Mock
    private CourseRepository courseRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private SubjectRepository subjectRepository;


    @Test
    @DisplayName("수강 신청 성공")
    void enrollCourse_success() {
        // given
        DeptEntity dept = DeptStubs.generateEntity();
        UserEntity prof = UserStubs.generateEntity(UserRole.PROFESSOR, "000-0000-0000", dept);
        UserEntity student = UserStubs.generateEntity(UserRole.STUDENT, "111-1111-1111", dept);
        SubjectEntity subject = SubjectStubs.generateSubject(prof);
        CourseSubjectEntity csEntity = CourseStubs.generateEntity(student, subject);
        List<CourseSubjectEntity> courseList = new ArrayList<>();
        courseList.add(csEntity);
        CourseRequest req = CourseStubs.generateRequest(student.getId(), subject.getId());

        doReturn(Optional.of(student)).when(userRepository).findById(any(Long.class));
        doReturn(Optional.of(subject)).when(subjectRepository).findById(any(Long.class));
        doReturn(csEntity).when(courseRepository).save(any(CourseSubjectEntity.class));
        doReturn(courseList).when(courseRepository).findAllByStudent(student);
        // when
        CourseResponse courseResponse = courseService.enrollCourse(req);

        // then
        assertEquals(courseResponse.getSubjectList().size(), 1);
    }

    @Test
    @DisplayName("수강 신청 실패 - Student Not Exist")
    void enrollCourse_fail_studentNotExist() {
        // given
        doReturn(Optional.empty()).when(userRepository).findById(any(Long.class));

        // when
        UserException userException = assertThrows(UserException.class, () -> courseService.enrollCourse(CourseStubs.generateRequest(1L, 1L)));

        // then
        assertEquals(UserErrorResult.NOT_EXISTS_USER, userException.getErrorResult());
    }

    @Test
    @DisplayName("수강 신청 실패 - Subject Not Exist")
    void enrollCourse_fail_subjectNotExist() {
        // given
        doReturn(Optional.of(UserEntity.builder().build())).when(userRepository).findById((any(Long.class)));
        doReturn(Optional.empty()).when(subjectRepository).findById(any(Long.class));

        // when
        SubjectException subjectException = assertThrows(SubjectException.class, () -> courseService.enrollCourse(CourseStubs.generateRequest(1L, 1L)));

        // then
        assertEquals(SubjectErrorResult.NOT_EXISTS_SUBJECT, subjectException.getErrorResult());
    }

    @Test
    @DisplayName("과목을 수강하는 학생 조회 성공")
    void getStudentsOfSubject_success() {
        // given
        DeptEntity dept = DeptStubs.generateEntity();
        UserEntity student1 = UserStubs.generateEntity(UserRole.STUDENT, "000-000-0000", dept);
        UserEntity student2 = UserStubs.generateEntity(UserRole.STUDENT, "222-222-2222", dept);
        UserEntity prof = UserStubs.generateEntity(UserRole.PROFESSOR, "111-111-1111", dept);
        SubjectEntity subject = SubjectStubs.generateSubject(prof);
        CourseSubjectEntity entity1 = CourseStubs.generateEntity(student1, subject);
        CourseSubjectEntity entity2 = CourseStubs.generateEntity(student2, subject);

        List<CourseSubjectEntity> courseList = new ArrayList<>();
        courseList.add(entity1);
        courseList.add(entity2);
        doReturn(courseList).when(courseRepository).findAllBySubject(any(SubjectEntity.class));
        doReturn(Optional.of(subject)).when(subjectRepository).findById(any(Long.class));
        // when

        List<UserBriefResponse> studentsOfSubject = courseService.getStudentsOfSubject(subject.getId());
        // then
        assertEquals(studentsOfSubject.size(), courseList.size());
    }

}
