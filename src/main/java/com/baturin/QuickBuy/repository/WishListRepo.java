package com.baturin.QuickBuy.repository;

import com.baturin.QuickBuy.model.User;
import com.baturin.QuickBuy.model.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishListRepo extends JpaRepository<WishList,Integer> {
    List<WishList> findAllByUserOrderByCreatedDateDesc(User user);
}
