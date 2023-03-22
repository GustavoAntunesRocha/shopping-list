package br.com.antunes.gustavo.shoppinglistapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.antunes.gustavo.shoppinglistapi.dto.CartDTO;
import br.com.antunes.gustavo.shoppinglistapi.dto.ProductDTO;
import br.com.antunes.gustavo.shoppinglistapi.entity.Cart;
import br.com.antunes.gustavo.shoppinglistapi.entity.Product;
import br.com.antunes.gustavo.shoppinglistapi.entity.ProductCart;
import br.com.antunes.gustavo.shoppinglistapi.exception.ResourceNotFoundException;
import br.com.antunes.gustavo.shoppinglistapi.repository.CartRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService {

	private final CartRepository cartRepository;
	
	private final ProductService productService;

	public List<Cart> getAllCarts() {
		return cartRepository.findAll();
	}

	public CartDTO getCartById(int id) {
		return CartDTO.fromEntity(cartRepository.findById(id).get());
	}

	public CartDTO createCart(CartDTO cartDTO) {
		return CartDTO.fromEntity(cartRepository.save(cartDTO.toEntity()));
	}
	
	public CartDTO addProduct(ProductDTO productDTO, float productPrice, float quantity, int cartId) throws ResourceNotFoundException {
		Product product = productService.findById(productDTO.getId()).toEntity();
		Optional<Cart> cartOptional = cartRepository.findById(cartId);
		Cart cart;
		if(!cartOptional.isPresent()) {
			cart = new Cart();
			cartRepository.save(cart);
		} else {
			cart = cartOptional.get();
		}
		List<ProductCart> productCartList = cart.getProductCartList();
		ProductCart productCart = new ProductCart(0, product, quantity, productPrice);
		productCartList.add(productCart);
		updateCart(CartDTO.fromEntity(cart));
		return CartDTO.fromEntity(cart);
	}

	public CartDTO updateCart(CartDTO cartDTO) {
		Optional<Cart> optionalCart = cartRepository.findById(cartDTO.getId());
		if (optionalCart.isPresent()) {
			Cart existingCart = optionalCart.get();
			existingCart.setProductCartList(cartDTO.toEntity().getProductCartList());
			existingCart.setPrice(cartDTO.getPrice());
			existingCart.setDate(cartDTO.getDate());
			return CartDTO.fromEntity(cartRepository.save(existingCart));
		} else {
			throw new ResourceNotFoundException("Cart not found with ID: " + cartDTO.getId());
		}
	}

	public void deleteCart(int id) {
		Optional<Cart> cart = cartRepository.findById(id);
		if (cart.isPresent()) {
			cartRepository.deleteById(id);
		} else {
			throw new ResourceNotFoundException("Cart not found with ID: " + id);
		}
	}

}
