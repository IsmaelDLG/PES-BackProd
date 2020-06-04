package com.pesados.purplepoint.api.model.report;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.pesados.purplepoint.api.model.location.Location;
import com.pesados.purplepoint.api.model.user.User;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "Report")
public class Report {
	
	
	@Schema(description = "Id of the report.", required = true)
	@Column(name = "reportid")
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long reportid;	
	@Schema(description = "Description of the report", required = true)
	@Column(name = "report_desc")
	private String description;
	@Schema(description = "Location of the report", required = true)
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "report_loc")
	private Location location;	
	@Schema(description = "Reporter", required = true)
	@ManyToOne
    @JoinColumn(name="id", nullable=false)
    private User reporter;
	
	public Report() {
		super();
	}
	
	public Report(Location loc, User reporter) {
		this.location = loc;
		this.reporter = reporter;
	}
	
	public Report(String desc, Location loc, User reporter) {
		this.description =desc;
		this.location = loc;
		this.reporter = reporter;
	}

	public Long getId() {
		return this.reportid;
	}
	
	public void setId(Long id) {
		this.reportid = id;
	}

	public User getReporter() {
		return this.reporter;
	}
	
	public void setReporter(User reporter) {
		this.reporter = reporter;
	}
	
	public Location getLocation() {
		return this.location;
	}
	
	public void setLocation(Location loc) {
		this.location = loc;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
