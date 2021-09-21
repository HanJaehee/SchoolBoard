package com.hindsight.sb.service;

import com.hindsight.sb.dto.course.CourseRequest;
import com.hindsight.sb.dto.course.CourseResponse;
import com.hindsight.sb.entity.*;
import com.hindsight.sb.exception.subject.SubjectErrorResult;
import com.hindsight.sb.exception.subject.SubjectException;
import com.hindsight.sb.exception.user.UserErrorResult;
import com.hindsight.sb.exception.user.UserException;
import com.hindsight.sb.repository.CourseRepository;
import com.hindsight.sb.repository.SubjectRepository;
import com.hindsight.sb.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
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

    CourseRequest courseRequest() {
        return CourseRequest.builder()
                .studentId(1L)
                .subjectId(1L)
                .build();
    }


    UserEntity userEntity(DeptEntity dept, UserRole role, String name, String phoneNo) {
        UserEntity entity = UserEntity.builder()
                .name(name)
                .userRole(role)
                .phoneNo(phoneNo)
                .deptEntity(dept)
                .birth(LocalDate.now())
                .address("경기도 의정부")
                .deptEntity(dept)
                .build();
        ReflectionTestUtils.setField(entity, "id", 1L);
        return entity;
    }

    SubjectEntity subjectEntity(UserEntity prof) {
        SubjectEntity entity = SubjectEntity.builder()
                .name("정보보호의 기초")
                .prof(prof)
                .build();
        ReflectionTestUtils.setField(entity, "id", 1L);
        return entity;
    }


    @Test
    @DisplayName("수강 신청 성공")
    void enrollCourse_success() {
        // given
        DeptEntity dept = DeptEntity.builder().name("정보호호학과").build();
        UserEntity prof = userEntity(dept, UserRole.PROFESSOR, "한교수", "000-0000-0000");
        UserEntity student = userEntity(dept, UserRole.STUDENT, "김학생", "111-1111-1111");
        SubjectEntity subject = subjectEntity(prof);
        CourseSubjectEntity csEntity = CourseSubjectEntity.of(subject, student);
        List<CourseSubjectEntity> courseList = new ArrayList<>();
        courseList.add(csEntity);
        CourseRequest req = CourseRequest.builder()
                .subjectId(subject.getId())
                .studentId(student.getId())
                .build();

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
        UserException userException = assertThrows(UserException.class, () -> courseService.enrollCourse(courseRequest()));

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
        SubjectException subjectException = assertThrows(SubjectException.class, () -> courseService.enrollCourse(courseRequest()));

        // then
        assertEquals(SubjectErrorResult.NOT_EXISTS_SUBJECT, subjectException.getErrorResult());
    }

}
