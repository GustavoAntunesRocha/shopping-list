package br.com.antunes.gustavo.shoppinglistapi;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.antunes.gustavo.shoppinglistapi.entity.Cart;
import br.com.antunes.gustavo.shoppinglistapi.entity.Product;
import br.com.antunes.gustavo.shoppinglistapi.entity.ProductCart;
import br.com.antunes.gustavo.shoppinglistapi.repository.CartRepository;
import br.com.antunes.gustavo.shoppinglistapi.repository.ProductRepository;
import jakarta.transaction.Transactional;

@SpringBootApplication
public class ShoppingListApiApplication implements CommandLineRunner{
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CartRepository cartRepository;

	public static void main(String[] args) {
		SpringApplication.run(ShoppingListApiApplication.class, args);
	}
	
	@Override
	@Transactional
	public void run(String... args) {

		Product product = Product.builder()
				.name("Chocolate")
				.description("Barra de chocolate")
				.build();
		
		productRepository.save(product);
		
		ProductCart productCart = ProductCart.builder()
				.product(product)
				.quantities(5.0f)
				.productPrice(8.89f)
				.build();
		
		List<ProductCart> productCartList = new ArrayList<>();
		
		productCartList.add(productCart);
		
		Cart cart = Cart.builder()
				.productCartList(productCartList)
				.date(new Date())
				.price(productCartList.get(0).getProductPrice() * productCartList.get(0).getQuantities())
				.build();
		
		cartRepository.save(cart);
	}

}
