package br.com.antunes.gustavo.shoppinglistapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.antunes.gustavo.shoppinglistapi.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Integer>{

}
