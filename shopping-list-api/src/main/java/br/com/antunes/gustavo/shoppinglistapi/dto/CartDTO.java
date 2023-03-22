package br.com.antunes.gustavo.shoppinglistapi.dto;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import br.com.antunes.gustavo.shoppinglistapi.entity.Cart;
import br.com.antunes.gustavo.shoppinglistapi.entity.ProductCart;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {

    private int id;
    private List<ProductCartDTO> productCartList;
    private float price;
    private Date date;

    public static CartDTO fromEntity(Cart cart) {
        List<ProductCartDTO> productCartDTOList = cart.getProductCartList()
                .stream()
                .map(ProductCartDTO::fromEntity)
                .collect(Collectors.toList());

        return new CartDTO(
                cart.getId(),
                productCartDTOList,
                cart.getPrice(),
                cart.getDate()
        );
    }

    public Cart toEntity() {
        List<ProductCart> productCartList = this.productCartList
                .stream()
                .map(ProductCartDTO::toEntity)
                .collect(Collectors.toList());

        return new Cart(
                this.id,
                productCartList,
                this.price,
                this.date
        );
    }

}

