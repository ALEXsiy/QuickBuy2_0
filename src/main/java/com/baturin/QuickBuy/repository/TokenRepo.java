package com.baturin.QuickBuy.repository;


import com.baturin.QuickBuy.model.AuthenticationToken;
import com.baturin.QuickBuy.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepo extends JpaRepository<AuthenticationToken, Integer> {
    AuthenticationToken findByUser(User user);
    AuthenticationToken findByToken(String token);

}
