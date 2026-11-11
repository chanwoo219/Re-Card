package com.rescene.recard.user.service;

import com.rescene.recard.global.exception.BusinessException;
import com.rescene.recard.global.exception.ErrorCode;
import com.rescene.recard.user.dto.UserCreateRequest;
import com.rescene.recard.user.dto.UserResponse;
import com.rescene.recard.user.dto.UserUpdateRequest;
import com.rescene.recard.user.entity.User;
import com.rescene.recard.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserResponse create(UserCreateRequest request) {
        validateNickname(request.nickname());
        User user = userRepository.save(new User(request.nickname()));
        return UserResponse.from(user);
    }

    @Override
    public UserResponse get(Long id) {
        return UserResponse.from(findUser(id));
    }

    @Override
    @Transactional
    public UserResponse updateNickname(Long id, Long loginUserId, UserUpdateRequest request) {
        User user = findUser(id);
        if (!user.getId().equals(loginUserId)) {
            throw new BusinessException(ErrorCode.NOT_OWNER);
        }
        if (!user.getNickname().equals(request.nickname())) {
            validateNickname(request.nickname());
            user.changeNickname(request.nickname());
        }
        return UserResponse.from(user);
    }

    private User findUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    }

    private void validateNickname(String nickname) {
        if (userRepository.existsByNickname(nickname)) {
            throw new BusinessException(ErrorCode.DUPLICATE_NICKNAME);
        }
    }
}
