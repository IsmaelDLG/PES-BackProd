package com.pesados.purplepoint.api.model.report;

import java.util.Optional;

//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface ReportRepository extends CrudRepository<Report, Long> {

	Optional<Report> findByReportid(Long id);

	long deleteByReportid(Long id);
	
}
