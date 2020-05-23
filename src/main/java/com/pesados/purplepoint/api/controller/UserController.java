package com.pesados.purplepoint.api.controller;


import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

import com.pesados.purplepoint.api.exception.UserNotFoundException;
import com.pesados.purplepoint.api.exception.UserRegisterBadRequestException;
import com.pesados.purplepoint.api.exception.WrongPasswordException;
import com.pesados.purplepoint.api.model.image.ImageService;
import com.pesados.purplepoint.api.model.user.User;
import com.pesados.purplepoint.api.model.user.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;



@RestController
@RequestMapping("/api/v1")
public class UserController {
	private final UserService userService;

	@ModelAttribute
	public void setResponseHeader(HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
	}

	UserController(UserService userService, ImageService imgService) {
		this.userService = userService;
		this.imgService = imgService;
	}

	@Operation(summary = "Login User with E-mail and Password", description = "Login an %user% with an exising correct combination of password and email", tags = {"authorizations"})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful login",
					content = @Content(array = @ArraySchema(schema = @Schema(implementation = User.class))))})
	@PostMapping(value = "/users/login", produces = {"application/json", "application/xml"})

	public User login(@RequestBody User aUser) throws WrongPasswordException {
		String email = aUser.getEmail();
		String pwd = aUser.getPassword();

		String token = getJWTToken(email);
		User user = this.userService.getUserByEmail(email).orElseThrow(() -> new UserNotFoundException(email));

		if (pwd.equals(user.getPassword())) {
			user.setToken(token);
		} else {
			throw new WrongPasswordException(pwd);
		}

		return userService.saveUser(user);
	}

	private String getJWTToken(String email) {
		String secretKey = "adivinaestacrack";
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
				.commaSeparatedStringToAuthorityList("ROLE_USER, ROLE_DEVICE");

		String token = Jwts
				.builder()
				.setId("ppJWT")
				.setSubject(email)
				.claim("authorities",
						grantedAuthorities.stream()
								.map(GrantedAuthority::getAuthority)
								.collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 5)) // 5 minutos

				.signWith(SignatureAlgorithm.HS512,
						secretKey.getBytes()).compact();

		return "Bearer " + token;
	}

	// Refresh token
	@Operation(summary = "Refreshes token", description = "Gives an user a new api-key token which replaces the old one.", tags = {"authorizations"})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Token refreshed",
					content = @Content(schema = @Schema(implementation = User.class))),
			@ApiResponse(responseCode = "400", description = "Invalid input")})
	@PutMapping(value = "/users/refresh", consumes = {"application/json", "application/xml"})
	public User refresh(
			@RequestHeader("Authorization") String unformatedJWT
	) {
		User user = this.userService.getUserByToken(unformatedJWT).orElseThrow(() -> new UserNotFoundException(unformatedJWT));
		String newToken = getJWTToken(user.getEmail());
		user.setToken(newToken);

		return userService.saveUser(user);
	}

	// Register new user

	@Operation(summary = "Add a new user",
			description = "Adds a new user to the database with the information provided. "
					+ "To create a new user please provide:\n- A valid e-mail \n- An username\n- "
					+ "An e-mail \n- A password \n- The user's gender", tags = {"authorizations"})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "User created",
					content = @Content(schema = @Schema(implementation = User.class))),
			@ApiResponse(responseCode = "400", description = "Invalid input"),
			@ApiResponse(responseCode = "409", description = "User already exists")})
	@PostMapping(value = "/users/register", consumes = {"application/json", "application/xml"})
	User newUser(
			@Parameter(description = "User to add. Cannot null or empty.",
					required = true, schema = @Schema(implementation = User.class))
			@Valid @RequestBody User userNew
	) {
		if (!userService.getUserByEmail(userNew.getEmail()).isPresent()) {
			return userService.saveUser(userNew);
		} else {
			throw new UserRegisterBadRequestException();
		}
	}

	// Get all users

	@Operation(summary = "Get All Users", description = "Get ", tags = {"users"})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation",
					content = @Content(array = @ArraySchema(schema = @Schema(implementation = User.class))))})
	@GetMapping(value = "/users", produces = {"application/json", "application/xml"})
	List<User> all() {
		return userService.getAll();
	}

// Get a single user

	@Operation(summary = "Get User By ID", description = "Get User from an existing ID value you want to look up", tags = {"users"})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation",
					content = @Content(array = @ArraySchema(schema = @Schema(implementation = User.class))))})
	@GetMapping(value = "/users/{id}", produces = {"application/json", "application/xml"})
	User idUser(
			@Parameter(description = "ID of the contact to search.", required = true)
			@PathVariable long id) {
		return userService.getUserById(id).orElseThrow(() -> new UserNotFoundException(id));
	}

	@Operation(summary = "Get User By email", description = "Get User from an existing email you want to look up", tags = {"users"})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation",
					content = @Content(array = @ArraySchema(schema = @Schema(implementation = User.class))))})
	@GetMapping(value = "/users/email/{email}", produces = {"application/json", "application/xml"})
	User emailUser(
			@Parameter(description = "email of the contact to search.", required = true)
			@PathVariable String email) {
		return userService.getUserByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
	}

	// Update users
	@Operation(summary = "Update an existing User by ID", description = "Update the Name, username, email, password, gender given the ID of an existing user", tags = {"users"})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation"),
			@ApiResponse(responseCode = "400", description = "Invalid username supplied"),
			@ApiResponse(responseCode = "401", description = "Unauthorized"),
			@ApiResponse(responseCode = "404", description = "User not found"),
			@ApiResponse(responseCode = "405", description = "Validation exception")})
	@PutMapping("/users/{id}")
	User replaceUserbyID(@Parameter(description = "New information for the user.", required = true)
						 @RequestBody User newUser,
						 @Parameter(description = "id of the user to replace.", required = true)
						 @PathVariable long id
	) {
		return userService.getUserById(id)
				.map(user -> {
					user.setName(newUser.getName());
					user.setUsername(newUser.getUsername());
					user.setEmail(newUser.getEmail());
					user.setPassword(newUser.getPassword());
					user.setGender(newUser.getGender());
					user.setProfilePic(newUser.getProfilePic());
					return userService.saveUser(user);
				})
				.orElseGet(() -> {
					newUser.setId(id);
					return userService.saveUser(newUser);
				});

	}


	//Update users pic
	@Operation(summary = "Update an existing User profile picture by User Id",
			description = "profile pic",
			tags = {"users"})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation"),
			@ApiResponse(responseCode = "400", description = "Invalid username supplied"),
			@ApiResponse(responseCode = "401", description = "Unauthorized"),
			@ApiResponse(responseCode = "404", description = "User not found"),
			@ApiResponse(responseCode = "405", description = "Validation exception")})
	@PutMapping(value = "/users/{id}/image", consumes = {"multipart/form-data"})
	User updatePic(@Parameter(description = "New pic for the user.", required = true) @RequestParam("file") MultipartFile file,
				   @Parameter(description = "id of the user to replace.", required = true)
				   @PathVariable long id
	) throws IOException {
		Image img = imgService.saveImage(new Image(file.getName(), "image/jpg", file.getBytes()));
		return userService.getUserById(id)
				.map(user -> {
					user.setProfilePic(img);
					return userService.saveUser(user);
				})
				.orElseGet(() -> {
					User myUser = new User();
					myUser.setId(id);
					myUser.setProfilePic(img);
					return userService.saveUser(myUser);
				});

	}


	@Operation(summary = "Update an existing User by email", description = "Update the Name, username, email, password, gender given the email of an existing user", tags = { "users" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation"),
			@ApiResponse(responseCode = "400", description = "Invalid username supplied"),
			@ApiResponse(responseCode = "401", description = "Unauthorized"),
			@ApiResponse(responseCode = "404", description = "User not found"),
			@ApiResponse(responseCode = "405", description = "Validation exception")})
	@PutMapping(value = "/users/email/{email}", consumes = {"application/json", "application/xml"})
	User replaceUserbyEmail(@Parameter(description = "New information for the user.", required = true)
							@RequestBody User newUser,
							@Parameter(description = "Email of the user to replace.", required = true)
							@PathVariable String email
	) {
		return userService.getUserByEmail(email)
				.map(user -> {
					user.setName(newUser.getName());
					user.setUsername(newUser.getUsername());
					user.setEmail(newUser.getEmail());
					user.setPassword(newUser.getPassword());
					user.setGender(newUser.getGender());
					user.setProfilePic(newUser.getProfilePic());
					return userService.saveUser(user);
				})
				.orElseGet(() -> {
					newUser.setEmail(email);
					return userService.saveUser(newUser);
				});
	}

	// Delete user
	@Operation(summary = "Delete an user", description = "Delete an existing user given its id", tags = {"users"})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successful operation"),
			@ApiResponse(responseCode = "404", description = "User not found")})
	@DeleteMapping(path = "/users/{id}")
	void deleteUser(
			@Parameter(description = "Id of the contact to be delete. Cannot be empty.",
					required = true)
			@PathVariable long id
	) {
		userService.deleteUserById(id);
	}

	// Increase helpedUser
	@Operation(summary = "Increase helpedUsers", description = "Indicate a user is going to help another user. Returns the helperUser updated.", tags = {"users"})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successful operation"),
			@ApiResponse(responseCode = "404", description = "User not found"),
			@ApiResponse(responseCode = "401", description = "Unauthorized")
	})
	@PutMapping(path = "/users/increaseHelpedUsers/{userEmail}")
	User increaseHelpedUser(
			@Parameter(description = "Information for the user who has helped.", required = true)
			@PathVariable String userEmail
	) {
		return userService.getUserByEmail(userEmail)
				.map(user -> {
					user.setHelpedUsers(user.getHelpedUsers()+1);
					return userService.saveUser(user);
				})
				.orElseGet(() -> {
					User newUser = new User();
					newUser.setEmail(userEmail);
					return userService.saveUser(newUser);
				});
	}
}