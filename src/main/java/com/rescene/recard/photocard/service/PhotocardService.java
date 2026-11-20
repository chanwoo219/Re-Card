package com.rescene.recard.photocard.service;

import com.rescene.recard.photocard.dto.PhotocardCreateRequest;
import com.rescene.recard.photocard.dto.PhotocardResponse;
import com.rescene.recard.photocard.dto.PhotocardUpdateRequest;
import com.rescene.recard.photocard.entity.PhotocardMember;
import java.util.List;

public interface PhotocardService {
    PhotocardResponse create(Long ownerId, PhotocardCreateRequest request);
    PhotocardResponse get(Long id);
    List<PhotocardResponse> search(PhotocardMember member, String album);
    PhotocardResponse update(Long id, Long userId, PhotocardUpdateRequest request);
    void delete(Long id, Long userId);
}
