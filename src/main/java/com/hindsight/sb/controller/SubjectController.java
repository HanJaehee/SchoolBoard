package com.hindsight.sb.controller;

import com.hindsight.sb.dto.course.CourseRequest;
import com.hindsight.sb.dto.course.CourseResponse;
import com.hindsight.sb.dto.subject.SubjectRequest;
import com.hindsight.sb.dto.subject.SubjectResponse;
import com.hindsight.sb.service.CourseService;
import com.hindsight.sb.service.SubjectService;
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
@RequestMapping("/subject")
public class SubjectController {

    private final SubjectService subjectService;
    private final CourseService courseService;

    @PostMapping
    public ResponseEntity<EntityModel<SubjectResponse>> addSubject(@RequestBody @Valid SubjectRequest request, Errors errors) {
        if (errors.hasErrors())
            return ResponseEntity.badRequest().build();

        SubjectResponse res = subjectService.addSubject(request);
        EntityModel<SubjectResponse> model = EntityModel.of(res);
        Link selfLink = linkTo(methodOn(SubjectController.class).getSubject(res.getId())).withSelfRel();
        Link profLink = linkTo(methodOn(UserController.class).getUser(res.getProf().getId())).withRel("prof");
        model.add(selfLink);
        model.add(profLink);

        return ResponseEntity.created(selfLink.toUri()).body(model);
    }

    @PostMapping("/course")
    public ResponseEntity<EntityModel<CourseResponse>> enrollCourse(@RequestBody @Valid CourseRequest req, Errors errors) {
        if (errors.hasErrors())
            return ResponseEntity.badRequest().build();

        CourseResponse courseResponse = courseService.enrollCourse(req);
        EntityModel<CourseResponse> model = EntityModel.of(courseResponse);
        Link subjectListOfStudent = linkTo(methodOn(this.getClass()).getSubjectsOfStudent(req.getStudentId())).withRel("subjectList");
        Link studentListOfSubject = linkTo(methodOn(this.getClass()).getSubject(req.getSubjectId())).withSelfRel();
        model.add(subjectListOfStudent);
        model.add(studentListOfSubject);

        return ResponseEntity.ok(model);
    }

    @GetMapping("/{subjectId}")
    public ResponseEntity<EntityModel<SubjectResponse>> getSubject(@PathVariable Long subjectId) {
        return null;
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<EntityModel<SubjectResponse>> getSubjectsOfStudent(@PathVariable Long studentId) {
        return null;
    }
}
