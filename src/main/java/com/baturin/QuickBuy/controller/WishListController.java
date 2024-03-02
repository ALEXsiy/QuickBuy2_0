package com.baturin.QuickBuy.controller;

import com.baturin.QuickBuy.common.ApiResponse;
import com.baturin.QuickBuy.dto.ProductDto;
import com.baturin.QuickBuy.model.Product;
import com.baturin.QuickBuy.model.User;
import com.baturin.QuickBuy.model.WishList;
import com.baturin.QuickBuy.service.AuthenticationService;
import com.baturin.QuickBuy.service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Entity;
import java.util.List;

@RestController
@RequestMapping("/wishlist")
public class WishListController {

    @Autowired
    WishListService wishListService;

    @Autowired
    AuthenticationService authenticationService;


    // save product as wishlist item
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addToWishList(@RequestBody Product product,
                                                     @RequestParam("token") String token) {
        // authenticate the token
        authenticationService.authenticate(token);
        // find the user
        User user = authenticationService.getUser(token);
        // save the item in wishlist
        WishList wishList = new WishList(user, product);
        wishListService.createWishlist(wishList);
        ApiResponse apiResponse = new ApiResponse(true, "Added to wishlist");
        return  new ResponseEntity<>(apiResponse, HttpStatus.CREATED);

    }

    // get all wishlist item for a user
    @GetMapping("/list/{token}")
    public ResponseEntity<List<ProductDto>> getWishList(@PathVariable("token") String token) {
        // authenticate the token
        authenticationService.authenticate(token);
        // find the user
        User user = authenticationService.getUser(token);
        List<ProductDto> productDtos = wishListService.getWishListForUser(user);
        return new ResponseEntity<>(productDtos, HttpStatus.OK);

    }



}
