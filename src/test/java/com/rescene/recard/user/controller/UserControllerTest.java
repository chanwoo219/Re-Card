package com.rescene.recard.user.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.rescene.recard.global.exception.BusinessException;
import com.rescene.recard.global.exception.ErrorCode;
import com.rescene.recard.user.dto.UserCreateRequest;
import com.rescene.recard.user.dto.UserResponse;
import com.rescene.recard.user.service.UserService;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Test
    @DisplayName("유저 생성 시 201과 ApiResponse 성공 형식을 반환한다")
    void createUser() throws Exception {
        given(userService.create(any(UserCreateRequest.class)))
                .willReturn(new UserResponse(1L, "woni", LocalDateTime.now()));

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nickname\":\"woni\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.nickname").value("woni"))
                .andExpect(jsonPath("$.error").doesNotExist());
    }

    @Test
    @DisplayName("없는 유저를 조회하면 404와 USER_NOT_FOUND를 반환한다")
    void getUserNotFound() throws Exception {
        given(userService.get(999L)).willThrow(new BusinessException(ErrorCode.USER_NOT_FOUND));

        mockMvc.perform(get("/api/users/{id}", 999L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.error.code").value("USER_NOT_FOUND"));
    }
}
