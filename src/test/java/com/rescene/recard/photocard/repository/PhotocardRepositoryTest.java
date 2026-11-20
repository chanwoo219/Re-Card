package com.rescene.recard.photocard.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.rescene.recard.global.config.QuerydslConfig;
import com.rescene.recard.photocard.entity.Photocard;
import com.rescene.recard.photocard.entity.PhotocardMember;
import com.rescene.recard.user.entity.User;
import com.rescene.recard.user.repository.UserRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(QuerydslConfig.class)
class PhotocardRepositoryTest {

    @Autowired
    private PhotocardRepository photocardRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        User owner = userRepository.save(new User("search-tester"));
        photocardRepository.saveAll(List.of(
                new Photocard(owner, PhotocardMember.LIV, "TESTA", null),
                new Photocard(owner, PhotocardMember.LIV, "TESTB", null),
                new Photocard(owner, PhotocardMember.WONI, "TESTA", null)));
    }

    @Test
    @DisplayName("member와 album 조건이 모두 일치하는 포토카드만 조회된다")
    void searchByMemberAndAlbum() {
        List<Photocard> result = photocardRepository.search(PhotocardMember.LIV, "TESTA");

        assertThat(result).hasSize(1)
                .allSatisfy(card -> {
                    assertThat(card.getMember()).isEqualTo(PhotocardMember.LIV);
                    assertThat(card.getAlbum()).isEqualTo("TESTA");
                });
    }

    @Test
    @DisplayName("null 조건은 제외되어 member 조건만 적용된다")
    void searchByMemberOnly() {
        List<Photocard> result = photocardRepository.search(PhotocardMember.LIV, null);

        assertThat(result).isNotEmpty()
                .allSatisfy(card -> assertThat(card.getMember()).isEqualTo(PhotocardMember.LIV));
        assertThat(result).extracting(Photocard::getAlbum).contains("TESTA", "TESTB");
    }

    @Test
    @DisplayName("조건이 모두 null이면 전체가 조회된다")
    void searchWithoutCondition() {
        assertThat(photocardRepository.search(null, null))
                .hasSize((int) photocardRepository.count());
    }
}
