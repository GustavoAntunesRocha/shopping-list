package br.com.antunes.gustavo.shoppinglistapi.dto;

import br.com.antunes.gustavo.shoppinglistapi.entity.ProductCart;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductCartDTO {

    private int id;
    private ProductDTO product;
    private float quantities;
    private float productPrice;

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

}
