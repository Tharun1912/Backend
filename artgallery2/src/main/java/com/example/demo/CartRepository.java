package com.example.demo;


import com.example.demo.Cart;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
	List<Cart> findAll();
}
