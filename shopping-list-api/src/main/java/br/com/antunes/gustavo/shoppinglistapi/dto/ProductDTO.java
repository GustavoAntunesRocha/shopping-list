package br.com.antunes.gustavo.shoppinglistapi.dto;

import br.com.antunes.gustavo.shoppinglistapi.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private int id;
    private String name;
    private String description;

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

}
