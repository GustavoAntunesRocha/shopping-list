package br.com.antunes.gustavo.shoppinglistapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.antunes.gustavo.shoppinglistapi.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer>{

	UserEntity findByEmail(String email);
	
}
