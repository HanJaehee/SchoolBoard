package com.hindsight.sb.service;

import com.hindsight.sb.dto.dept.DeptRequest;
import com.hindsight.sb.dto.dept.DeptResponse;

public interface DeptService {

    DeptResponse addDept(DeptRequest req);
}
