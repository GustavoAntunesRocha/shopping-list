package br.com.antunes.gustavo.shoppinglistapi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class ProductCart {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@ManyToOne
	private Product product;
	
	private float quantities;
	
	private float productPrice;
	
	public ProductCart() {}

	public ProductCart(int id, Product product, float quantities, float productPrice) {
		super();
		this.id = id;
		this.product = product;
		this.quantities = quantities;
		this.productPrice = productPrice;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public float getQuantities() {
		return quantities;
	}

	public void setQuantities(float quantities) {
		this.quantities = quantities;
	}

	public float getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(float productPrice) {
		this.productPrice = productPrice;
	}

}
