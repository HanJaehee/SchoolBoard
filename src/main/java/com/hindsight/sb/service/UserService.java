package com.hindsight.sb.service;

import com.hindsight.sb.dto.user.UserRequest;
import com.hindsight.sb.dto.user.UserResponse;

public interface UserService {

    UserResponse addUser(UserRequest req);

}
