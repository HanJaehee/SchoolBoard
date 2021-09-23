package com.hindsight.sb.service;

import com.hindsight.sb.dto.course.CourseRequest;
import com.hindsight.sb.dto.course.CourseResponse;
import com.hindsight.sb.dto.user.UserBriefResponse;

import java.util.List;

public interface CourseService {
    CourseResponse enrollCourse(CourseRequest req);

    List<UserBriefResponse> getStudentsOfSubject(Long subjectId);
}
