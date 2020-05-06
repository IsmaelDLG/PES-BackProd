package com.pesados.purplepoint.api.model.alarm;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface AlarmRepository extends JpaRepository<Alarm, Long> {

	public Optional<Alarm> findByUsername(String email);


}