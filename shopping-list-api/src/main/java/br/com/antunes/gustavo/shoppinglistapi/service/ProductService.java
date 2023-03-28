package br.com.antunes.gustavo.shoppinglistapi.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.antunes.gustavo.shoppinglistapi.dto.ProductDTO;
import br.com.antunes.gustavo.shoppinglistapi.entity.Product;
import br.com.antunes.gustavo.shoppinglistapi.exception.CustomException;
import br.com.antunes.gustavo.shoppinglistapi.exception.ResourceNotFoundException;
import br.com.antunes.gustavo.shoppinglistapi.repository.ProductRepository;

@Service
public class ProductService {
	
	private final ProductRepository productRepository;

	public ProductService(ProductRepository productRepository) {
		super();
		this.productRepository = productRepository;
	}

	public List<ProductDTO> findAll() throws ResourceNotFoundException{
		List<ProductDTO> productDTOlist = new ArrayList<>();
		List<Product> productList = productRepository.findAll();
		if(productList.isEmpty()) {
			throw new ResourceNotFoundException("No product was found");
		}
		for (Product product : productList) {
			productDTOlist.add(ProductDTO.fromEntity(product));
		}
	    return productDTOlist;
	}
	
	public ProductDTO findById(int id) throws ResourceNotFoundException {
	    return ProductDTO.fromEntity(productRepository.findById(id)
	            .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id)));
	}
	
	public List<ProductDTO> findByName(String name) throws ResourceNotFoundException {
		List<Product> productList = productRepository.findByNameContainingIgnoreCase(name);
		if(productList.isEmpty()) {
			throw new ResourceNotFoundException("No product was found containing the name: " + name);
		}
		List<ProductDTO> productDTOList = new ArrayList<>();
		for (Product product : productList) {
			productDTOList.add(ProductDTO.fromEntity(product));
		}
		return productDTOList;
	}

	public ProductDTO create(ProductDTO productDTO) throws CustomException{
		if(productDTO == null) {
			throw new CustomException("ProductDTO must not be null!");
		}
	    return ProductDTO.fromEntity(productRepository.save(productDTO.toEntity()));
	}
	
	public ProductDTO update(ProductDTO productDTO) throws ResourceNotFoundException{
		Product product = productRepository.findById(productDTO.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productDTO.getId()));
		product.setDescription(productDTO.getDescription());
		product.setName(productDTO.getName());
		productRepository.save(product);
		return ProductDTO.fromEntity(product);
	}

	public void deleteById(int id) throws ResourceNotFoundException{
		Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found with the id: " + id));
	    productRepository.delete(product);
	}


}
