package br.com.antunes.gustavo.shoppinglistapi.controller;

import java.net.URI;

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
import br.com.antunes.gustavo.shoppinglistapi.service.CartService;

@RestController
@RequestMapping("/api/carts")
public class CartController {

	private final CartService cartService;

	public CartController(CartService cartService) {
		this.cartService = cartService;
	}

	@GetMapping("/{id}")
	public ResponseEntity<CartDTO> getCartById(@PathVariable int id) {
		return ResponseEntity.ok(cartService.getCartById(id));
	}

	@PostMapping
	public ResponseEntity<CartDTO> createCart(@RequestBody CartDTO cartDTO) {
		CartDTO createdCartDTO = cartService.createCart(cartDTO);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(createdCartDTO.getId()).toUri();
		return ResponseEntity.created(location).body(createdCartDTO);
	}

	@PostMapping("/product")
	public ResponseEntity<CartDTO> addProductToCart(@RequestBody ProductDTO productDTO,
			@RequestParam float productPrice, @RequestParam float quantity, @RequestParam int cartId){
		return ResponseEntity.ok(cartService.addProduct(productDTO, productPrice, quantity, cartId));
	}

	@PutMapping
	public ResponseEntity<CartDTO> updateCart(@RequestBody CartDTO cartDTO) {
		CartDTO updatedCartDTO = cartService.updateCart(cartDTO);
		return ResponseEntity.ok(updatedCartDTO);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCart(@PathVariable int id) {
		cartService.deleteCart(id);
		return ResponseEntity.noContent().build();
	}

}
