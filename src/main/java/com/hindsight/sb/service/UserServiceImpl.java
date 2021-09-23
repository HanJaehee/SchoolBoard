package com.hindsight.sb.service;

import com.hindsight.sb.dto.user.UserDetailResponse;
import com.hindsight.sb.dto.user.UserRequest;
import com.hindsight.sb.entity.DeptEntity;
import com.hindsight.sb.entity.UserEntity;
import com.hindsight.sb.exception.dept.DeptErrorResult;
import com.hindsight.sb.exception.dept.DeptException;
import com.hindsight.sb.exception.user.UserErrorResult;
import com.hindsight.sb.exception.user.UserException;
import com.hindsight.sb.repository.DeptRepository;
import com.hindsight.sb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final DeptRepository deptRepository;

    @Override
    public UserDetailResponse addUser(UserRequest req) {
        if (userRepository.findByPhoneNo(req.getPhoneNo()).isPresent())
            throw new UserException(UserErrorResult.DUPLICATED_PHONE_NUMBER);

        Optional<DeptEntity> optionalDept = deptRepository.findById(req.getDeptId());
        if (!optionalDept.isPresent())
            throw new DeptException(DeptErrorResult.NO_SUCH_DEPT_ID);

        UserEntity newUser = userRepository.save(UserEntity.of(req, optionalDept.get()));
        return UserDetailResponse.toDto(newUser);
    }
}
