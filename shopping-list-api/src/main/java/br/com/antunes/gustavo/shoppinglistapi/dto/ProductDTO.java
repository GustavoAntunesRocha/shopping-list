package br.com.antunes.gustavo.shoppinglistapi.dto;

import br.com.antunes.gustavo.shoppinglistapi.entity.Product;

public class ProductDTO {

    private int id;
    private String name;
    private String description;
    
    public ProductDTO() {}

    public ProductDTO(int id, String name, String description) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
	}

	public static ProductDTO fromEntity(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getDescription()
        );
    }

    public Product toEntity() {
        return new Product(
                this.id,
                this.name,
                this.description
        );
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
