package com.hindsight.sb.service;

import com.hindsight.sb.dto.user.UserDetailResponse;
import com.hindsight.sb.dto.user.UserRequest;

public interface UserService {

    UserDetailResponse addUser(UserRequest req);

    UserDetailResponse getUser(Long userId);

}
