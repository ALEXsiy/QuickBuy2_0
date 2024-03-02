package com.baturin.QuickBuy.controller;

import com.baturin.QuickBuy.dto.ResponseDto;
import com.baturin.QuickBuy.dto.user.SignInDto;
import com.baturin.QuickBuy.dto.user.SignInResponseDto;
import com.baturin.QuickBuy.dto.user.SignupDto;
import com.baturin.QuickBuy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    //signup
    @PostMapping("/signup")
    public ResponseDto signup(@RequestBody SignupDto signupDto){
        return userService.signUp(signupDto);
    }

    //signIn
    @PostMapping("/signin")
    public SignInResponseDto signIn(@RequestBody SignInDto signInDto){
        return userService.signIn(signInDto);
    }

}
