package com.rescene.recard.photocard.service;

import com.rescene.recard.global.exception.BusinessException;
import com.rescene.recard.global.exception.ErrorCode;
import com.rescene.recard.photocard.dto.PhotocardCreateRequest;
import com.rescene.recard.photocard.dto.PhotocardResponse;
import com.rescene.recard.photocard.dto.PhotocardUpdateRequest;
import com.rescene.recard.photocard.entity.Photocard;
import com.rescene.recard.photocard.repository.PhotocardRepository;
import com.rescene.recard.user.entity.User;
import com.rescene.recard.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PhotocardServiceImpl implements PhotocardService {

    private final PhotocardRepository photocardRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public PhotocardResponse create(Long ownerId, PhotocardCreateRequest request) {
        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        Photocard saved = photocardRepository.save(
                new Photocard(owner, request.member(), request.album().trim(), request.memo()));
        return PhotocardResponse.from(saved);
    }

    @Override
    public PhotocardResponse get(Long id) {
        return PhotocardResponse.from(findPhotocard(id));
    }

    @Override
    @Transactional
    public PhotocardResponse update(Long id, Long userId, PhotocardUpdateRequest request) {
        Photocard photocard = findOwnedPhotocard(id, userId);
        photocard.update(request.member(), request.album().trim(), request.memo());
        return PhotocardResponse.from(photocard);
    }

    @Override
    @Transactional
    public void delete(Long id, Long userId) {
        photocardRepository.delete(findOwnedPhotocard(id, userId));
    }

    private Photocard findPhotocard(Long id) {
        return photocardRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.CARD_NOT_FOUND));
    }

    private Photocard findOwnedPhotocard(Long id, Long userId) {
        Photocard photocard = findPhotocard(id);
        if (!photocard.getOwner().getId().equals(userId)) {
            throw new BusinessException(ErrorCode.NOT_OWNER);
        }
        return photocard;
    }
}
