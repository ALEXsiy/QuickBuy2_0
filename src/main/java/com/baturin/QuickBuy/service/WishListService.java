package com.baturin.QuickBuy.service;

import com.baturin.QuickBuy.dto.ProductDto;
import com.baturin.QuickBuy.model.User;
import com.baturin.QuickBuy.model.WishList;
import com.baturin.QuickBuy.repository.WishListRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WishListService {
    @Autowired
    private WishListRepo wishListRepo;
    @Autowired
    private ProductService productService;

    public void createWishlist(WishList wishList) {
        wishListRepo.save(wishList);
    }

        public List<ProductDto> getWishListForUser(User user) {
            final List<WishList> wishLists = wishListRepo.findAllByUserOrderByCreatedDateDesc(user);
            List<ProductDto> productDtos = new ArrayList<>();
            for (WishList wishList: wishLists) {
                productDtos.add(productService.getProductDto(wishList.getProduct()));
            }

            return productDtos;
        }


}
