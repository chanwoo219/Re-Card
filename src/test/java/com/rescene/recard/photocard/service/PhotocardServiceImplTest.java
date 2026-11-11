package com.rescene.recard.photocard.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.rescene.recard.global.exception.BusinessException;
import com.rescene.recard.global.exception.ErrorCode;
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
