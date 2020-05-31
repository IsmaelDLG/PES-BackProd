package com.pesados.purplepoint.api.model.report;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportServiceImpl implements ReportService {
 
    @Autowired
    private ReportRepository reportRepository;

	@Override
	public Optional<Report> getReportById(Long id) {
		return this.reportRepository.findById(id);
	}

	@Override
	public List<Report> getAll() {
		return this.reportRepository.findAll();
	}

	@Override
	public void deleteReportById(Long id) {
		this.reportRepository.deleteById(id);
	}

	@Override
	public Report saveReport(Report newRep) {
		return this.reportRepository.save(newRep);
	}
 

}