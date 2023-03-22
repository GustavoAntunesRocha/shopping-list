package br.com.antunes.gustavo.shoppinglistapi.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.antunes.gustavo.shoppinglistapi.dto.ProductDTO;
import br.com.antunes.gustavo.shoppinglistapi.entity.Product;
import br.com.antunes.gustavo.shoppinglistapi.exception.ResourceNotFoundException;
import br.com.antunes.gustavo.shoppinglistapi.repository.ProductRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
	
	private final ProductRepository productRepository;

	public List<ProductDTO> findAll() {
		List<ProductDTO> productDTOlist = new ArrayList<>();
		List<Product> productList = productRepository.findAll();
		for (Product product : productList) {
			productDTOlist.add(ProductDTO.fromEntity(product));
		}
	    return productDTOlist;
	}
	
	public ProductDTO findById(int id) throws ResourceNotFoundException {
	    return ProductDTO.fromEntity(productRepository.findById(id)
	            .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id)));
	}

	public ProductDTO save(Product product) {
	    return ProductDTO.fromEntity(productRepository.save(product));
	}

	public void deleteById(int id) {
	    productRepository.deleteById(id);
	}


}
