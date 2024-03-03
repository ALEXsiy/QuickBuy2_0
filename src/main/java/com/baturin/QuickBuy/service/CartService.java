package com.baturin.QuickBuy.service;

import com.baturin.QuickBuy.dto.cart.AddToCartDto;
import com.baturin.QuickBuy.dto.cart.CartDto;
import com.baturin.QuickBuy.dto.cart.CartItemDto;
import com.baturin.QuickBuy.exception.CustomException;
import com.baturin.QuickBuy.model.Cart;
import com.baturin.QuickBuy.model.Product;
import com.baturin.QuickBuy.model.User;
import com.baturin.QuickBuy.repository.CartRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private ProductService productService;

    @Autowired
    private CartRepo cartRepo;

    public void addToCart(AddToCartDto addToCartDto, User user) {
        // validate if the product id is valid
        Product product = productService.findById(addToCartDto.getProductId());
        Cart cart = new Cart();
        cart.setProduct(product);
        cart.setUser(user);
        cart.setQuantity(addToCartDto.getQuantity());
        cart.setCreatedDate(new Date());
        // save the cart
        cartRepo.save(cart);
    }

    public CartDto listCartItems(User user) {
        List<Cart> cartList = cartRepo.findAllByUserOrderByCreatedDateDesc(user);
        List<CartItemDto> cartItems = new ArrayList<>();
        double totalCost = 0;
        for (Cart cart: cartList) {
            CartItemDto cartItemDto = new CartItemDto(cart);
            totalCost += cartItemDto.getQuantity() * cart.getProduct().getPrice();
            cartItems.add(cartItemDto);
        }
        CartDto cartDto = new CartDto();
        cartDto.setTotalCost(totalCost);
        cartDto.setCartItems(cartItems);
        return  cartDto;
    }

    public void deleteCartItem(Integer cartItemId, User user) {
        // the item id belongs to user
        Optional<Cart> optionalCart = cartRepo.findById(cartItemId);
        if (optionalCart.isEmpty()) {
            throw new CustomException("cart item id is invalid: " + cartItemId);
        }
        Cart cart = optionalCart.get();
        if (cart.getUser() != user) {
            throw  new CustomException("cart item does not belong to user: " +cartItemId);
        }
        cartRepo.delete(cart);
    }
}
