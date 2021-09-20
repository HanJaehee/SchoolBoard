package com.hindsight.sb.service;

import com.hindsight.sb.dto.subject.SubjectRequest;
import com.hindsight.sb.dto.subject.SubjectResponse;
import com.hindsight.sb.entity.SubjectEntity;
import com.hindsight.sb.exception.subject.SubjectErrorResult;
import com.hindsight.sb.exception.subject.SubjectException;
import com.hindsight.sb.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;

    @Override
    public SubjectResponse addSubject(SubjectRequest req) {
        if (subjectRepository.findByName(req.getName()).isPresent())
            throw new SubjectException(SubjectErrorResult.DUPLICATE_NAME);

        SubjectEntity savedEntity = subjectRepository.save(SubjectEntity.of(req));
        return SubjectResponse.toDto(savedEntity);
    }
}
