package com.elice.bookstore.user.repository;

import com.elice.bookstore.user.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * user JpaRepository.
 *
 * findByUserIdAndIsExist for social login.
 *
 * findByEmailAndIsExist
 */
public interface UserRepository extends JpaRepository<User, Long> {

  Boolean existsByEmailAndIsExist(String email, Boolean isExist);

  Optional<User> findByIdAndIsExist(Long id, Boolean isExist);

  Optional<User> findByEmailAndIsExist(String email, Boolean isExist);

  Optional<User> findByUserIdAndIsExist(String userId, Boolean isExist);
}
