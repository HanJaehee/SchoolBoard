package com.hindsight.sb.controller;

import com.hindsight.sb.dto.course.CourseRequest;
import com.hindsight.sb.dto.course.CourseResponse;
import com.hindsight.sb.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequiredArgsConstructor
@RestController
@RequestMapping("/course")
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    public ResponseEntity<EntityModel<CourseResponse>> enrollCourse(@RequestBody @Valid CourseRequest req, Errors errors) {
        if (errors.hasErrors())
            return ResponseEntity.badRequest().build();

        CourseResponse courseResponse = courseService.enrollCourse(req);
        EntityModel<CourseResponse> model = EntityModel.of(courseResponse);
        Link courseList = linkTo(methodOn(CourseController.class).getCoursesByStudent(req.getStudentId())).withRel("course_list");
        model.add(courseList);

        return ResponseEntity.ok(model);
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<EntityModel<CourseResponse>> getCoursesByStudent(@PathVariable Long studentId) {
        return null;
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<EntityModel<CourseResponse>> getStudentsByCourse(@PathVariable Long courseId) {
        return null;
    }

}
