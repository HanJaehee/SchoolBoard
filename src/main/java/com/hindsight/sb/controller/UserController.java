package com.hindsight.sb.controller;

import com.hindsight.sb.dto.user.UserDetailResponse;
import com.hindsight.sb.dto.user.UserRequest;
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
    public ResponseEntity<EntityModel<UserDetailResponse>> addUser(@RequestBody @Valid UserRequest req, Errors errors) {
        if (errors.hasErrors())
            return ResponseEntity.badRequest().build();

        UserDetailResponse res = userService.addUser(req);

        EntityModel<UserDetailResponse> model = EntityModel.of(res);
        Link selfLink = linkTo(methodOn(this.getClass()).getUser(res.getId())).withSelfRel();
        Link deptLink = linkTo(methodOn(DeptController.class).getDept(res.getDept().getId())).withRel("dept");
        model.add(selfLink);
        model.add(deptLink);

        return ResponseEntity.created(selfLink.toUri()).body(model);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<EntityModel<UserDetailResponse>> getUser(@PathVariable Long userId) {
        return ResponseEntity.ok(
                EntityModel.of(userService.getUser(userId))
                        .add(linkTo(methodOn(this.getClass()).getUser(userId)).withSelfRel())
        );
    }
}
