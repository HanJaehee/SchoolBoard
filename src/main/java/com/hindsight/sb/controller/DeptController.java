package com.hindsight.sb.controller;

import com.hindsight.sb.dto.dept.DeptRequest;
import com.hindsight.sb.dto.dept.DeptResponse;
import com.hindsight.sb.service.DeptService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequiredArgsConstructor
@RestController
@RequestMapping("/dept")
public class DeptController {

    private final DeptService deptService;

    @PostMapping
    public ResponseEntity<EntityModel<DeptResponse>> addDept(@RequestBody @Valid DeptRequest req) {
        DeptResponse deptResponse = deptService.addDept(req);

        EntityModel<DeptResponse> model = EntityModel.of(deptResponse);
        Link link = linkTo(methodOn(this.getClass()).getDept(deptResponse.getId())).withSelfRel();
        model.add(link);
        return ResponseEntity.created(link.toUri()).body(model);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeptResponse> getDept(@PathVariable Long id) {
        return null;
    }
}
