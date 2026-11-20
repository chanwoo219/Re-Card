package com.rescene.recard.photocard.repository;

import com.rescene.recard.photocard.entity.Photocard;
import com.rescene.recard.photocard.entity.PhotocardMember;
import java.util.List;

public interface PhotocardRepositoryCustom {
    List<Photocard> search(PhotocardMember member, String album);
}
