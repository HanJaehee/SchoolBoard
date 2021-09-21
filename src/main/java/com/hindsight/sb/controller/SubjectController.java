package com.hindsight.sb.controller;

import com.hindsight.sb.dto.subject.SubjectRequest;
import com.hindsight.sb.dto.subject.SubjectResponse;
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

    @PostMapping
    public ResponseEntity<EntityModel<SubjectResponse>> addSubject(@RequestBody @Valid SubjectRequest request, Errors errors) {
        if (errors.hasErrors())
            return ResponseEntity.badRequest().build();

        SubjectResponse res = subjectService.addSubject(request);
        EntityModel<SubjectResponse> model = EntityModel.of(res);
        Link selfLink = linkTo(methodOn(SubjectController.class).getSubject(res.getId())).withSelfRel();
        Link superLink = linkTo(methodOn(UserController.class).getUser(res.getSupervisor().getId())).withRel("supervisor");
        model.add(selfLink);
        model.add(superLink);

        return ResponseEntity.created(selfLink.toUri()).body(model);


    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<SubjectResponse>> getSubject(@PathVariable Long id) {
        return null;
    }
}
