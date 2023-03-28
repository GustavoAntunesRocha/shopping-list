package br.com.antunes.gustavo.shoppinglistapi.dto;

import br.com.antunes.gustavo.shoppinglistapi.entity.ProductCart;

public class ProductCartDTO {

    private int id;
    private ProductDTO product;
    private float quantities;
    private float productPrice;
    
    public ProductCartDTO() {}

    public ProductCartDTO(int id, ProductDTO product, float quantities, float productPrice) {
		super();
		this.id = id;
		this.product = product;
		this.quantities = quantities;
		this.productPrice = productPrice;
	}

	public static ProductCartDTO fromEntity(ProductCart productCart) {
        return new ProductCartDTO(
                productCart.getId(),
                ProductDTO.fromEntity(productCart.getProduct()),
                productCart.getQuantities(),
                productCart.getProductPrice()
        );
    }

    public ProductCart toEntity() {
        return new ProductCart(
                this.id,
                this.product.toEntity(),
                this.quantities,
                this.productPrice
        );
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ProductDTO getProduct() {
		return product;
	}

	public void setProduct(ProductDTO product) {
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
