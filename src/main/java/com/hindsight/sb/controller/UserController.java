package com.hindsight.sb.controller;

import com.hindsight.sb.dto.user.UserRequest;
import com.hindsight.sb.dto.user.UserResponse;
import com.hindsight.sb.exception.user.UserErrorResult;
import com.hindsight.sb.exception.user.UserException;
import com.hindsight.sb.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/test")
    public String hi() {
        throw new UserException(UserErrorResult.DUPLICATED_PHONE_NUMBER);
    }

    @PostMapping
    public ResponseEntity<EntityModel<UserResponse>> addUser(@RequestBody @Valid UserRequest req, Errors errors) {
        if (errors.hasErrors())
            return ResponseEntity.badRequest().build();

        UserResponse res = userService.addUser(req);

        EntityModel<UserResponse> model = EntityModel.of(res);
        Link selfLink = linkTo(methodOn(this.getClass()).getUser(res.getId())).withSelfRel();
        Link deptLink = linkTo(methodOn(DeptController.class).getDept(res.getDept().getId())).withRel("dept");
        model.add(selfLink);
        model.add(deptLink);

        return ResponseEntity.created(selfLink.toUri()).body(model);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<UserResponse>> getUser(@PathVariable Long id) {
        return null;
    }
}
