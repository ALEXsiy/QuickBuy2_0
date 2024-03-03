package com.baturin.QuickBuy.repository;

import com.baturin.QuickBuy.model.Cart;
import com.baturin.QuickBuy.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepo extends JpaRepository<Cart, Integer> {
    List<Cart> findAllByUserOrderByCreatedDateDesc(User user);
}
