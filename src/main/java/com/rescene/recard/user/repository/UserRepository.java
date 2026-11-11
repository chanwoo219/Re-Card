package com.rescene.recard.user.repository;

import com.rescene.recard.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
