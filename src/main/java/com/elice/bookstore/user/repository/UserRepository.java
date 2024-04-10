package com.elice.bookstore.user.repository;

import com.elice.bookstore.user.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * user JpaRepository.
 */
public interface UserRepository extends JpaRepository<User, Long> {

  Boolean existsByUserIdAndIsExist(String userId, Boolean isExist);

  Optional<User> findByUserIdAndIsExist(String userId, Boolean isExist);
}
