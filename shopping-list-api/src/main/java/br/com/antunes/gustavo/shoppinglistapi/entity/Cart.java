package br.com.antunes.gustavo.shoppinglistapi.entity;

import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

@Entity
public class Cart {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "cart_id", referencedColumnName = "id")
	private List<ProductCart> productCartList;
	
	private float price;
	
	private Date date;
	
	public Cart() {}

	public Cart(int id, List<ProductCart> productCartList, float price, Date date) {
		super();
		this.id = id;
		this.productCartList = productCartList;
		this.price = price;
		this.date = date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<ProductCart> getProductCartList() {
		return productCartList;
	}

	public void setProductCartList(List<ProductCart> productCartList) {
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
