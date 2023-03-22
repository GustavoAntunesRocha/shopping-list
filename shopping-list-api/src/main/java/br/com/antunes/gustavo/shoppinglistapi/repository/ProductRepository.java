package br.com.antunes.gustavo.shoppinglistapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.antunes.gustavo.shoppinglistapi.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

}
