package com.pesados.purplepoint.api.model.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
	public Optional<User> findByEmail(String email);

	Optional<User> findByToken(String token);
}