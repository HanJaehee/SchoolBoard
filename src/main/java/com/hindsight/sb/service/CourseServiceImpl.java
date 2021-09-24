package com.hindsight.sb.service;

import com.hindsight.sb.dto.course.CourseRequest;
import com.hindsight.sb.dto.course.CourseResponse;
import com.hindsight.sb.dto.subject.SubjectResponse;
import com.hindsight.sb.dto.user.UserBriefResponse;
import com.hindsight.sb.entity.CourseSubjectEntity;
import com.hindsight.sb.entity.SubjectEntity;
import com.hindsight.sb.entity.UserEntity;
import com.hindsight.sb.entity.UserRole;
import com.hindsight.sb.exception.subject.SubjectErrorResult;
import com.hindsight.sb.exception.subject.SubjectException;
import com.hindsight.sb.exception.user.UserErrorResult;
import com.hindsight.sb.exception.user.UserException;
import com.hindsight.sb.repository.CourseRepository;
import com.hindsight.sb.repository.SubjectRepository;
import com.hindsight.sb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;

    @Override
    public CourseResponse enrollCourse(CourseRequest req) {
        Optional<UserEntity> optionalStudent = userRepository.findById(req.getStudentId());
        if (!optionalStudent.isPresent())
            throw new UserException(UserErrorResult.NOT_EXISTS_USER);
        else if (optionalStudent.get().getUserRole() == UserRole.PROFESSOR)
            throw new UserException(UserErrorResult.IS_NOT_STUDENT);

        Optional<SubjectEntity> optionalSubject = subjectRepository.findById(req.getSubjectId());
        if (!optionalSubject.isPresent())
            throw new SubjectException(SubjectErrorResult.NOT_EXISTS_SUBJECT);

        CourseSubjectEntity entity = CourseSubjectEntity.of(optionalSubject.get(), optionalStudent.get());
        courseRepository.save(entity);

        List<CourseSubjectEntity> allByStudent = courseRepository.findAllByStudent(optionalStudent.get());

        return CourseResponse.builder()
                .subjectList(allByStudent.stream().map(x -> SubjectResponse.toDto(x.getSubject())).collect(Collectors.toList()))
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserBriefResponse> getStudentsOfSubject(Long subjectId) {
        Optional<SubjectEntity> optionalSubject = subjectRepository.findById(subjectId);
        if (!optionalSubject.isPresent())
            throw new SubjectException(SubjectErrorResult.NOT_EXISTS_SUBJECT);

        List<CourseSubjectEntity> allBySubject = courseRepository.findAllBySubject(optionalSubject.get());

        return allBySubject.stream().map(x -> UserBriefResponse.toDto(x.getStudent())).collect(Collectors.toList());
//        return SubjectAndStudentListResponse.builder()
//                .studentList(allBySubject.stream().map(x -> UserBriefResponse.toDto(x.getStudent())).collect(Collectors.toList()))
//                .subjectResponse(SubjectResponse.toDto(optionalSubject.get()))
//                .build();
    }
}
