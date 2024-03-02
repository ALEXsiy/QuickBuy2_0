package com.baturin.QuickBuy.service;

import com.baturin.QuickBuy.dto.ResponseDto;
import com.baturin.QuickBuy.dto.user.SignInDto;
import com.baturin.QuickBuy.dto.user.SignInResponseDto;
import com.baturin.QuickBuy.dto.user.SignupDto;
import com.baturin.QuickBuy.exception.AuthenticationFailException;
import com.baturin.QuickBuy.exception.CustomException;
import com.baturin.QuickBuy.model.AuthenticationToken;
import com.baturin.QuickBuy.model.User;
import com.baturin.QuickBuy.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AuthenticationService authenticationService;

    @Transactional
    public ResponseDto signUp(SignupDto signupDto) {
        //check if user is already present
        if(Objects.nonNull(userRepo.findByEmail(signupDto.getEmail()))){
            //we have a user
            throw new CustomException("user already present");
        }
        //hash the password
        String encryptedPassword = signupDto.getPassword();
        try{
            encryptedPassword = hashPassword(signupDto.getPassword());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        //save the user
        User user = new User(signupDto.getFirstName(),signupDto.getLastName(),
                signupDto.getEmail(),encryptedPassword);
        userRepo.save(user);
        //create token
        final AuthenticationToken authenticationToken = new AuthenticationToken(user);
        authenticationService.saveConfirmationToken(authenticationToken);
        ResponseDto responseDto = new ResponseDto("success","user created successfully");
        return  responseDto;
    }

    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        String hash = DatatypeConverter
                .printHexBinary(digest).toUpperCase();
        return hash;
    }

    public SignInResponseDto signIn(SignInDto signInDto) {
        //find user by email
        User user = userRepo.findByEmail(signInDto.getEmail());
        if (Objects.isNull(user)) {
            throw new AuthenticationFailException("user is not valid");
        }
        //hash the password
        try {
            //compare the password in DB
            if (!user.getPassword().equals(hashPassword(signInDto.getPassword()))) {
                throw new AuthenticationFailException("wrong password");
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        //if password match
        AuthenticationToken token = authenticationService.getToken(user);
        //check the token
        if (Objects.isNull(token)) {
            throw new CustomException("token is not present");
        }
        //return response
        return new SignInResponseDto("success", token.getToken());
    }
}
