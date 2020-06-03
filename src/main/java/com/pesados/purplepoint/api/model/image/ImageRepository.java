package com.pesados.purplepoint.api.model.image;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
	Optional<Image> findByImgname(String name);

	void deleteByImgname(String name);
}
