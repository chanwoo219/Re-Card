package com.rescene.recard.photocard.dto;

import com.rescene.recard.photocard.entity.Photocard;
import com.rescene.recard.photocard.entity.PhotocardMember;
import java.time.LocalDateTime;

public record PhotocardResponse(
        Long id,
        Long ownerId,
        PhotocardMember member,
        String album,
        String memo,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static PhotocardResponse from(Photocard photocard) {
        return new PhotocardResponse(photocard.getId(), photocard.getOwner().getId(), photocard.getMember(),
                photocard.getAlbum(), photocard.getMemo(), photocard.getCreatedAt(), photocard.getUpdatedAt());
    }
}
