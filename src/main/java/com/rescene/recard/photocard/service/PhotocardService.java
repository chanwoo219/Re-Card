package com.rescene.recard.photocard.service;

import com.rescene.recard.photocard.dto.PhotocardCreateRequest;
import com.rescene.recard.photocard.dto.PhotocardResponse;
import com.rescene.recard.photocard.dto.PhotocardUpdateRequest;

public interface PhotocardService {
    PhotocardResponse create(Long ownerId, PhotocardCreateRequest request);
    PhotocardResponse get(Long id);
    PhotocardResponse update(Long id, Long userId, PhotocardUpdateRequest request);
    void delete(Long id, Long userId);
}
