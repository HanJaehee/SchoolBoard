package com.hindsight.sb.service;

import com.hindsight.sb.dto.subject.SubjectRequest;
import com.hindsight.sb.dto.subject.SubjectResponse;

public interface SubjectService {
    SubjectResponse addSubject(SubjectRequest req);
}
