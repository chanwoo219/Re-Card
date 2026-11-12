package com.rescene.recard.photocard.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willAnswer;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.rescene.recard.global.exception.BusinessException;
import com.rescene.recard.global.exception.ErrorCode;
import com.rescene.recard.photocard.dto.PhotocardCreateRequest;
import com.rescene.recard.photocard.entity.Photocard;
import com.rescene.recard.photocard.entity.PhotocardMember;
import com.rescene.recard.photocard.repository.PhotocardRepository;
import com.rescene.recard.user.entity.User;
import com.rescene.recard.user.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PhotocardServiceImplTest {

    @Mock
    private PhotocardRepository photocardRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PhotocardServiceImpl photocardService;

    @Test
    void createPhotocardReturnsOwnerAndCardDetails() {
        User owner = mock(User.class);
        given(owner.getId()).willReturn(5L);
        given(userRepository.findById(5L)).willReturn(Optional.of(owner));
        willAnswer(invocation -> invocation.getArgument(0))
                .given(photocardRepository).save(any(Photocard.class));

        var response = photocardService.create(5L,
                new PhotocardCreateRequest(PhotocardMember.WONI, " First Album ", "mint condition"));

        assertThat(response.ownerId()).isEqualTo(5L);
        assertThat(response.member()).isEqualTo(PhotocardMember.WONI);
        assertThat(response.album()).isEqualTo("First Album");
        assertThat(response.memo()).isEqualTo("mint condition");
        verify(userRepository).findById(5L);
        verify(photocardRepository).save(any(Photocard.class));
    }

    @Test
    void deleteOtherUsersPhotocardThrowsNotOwner() {
        User owner = mock(User.class);
        given(owner.getId()).willReturn(1L);
        Photocard photocard = new Photocard(owner, PhotocardMember.WONI, "Album", null);
        given(photocardRepository.findById(1L)).willReturn(Optional.of(photocard));

        assertThatThrownBy(() -> photocardService.delete(1L, 2L))
                .isInstanceOfSatisfying(BusinessException.class,
                        exception -> org.assertj.core.api.Assertions.assertThat(exception.getErrorCode())
                                .isEqualTo(ErrorCode.NOT_OWNER));

        verify(photocardRepository).findById(1L);
    }
}
