package com.hindsight.sb.service;

import com.hindsight.sb.dto.subject.SubjectRequest;
import com.hindsight.sb.dto.subject.SubjectResponse;
import com.hindsight.sb.entity.SubjectEntity;
import com.hindsight.sb.entity.UserEntity;
import com.hindsight.sb.exception.subject.SubjectErrorResult;
import com.hindsight.sb.exception.subject.SubjectException;
import com.hindsight.sb.exception.user.UserErrorResult;
import com.hindsight.sb.exception.user.UserException;
import com.hindsight.sb.repository.SubjectRepository;
import com.hindsight.sb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;
    private final UserRepository userRepository;

    @Override
    public SubjectResponse addSubject(SubjectRequest req) {
        if (subjectRepository.findByName(req.getName()).isPresent())
            throw new SubjectException(SubjectErrorResult.DUPLICATE_NAME);

        Optional<UserEntity> optionalProf = userRepository.findById(req.getProfId());
        if (!optionalProf.isPresent())
            throw new UserException(UserErrorResult.NOT_EXISTS_USER);

        SubjectEntity savedEntity = subjectRepository.save(SubjectEntity.of(req, optionalProf.get()));
        return SubjectResponse.toDto(savedEntity);
    }
}
