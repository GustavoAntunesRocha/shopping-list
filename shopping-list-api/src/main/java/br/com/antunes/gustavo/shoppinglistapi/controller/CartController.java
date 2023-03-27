package br.com.antunes.gustavo.shoppinglistapi.controller;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.antunes.gustavo.shoppinglistapi.dto.CartDTO;
import br.com.antunes.gustavo.shoppinglistapi.dto.ProductDTO;
import br.com.antunes.gustavo.shoppinglistapi.exception.ApiErrorResponse;
import br.com.antunes.gustavo.shoppinglistapi.exception.CustomException;
import br.com.antunes.gustavo.shoppinglistapi.exception.ResourceNotFoundException;
import br.com.antunes.gustavo.shoppinglistapi.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/carts")
public class CartController {

	private final CartService cartService;

	public CartController(CartService cartService) {
		this.cartService = cartService;
	}

	@Operation(description = "Endpoint for getting an cart using it's id", responses = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved the cart entity", 
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = CartDTO.class))),
			@ApiResponse(responseCode = "404", description = "No entity found",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class)))
	})
	@GetMapping("/{id}")
	public ResponseEntity<?> getCartById(@PathVariable int id) {
		try {
			return ResponseEntity.ok(cartService.getCartById(id));
		} catch (ResourceNotFoundException e) {
			return new ResponseEntity<>(handleCustomException(e, HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
		}
	}

	@Operation(description = "Endpoint for creating an cart", responses = {
			@ApiResponse(responseCode = "200", description = "Successfully created the cart entity", 
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = CartDTO.class))),
			@ApiResponse(responseCode = "404", description = "No entity found",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class)))
	})
	@PostMapping
	public ResponseEntity<?> createCart(@RequestBody CartDTO cartDTO) {
		try {
			CartDTO createdCartDTO = cartService.createCart(cartDTO);
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(createdCartDTO.getId()).toUri();
			return ResponseEntity.created(location).body(createdCartDTO);
		} catch (CustomException e) {
			return new ResponseEntity<>(handleCustomException(e, HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
		}
	}

	@Operation(description = "Endpoint for adding a product to an cart", responses = {
			@ApiResponse(responseCode = "200", description = "Successfully added the productto the the cart", 
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = CartDTO.class))),
			@ApiResponse(responseCode = "404", description = "No entity found",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class)))
	})
	@PostMapping("/product")
	public ResponseEntity<?> addProductToCart(@RequestBody ProductDTO productDTO,
			@RequestParam float productPrice, @RequestParam float quantity, @RequestParam int cartId){
		try {
			return ResponseEntity.ok(cartService.addProduct(productDTO, productPrice, quantity, cartId));
		} catch (ResourceNotFoundException e) {
			return new ResponseEntity<>(handleCustomException(e, HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
		}
	}

	@Operation(description = "Endpoint for updating an cart", responses = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved the cart entity", 
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = CartDTO.class))),
			@ApiResponse(responseCode = "404", description = "No entity found",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class)))
	})
	@PutMapping
	public ResponseEntity<?> updateCart(@RequestBody CartDTO cartDTO) {
		try {
			CartDTO updatedCartDTO = cartService.updateCart(cartDTO);
			return ResponseEntity.ok(updatedCartDTO);
		} catch (ResourceNotFoundException e) {
			return new ResponseEntity<>(handleCustomException(e, HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
		}
	}

	@Operation(description = "Endpoint fo deleting a cart object", responses = {
			@ApiResponse(responseCode = "200", description = "Successfully deleted the cart!", content = @Content(mediaType = "application/json")) })
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteCart(@PathVariable int id) {
		try {
			cartService.deleteCart(id);
			return ResponseEntity.noContent().build();
		} catch (ResourceNotFoundException e) {
			return new ResponseEntity<>(handleCustomException(e, HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
		}
	}
	
	public ApiErrorResponse handleCustomException(RuntimeException e, HttpStatus status) {
		LocalDateTime timestamp = LocalDateTime.now();
		ApiErrorResponse errorResponse = new ApiErrorResponse(status.toString(),
				DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss").format(timestamp), e.getMessage());
		return errorResponse;
	}

}
