package com.rescene.recard.photocard.repository;

import com.rescene.recard.photocard.entity.Photocard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotocardRepository extends JpaRepository<Photocard, Long> {
}
