package com.hindsight.sb.controller;

import com.hindsight.sb.dto.dept.DeptRequest;
import com.hindsight.sb.dto.dept.DeptResponse;
import com.hindsight.sb.service.DeptService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

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
        URI selfUri = linkTo(methodOn(this.getClass()).getDept(deptResponse.getId())).withSelfRel().toUri();

        return ResponseEntity.created(selfUri).body(model);
    }

    public ResponseEntity<DeptResponse> getDept(Long id) {
        return null;
    }
}
