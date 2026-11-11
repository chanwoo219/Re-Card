package com.rescene.recard.photocard.dto;

import com.rescene.recard.photocard.entity.PhotocardMember;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PhotocardUpdateRequest(
        @NotNull PhotocardMember member,
        @NotBlank @Size(max = 100) String album,
        @Size(max = 500) String memo
) {
}
