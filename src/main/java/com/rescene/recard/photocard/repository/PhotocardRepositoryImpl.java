package com.rescene.recard.photocard.repository;

import static com.rescene.recard.photocard.entity.QPhotocard.photocard;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.rescene.recard.photocard.entity.Photocard;
import com.rescene.recard.photocard.entity.PhotocardMember;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
public class PhotocardRepositoryImpl implements PhotocardRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Photocard> search(PhotocardMember member, String album) {
        return queryFactory
                .selectFrom(photocard)
                .where(memberEq(member), albumEq(album))
                .orderBy(photocard.id.desc())
                .fetch();
    }

    // null을 반환하면 where 절에서 해당 조건이 제외된다
    private BooleanExpression memberEq(PhotocardMember member) {
        return member != null ? photocard.member.eq(member) : null;
    }

    private BooleanExpression albumEq(String album) {
        return StringUtils.hasText(album) ? photocard.album.eq(album.trim()) : null;
    }
}
