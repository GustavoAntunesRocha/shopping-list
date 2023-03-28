package br.com.antunes.gustavo.shoppinglistapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.antunes.gustavo.shoppinglistapi.dto.CartDTO;
import br.com.antunes.gustavo.shoppinglistapi.dto.ProductDTO;
import br.com.antunes.gustavo.shoppinglistapi.entity.Cart;
import br.com.antunes.gustavo.shoppinglistapi.entity.Product;
import br.com.antunes.gustavo.shoppinglistapi.entity.ProductCart;
import br.com.antunes.gustavo.shoppinglistapi.exception.CustomException;
import br.com.antunes.gustavo.shoppinglistapi.exception.ResourceNotFoundException;
import br.com.antunes.gustavo.shoppinglistapi.repository.CartRepository;

@Service
public class CartService {

	private final CartRepository cartRepository;
	
	private final ProductService productService;

	public CartService(CartRepository cartRepository, ProductService productService) {
		super();
		this.cartRepository = cartRepository;
		this.productService = productService;
	}


	public List<Cart> getAllCarts() throws ResourceNotFoundException{
	    List<Cart> allCarts = cartRepository.findAll();
	    if (allCarts.isEmpty()) {
	        throw new ResourceNotFoundException("No cart was found");
	    } else {
	        return allCarts;
	    }
	}


	public CartDTO getCartById(int id) throws ResourceNotFoundException{
		return CartDTO.fromEntity(cartRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cart not found with the id: " + id)));
	}

	public CartDTO createCart(CartDTO cartDTO) throws CustomException{
		try {
			return CartDTO.fromEntity(cartRepository.save(cartDTO.toEntity()));
		} catch (IllegalArgumentException e) {
			throw new CustomException("The given CartDTO entity must not be null!");
		}
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

	public CartDTO updateCart(CartDTO cartDTO) throws ResourceNotFoundException{
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

	public void deleteCart(int id) throws ResourceNotFoundException{
		Optional<Cart> cart = cartRepository.findById(id);
		if (cart.isPresent()) {
			cartRepository.deleteById(id);
		} else {
			throw new ResourceNotFoundException("Cart not found with ID: " + id);
		}
	}

}
