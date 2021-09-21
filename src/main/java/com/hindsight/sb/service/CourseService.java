package com.hindsight.sb.service;

import com.hindsight.sb.dto.course.CourseRequest;
import com.hindsight.sb.dto.course.CourseResponse;

public interface CourseService {
    CourseResponse enrollCourse(CourseRequest req);
}
