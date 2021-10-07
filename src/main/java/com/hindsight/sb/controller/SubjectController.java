package com.hindsight.sb.controller;

import com.hindsight.sb.dto.course.CourseRequest;
import com.hindsight.sb.dto.course.CourseResponse;
import com.hindsight.sb.dto.subject.SubjectDetailResponse;
import com.hindsight.sb.dto.subject.SubjectRequest;
import com.hindsight.sb.dto.user.UserBriefResponse;
import com.hindsight.sb.service.CourseService;
import com.hindsight.sb.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequiredArgsConstructor
@RestController
@RequestMapping("/subject")
public class SubjectController {

    private final SubjectService subjectService;
    private final CourseService courseService;

    @PostMapping
    public ResponseEntity<EntityModel<SubjectDetailResponse>> addSubject(@RequestBody @Valid SubjectRequest request, Errors errors) {
        if (errors.hasErrors())
            return ResponseEntity.badRequest().build();

        SubjectDetailResponse res = subjectService.addSubject(request);

        return ResponseEntity
                .created(linkTo(methodOn(SubjectController.class).getSubject(res.getId())).withSelfRel().toUri())
                .body(
                        EntityModel.of(res)
                                .add(linkTo(methodOn(SubjectController.class).getSubject(res.getId())).withSelfRel())
                                .add(linkTo(methodOn(UserController.class).getUser(res.getProf().getId())).withRel("prof"))
                );
    }

    @PostMapping("/course")
    public ResponseEntity<EntityModel<CourseResponse>> enrollCourse(@RequestBody @Valid CourseRequest req, Errors errors) {
        if (errors.hasErrors())
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(
                EntityModel.of(courseService.enrollCourse(req))
                        .add(linkTo(methodOn(this.getClass()).getSubjectsOfStudent(req.getStudentId())).withRel("subjectList"))
                        .add(linkTo(methodOn(this.getClass()).getSubject(req.getSubjectId())).withSelfRel())
        );
    }

    @GetMapping("/{subjectId}")
    public ResponseEntity<EntityModel<SubjectDetailResponse>> getSubject(@PathVariable Long subjectId) {
        return ResponseEntity.ok(
                EntityModel.of(subjectService.getSubject(subjectId))
                        .add(linkTo(methodOn(this.getClass()).getSubject(subjectId)).withSelfRel())
                        .add(linkTo(methodOn(this.getClass()).getStudentsOfSubject(subjectId)).withRel("studentsOfSubject"))
        );
    }

    /*
    기능 : 과목을 수강하는 학생들 조회
     */
    @GetMapping("/users/{subjectId}")
    public ResponseEntity<CollectionModel<EntityModel<UserBriefResponse>>> getStudentsOfSubject(@PathVariable Long subjectId) {
        return ResponseEntity.ok(
                CollectionModel.of(
                        courseService.getStudentsOfSubject(subjectId).stream()
                                .map(x -> EntityModel.of(x).add(linkTo(methodOn(UserController.class).getUser(x.getId())).withSelfRel()))
                                .collect(Collectors.toList())
                ).add(linkTo(methodOn(this.getClass()).getStudentsOfSubject(subjectId)).withSelfRel())
        );
    }

    @GetMapping("/subject/{studentId}")
    public ResponseEntity<EntityModel<SubjectDetailResponse>> getSubjectsOfStudent(@PathVariable Long studentId) {
        return null;
    }
}
