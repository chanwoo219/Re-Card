package com.rescene.recard.photocard.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.rescene.recard.photocard.dto.PhotocardCreateRequest;
import com.rescene.recard.photocard.dto.PhotocardResponse;
import com.rescene.recard.photocard.entity.PhotocardMember;
import com.rescene.recard.photocard.service.PhotocardService;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(PhotocardController.class)
class PhotocardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PhotocardService photocardService;

    @Test
    void createPhotocardReturns201AndApiResponse() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        given(photocardService.create(eq(5L), any(PhotocardCreateRequest.class)))
                .willReturn(new PhotocardResponse(10L, 5L, PhotocardMember.WONI,
                        "First Album", "mint condition", now, now));

        mockMvc.perform(post("/api/photocards")
                        .header("X-User-Id", 5L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"member\":\"WONI\",\"album\":\"First Album\",\"memo\":\"mint condition\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(10))
                .andExpect(jsonPath("$.data.ownerId").value(5))
                .andExpect(jsonPath("$.data.member").value("WONI"));
    }

    @Test
    void createPhotocardWithInvalidAlbumReturns400() throws Exception {
        mockMvc.perform(post("/api/photocards")
                        .header("X-User-Id", 5L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"member\":\"WONI\",\"album\":\" \"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.error.code").value("INVALID_INPUT"));
    }

    @Test
    void deletePhotocardReturns204() throws Exception {
        mockMvc.perform(delete("/api/photocards/{id}", 10L)
                        .header("X-User-Id", 5L))
                .andExpect(status().isNoContent());

        verify(photocardService).delete(10L, 5L);
    }
}
