package br.com.antunes.gustavo.shoppinglistapi.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.antunes.gustavo.shoppinglistapi.dto.UserDTO;
import br.com.antunes.gustavo.shoppinglistapi.entity.UserEntity;
import br.com.antunes.gustavo.shoppinglistapi.exception.ApiErrorResponse;
import br.com.antunes.gustavo.shoppinglistapi.exception.CustomException;
import br.com.antunes.gustavo.shoppinglistapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;
    
    @Operation(description = "Endpoint for user authentication and to obtain an access token", responses = {
			@ApiResponse(responseCode = "200", description = "Successfully authenticated and received an access token", 
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponse.class))),
			@ApiResponse(responseCode = "400", description = "Invalid login request or missing request parameters",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))),
			@ApiResponse(responseCode = "401", description = "Unauthorized access or invalid credentials",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class)))
	})
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest request) {
		try {
			return ResponseEntity.ok(userService.authenticate(request));
		} catch (CustomException e) {
			return new ResponseEntity<>(handleCustomException(e, HttpStatus.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);
		}
	}

    @PostMapping("/create")
    @Operation(summary = "Create a user", description = "Creates a new user and returns the created user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CreateUserRequestModel.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> createUser(@RequestBody @Valid CreateUserRequestModel userDTO) {
        UserEntity user = modelMapper.map(userDTO, UserEntity.class);
        //user.setPassword(password);
        UserDTO createdUserDTO;
		try {
			createdUserDTO = userService.createUser(user);
			return ResponseEntity.status(HttpStatus.CREATED).body(createdUserDTO);
		} catch (CustomException e) {
			return new ResponseEntity<>(handleCustomException(e, HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
		}
        
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a user by ID", description = "Returns a user by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found successfully",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))}),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> getUserById(@PathVariable @Parameter(description = "User ID") int id){
        try {
			UserDTO userDTO = userService.getUserById(id);
			return ResponseEntity.ok(userDTO);
		} catch (CustomException e) {
			return new ResponseEntity<>(handleCustomException(e, HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
		}
    }

    @GetMapping("/email/{email}")
    @Operation(summary = "Get a user by email", description = "Returns a user by their email address")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found successfully",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))}),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> getUserByEmail(@PathVariable @Parameter(description = "User email") String email){
        try {
			UserDTO userDTO = userService.getUserByEmail(email);
			return ResponseEntity.ok(userDTO);
		} catch (CustomException e) {
			return new ResponseEntity<>(handleCustomException(e, HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
		}
    }

    @GetMapping
    @Operation(summary = "Get all users", description = "Returns a list of all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users found successfully",
                    content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UserDTO.class)))}),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> userDTOs = userService.getAllUsers();
        return ResponseEntity.ok(userDTOs);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a user", description = "Updates an existing user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")})
    public ResponseEntity<?> updateUser(@PathVariable int id, @RequestBody UserDTO userDTO){
    	try {
			UserEntity user = modelMapper.map(userDTO, UserEntity.class);
			user.setId(id);
			userService.updateUser(user);
			return ResponseEntity.ok().build();
		} catch (CustomException e) {
			return new ResponseEntity<>(handleCustomException(e, HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
		}
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a user by ID", description = "Deletes an existing user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")})
    public ResponseEntity<?> deleteUserById(@PathVariable int id){
        try {
			userService.deleteUserById(id);
			return ResponseEntity.ok().build();
		} catch (CustomException e) {
			return new ResponseEntity<>(handleCustomException(e, HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
		}
    }
    
    public ApiErrorResponse handleCustomException(CustomException e, HttpStatus status) {
		LocalDateTime timestamp = LocalDateTime.now();
		ApiErrorResponse errorResponse = new ApiErrorResponse(status.toString(),
				DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss").format(timestamp), e.getMessage());
		return errorResponse;
	}

}
