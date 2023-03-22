package br.com.antunes.gustavo.shoppinglistapi.controller;

import java.net.URI;

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
import br.com.antunes.gustavo.shoppinglistapi.service.ProductService;

@Controller
@RequestMapping("/product")
public class ProductController {
	
	private final ProductService productService;
	
	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping
	public ResponseEntity<?> getProduct(@RequestParam(required = false) Integer id, @RequestParam(required = false) String name) {
		if(!name.isEmpty()) {
			return ResponseEntity.ok(productService.findByName(name));
		}
		return ResponseEntity.ok(productService.findById(id));
	}

	@PostMapping
	public ResponseEntity<ProductDTO> createCart(@RequestBody ProductDTO productDTO) {
		ProductDTO createdProductDTO = productService.create(productDTO);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(createdProductDTO.getId()).toUri();
		return ResponseEntity.created(location).body(createdProductDTO);
	}
	
	@PutMapping
	public ResponseEntity<ProductDTO> updateProduct(@RequestBody ProductDTO productDTO) {
		ProductDTO updatedProductDTO = productService.update(productDTO);
		return ResponseEntity.ok(updatedProductDTO);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable int id) {
		productService.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
