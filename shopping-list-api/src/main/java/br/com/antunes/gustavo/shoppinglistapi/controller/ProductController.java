package br.com.antunes.gustavo.shoppinglistapi.controller;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.antunes.gustavo.shoppinglistapi.dto.ProductDTO;
import br.com.antunes.gustavo.shoppinglistapi.exception.ApiErrorResponse;
import br.com.antunes.gustavo.shoppinglistapi.exception.CustomException;
import br.com.antunes.gustavo.shoppinglistapi.exception.ResourceNotFoundException;
import br.com.antunes.gustavo.shoppinglistapi.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Controller
@RequestMapping("/product")
public class ProductController {
	
	private final ProductService productService;
	
	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@Operation(description = "Endpoint for getting an product with it's id", responses = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved the product entity", 
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDTO.class))),
			@ApiResponse(responseCode = "404", description = "No entity found",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class)))
	})
	@GetMapping
	public ResponseEntity<?> getProduct(@RequestParam(required = false) Integer id, @RequestParam(required = false) String name) {
		try {
			if(!name.isEmpty()) {
				return ResponseEntity.ok(productService.findByName(name));
			}
			return ResponseEntity.ok(productService.findById(id));
		} catch (ResourceNotFoundException e) {
			return new ResponseEntity<>(handleCustomException(e, HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
		}
	}

	@Operation(description = "Endpoint for creating an product", responses = {
			@ApiResponse(responseCode = "200", description = "Successfully created the product entity", 
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDTO.class))),
			@ApiResponse(responseCode = "404", description = "No entity found",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class)))
	})
	@PostMapping
	public ResponseEntity<?> createProduct(@RequestBody ProductDTO productDTO) {
		try {
			ProductDTO createdProductDTO = productService.create(productDTO);
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(createdProductDTO.getId()).toUri();
			return ResponseEntity.created(location).body(createdProductDTO);
		} catch (CustomException e) {
			return new ResponseEntity<>(handleCustomException(e, HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
		}
	}
	
	@Operation(description = "Endpoint for updating an product", responses = {
			@ApiResponse(responseCode = "200", description = "Successfully updated the product", 
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDTO.class))),
			@ApiResponse(responseCode = "404", description = "No entity found",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class)))
	})
	@PutMapping
	public ResponseEntity<?> updateProduct(@RequestBody ProductDTO productDTO) {
		try {
			ProductDTO updatedProductDTO = productService.update(productDTO);
			return ResponseEntity.ok(updatedProductDTO);
		} catch (ResourceNotFoundException e) {
			return new ResponseEntity<>(handleCustomException(e, HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
		}
	}
	
	@Operation(description = "Endpoint for deleting a product object", responses = {
			@ApiResponse(responseCode = "200", description = "Successfully deleted the product!", content = @Content(mediaType = "application/json")) })
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable int id) {
		try {
			productService.deleteById(id);
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
