package com.hindsight.sb.service;

import com.hindsight.sb.dto.subject.SubjectBriefResponse;
import com.hindsight.sb.dto.user.UserDetailResponse;
import com.hindsight.sb.dto.user.UserRequest;
import com.hindsight.sb.entity.DeptEntity;
import com.hindsight.sb.entity.UserEntity;
import com.hindsight.sb.exception.dept.DeptErrorResult;
import com.hindsight.sb.exception.dept.DeptException;
import com.hindsight.sb.exception.user.UserErrorResult;
import com.hindsight.sb.exception.user.UserException;
import com.hindsight.sb.repository.CourseRepository;
import com.hindsight.sb.repository.DeptRepository;
import com.hindsight.sb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final DeptRepository deptRepository;
    private final CourseRepository courseRepository;

    @Override
    public UserDetailResponse addUser(UserRequest req) {
        userRepository.findByPhoneNo(req.getPhoneNo())
                .ifPresent((user) -> {
                    throw new UserException(UserErrorResult.DUPLICATED_PHONE_NUMBER);
                });

        DeptEntity deptEntity = deptRepository.findById(req.getDeptId())
                .orElseThrow(() -> new DeptException(DeptErrorResult.NO_SUCH_DEPT_ID));

        UserEntity newUser = userRepository.save(UserEntity.of(req, deptEntity));
        return UserDetailResponse.toDto(newUser, Collections.emptyList());
    }

    @Override
    public UserDetailResponse getUser(Long userId) {
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(() -> new UserException(UserErrorResult.NOT_EXISTS_USER));
        List<SubjectBriefResponse> subjectList = courseRepository.findAllByStudent(userEntity).stream()
                .map(course -> SubjectBriefResponse.toDto(course.getSubject()))
                .collect(Collectors.toList());

        return UserDetailResponse.toDto(userEntity, subjectList);
    }
}
