package com.hindsight.sb.controller;

import com.hindsight.sb.dto.dept.DeptRequest;
import com.hindsight.sb.dto.dept.DeptResponse;
import com.hindsight.sb.service.DeptService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("/{deptId}")
    public ResponseEntity<EntityModel<DeptResponse>> getDept(@PathVariable Long deptId) {
        DeptResponse deptById = deptService.getDeptById(deptId);
        EntityModel<DeptResponse> model = EntityModel.of(deptById);
        model.add(linkTo(methodOn(this.getClass()).getDept(deptId)).withSelfRel());
        return ResponseEntity.ok(model);
    }

    @GetMapping("/search")
    public ResponseEntity<CollectionModel<EntityModel<DeptResponse>>> getAllDeptByName(@RequestParam String name) {
        List<DeptResponse> allDeptByName = deptService.getAllDeptByName(name);
        CollectionModel<EntityModel<DeptResponse>> model = CollectionModel.of(
                allDeptByName.stream()
                        .map(x -> EntityModel.of(x).add(linkTo(methodOn(this.getClass()).getDept(x.getId())).withSelfRel()))
                        .collect(Collectors.toList()));
        model.add(linkTo(methodOn(this.getClass()).getAllDeptByName(name)).withSelfRel());
        return ResponseEntity.ok(model);
    }
}
