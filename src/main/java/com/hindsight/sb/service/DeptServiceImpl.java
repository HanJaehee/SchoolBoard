package com.hindsight.sb.service;

import com.hindsight.sb.dto.dept.DeptRequest;
import com.hindsight.sb.dto.dept.DeptResponse;
import com.hindsight.sb.entity.DeptEntity;
import com.hindsight.sb.exception.dept.DeptErrorResult;
import com.hindsight.sb.exception.dept.DeptException;
import com.hindsight.sb.repository.DeptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Transactional
@Service
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

    @Override
    public DeptResponse getDeptById(Long deptId) {
        Optional<DeptEntity> optionalDept = deptRepository.findById(deptId);
        if (!optionalDept.isPresent())
            throw new DeptException(DeptErrorResult.NO_SUCH_DEPT_ID);
        return DeptResponse.toDto(optionalDept.get());
    }

    @Override
    public List<DeptResponse> getAllDeptByName(String name) {
        return deptRepository.findAllByName(name).stream()
                .map(DeptResponse::toDto)
                .collect(Collectors.toList());
    }


}
