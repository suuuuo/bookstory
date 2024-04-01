package com.elice.bookstore.user.repository;

import com.elice.bookstore.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  Boolean existsByUserIdAndIsExist(String userId, Boolean isExist);
}
