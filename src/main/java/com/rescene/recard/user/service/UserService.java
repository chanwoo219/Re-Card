package com.rescene.recard.user.service;

import com.rescene.recard.user.dto.UserCreateRequest;
import com.rescene.recard.user.dto.UserResponse;
import com.rescene.recard.user.dto.UserUpdateRequest;

public interface UserService {

    UserResponse create(UserCreateRequest request);

    UserResponse get(Long id);

    UserResponse updateNickname(Long id, Long loginUserId, UserUpdateRequest request);
}
