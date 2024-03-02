package com.baturin.QuickBuy.repository;

import com.baturin.QuickBuy.model.User;
import io.swagger.models.auth.In;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

    User findByEmail(String Email);
}
