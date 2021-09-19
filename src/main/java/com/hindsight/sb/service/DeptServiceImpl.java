package com.hindsight.sb.service;

import com.hindsight.sb.dto.dept.DeptRequest;
import com.hindsight.sb.dto.dept.DeptResponse;
import com.hindsight.sb.entity.DeptEntity;
import com.hindsight.sb.exception.dept.DeptErrorResult;
import com.hindsight.sb.exception.dept.DeptException;
import com.hindsight.sb.repository.DeptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DeptServiceImpl implements DeptService {

    private final DeptRepository deptRepository;

    @Override
    public DeptResponse addDept(DeptRequest req) {
        if (deptRepository.findByName(req.getName()).isPresent())
            throw new DeptException(DeptErrorResult.DUPLICATED_NAME);

        DeptEntity newDept = DeptEntity.builder()
                .name(req.getName())
                .build();

        DeptEntity savedDept = deptRepository.save(newDept);

        return DeptResponse.toDto(savedDept);
    }
}
