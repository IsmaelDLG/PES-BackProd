package com.pesados.purplepoint.api.model.user;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public interface UserService {

	Optional<User> getUserByEmail(String email);

	Optional<User> getUserById(Long id);
	
	Optional<User> getUserByToken(String token);

	List<User> getAll();

	User saveUser(User newUser);

	void deleteUserById(Long id);

}
