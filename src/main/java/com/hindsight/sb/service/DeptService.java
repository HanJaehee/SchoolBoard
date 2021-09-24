package com.hindsight.sb.service;

import com.hindsight.sb.dto.dept.DeptRequest;
import com.hindsight.sb.dto.dept.DeptResponse;

import java.util.List;

public interface DeptService {

    DeptResponse addDept(DeptRequest req);

    DeptResponse getDeptById(Long deptId);

    List<DeptResponse> getAllDeptByName(String name);
}
