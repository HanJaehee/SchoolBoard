package com.hindsight.sb.service;

import com.hindsight.sb.dto.subject.SubjectDetailResponse;
import com.hindsight.sb.dto.subject.SubjectRequest;

public interface SubjectService {
    SubjectDetailResponse addSubject(SubjectRequest req);

    SubjectDetailResponse getSubject(Long subjectId);
}
