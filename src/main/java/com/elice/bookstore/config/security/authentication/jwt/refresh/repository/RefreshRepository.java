package com.elice.bookstore.config.security.authentication.jwt.refresh.repository;

import com.elice.bookstore.config.security.authentication.jwt.refresh.domain.Refresh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * refresh token repository.
 */
public interface RefreshRepository extends JpaRepository<Refresh, Long> {

  Boolean existsByRefresh(String refresh);

  @Transactional
  void deleteByRefresh(String refresh);
}