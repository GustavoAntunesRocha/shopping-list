package br.com.antunes.gustavo.shoppinglistapi.dto;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import br.com.antunes.gustavo.shoppinglistapi.entity.Cart;
import br.com.antunes.gustavo.shoppinglistapi.entity.ProductCart;

public class CartDTO {

    private int id;
    private List<ProductCartDTO> productCartList;
    private float price;
    private Date date;
    
    public CartDTO() {}

    public CartDTO(int id, List<ProductCartDTO> productCartList, float price, Date date) {
		super();
		this.id = id;
		this.productCartList = productCartList;
		this.price = price;
		this.date = date;
	}

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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<ProductCartDTO> getProductCartList() {
		return productCartList;
	}

	public void setProductCartList(List<ProductCartDTO> productCartList) {
		this.productCartList = productCartList;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}

