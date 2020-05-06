package com.pesados.purplepoint.api.controller;


import com.pesados.purplepoint.api.exception.AlarmNotFoundException;
import com.pesados.purplepoint.api.model.alarm.Alarm;
import com.pesados.purplepoint.api.model.alarm.AlarmService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class AlarmController {

	private final AlarmService alarmService;

	AlarmController(AlarmService alarmService) {
		this.alarmService = alarmService;
	}


	// Creates a new alarm


	@Operation(summary = "Create a new alarm",
			description = "Adds a new alarm to the database with the information provided. ", tags = { "alarms" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Alarm created",
					content = @Content(schema = @Schema(implementation = Alarm.class))),
			@ApiResponse(responseCode = "400", description = "Invalid input"),
			@ApiResponse(responseCode = "409", description = "Alarm already exists") })
	@PostMapping(value = "/alarms/create", consumes = { "application/json", "application/xml" })
	Alarm newAlarm(
			@Parameter(description="Alarm to add. Cannot null or empty.",
					required=true, schema=@Schema(implementation = Alarm.class))
			@Valid @RequestBody Alarm alarmNew
	) {
		return alarmService.saveAlarm(alarmNew);

	}


	// Get all alarms

	@Operation(summary = "Get All Alarms", description = "Get all the existing alarms", tags = {"alarms"})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation",
					content = @Content(array = @ArraySchema(schema = @Schema(implementation = Alarm.class))))})
	@GetMapping(value = "/alarms", produces = {"application/json", "application/xml"})
	List<Alarm> all() {
		return alarmService.getAll();
	}

	//Get an alarm by its Id

	@Operation(summary = "Get Alarm By ID", description = "Get an Alarm from an existing ID value you want to look up", tags = {"alarms"})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation",
					content = @Content(array = @ArraySchema(schema = @Schema(implementation = Alarm.class))))})
	@GetMapping(value = "/alarms/{id}", produces = {"application/json", "application/xml"})
	Alarm getbyId(
			@Parameter(description = "ID of the contact to search.", required = true)
			@PathVariable long id) {
		return alarmService.getAlarmById(id).orElseThrow(() -> new AlarmNotFoundException(id));
	}

	@Operation(summary = "Get Alarm By Username", description = "Get an Alarm by its username", tags = {"alarms"})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation",
					content = @Content(array = @ArraySchema(schema = @Schema(implementation = Alarm.class))))})
	@GetMapping(value = "/alarms/username/{username}", produces = {"application/json", "application/xml"})
	Alarm getbyUsername(
			@Parameter(description = "username of the person who pressed the panic button.", required = true)
			@PathVariable String username){
		return alarmService.getAlarmByUsername(username).orElseThrow(() -> new AlarmNotFoundException(username));
	}


	//Update an alarm

	@Operation(summary = "Update an existing alarm by ID", description = "Update the username, Location, given the ID of an existing alarm", tags = {"alarms"})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation"),
			@ApiResponse(responseCode = "400", description = "Invalid id supplied"),
			@ApiResponse(responseCode = "401", description = "Unauthorized"),
			@ApiResponse(responseCode = "404", description = "Alarm not found"),
			@ApiResponse(responseCode = "405", description = "Validation exception")})
	@PutMapping("/alarms/update/ {id}")
	Alarm replaceAlarmbyID(@Parameter(description = "New information for the alarm.", required = true)
						   @RequestBody Alarm newAlarm,
						   @Parameter(description = "id of the alarm to replace.", required = true)
						   @PathVariable long id
	) {
		return alarmService.getAlarmById(id)
				.map(alarm -> {
					alarm.setUsername(newAlarm.getUsername());
					alarm.setLocation(newAlarm.getLocation());
					alarm.setPanicbutton(true);
					return alarmService.saveAlarm((alarm));
				}).orElseGet(() -> {
					newAlarm.setAlarmId(id);
					return alarmService.saveAlarm(newAlarm);

				});
	}

	//Delete an Alarm

	@Operation(summary = "Delete an alarm", description = "Delete an existing alarm given its id", tags = { "alarms" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation"),
			@ApiResponse(responseCode = "404", description = "Alarm not found") })
	@DeleteMapping(path="/alarms/delete/{id}")
	void deleyeAlarm(
			@Parameter(description="Id of the alarm to be deleted. Cannot be empty.",
					required=true)
			@PathVariable long id
	) {
		alarmService.deleteAlarmById(id);
	}

}