package com.baturin.QuickBuy.service;

import com.baturin.QuickBuy.exception.AuthenticationFailException;
import com.baturin.QuickBuy.model.AuthenticationToken;
import com.baturin.QuickBuy.model.User;
import com.baturin.QuickBuy.repository.TokenRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthenticationService {
    @Autowired
    private TokenRepo tokenRepo;
    public void saveConfirmationToken(AuthenticationToken authenticationToken) {
        tokenRepo.save(authenticationToken);
    }

    public AuthenticationToken getToken(User user) {
        return tokenRepo.findByUser(user);
    }
    public User getUser(String token) {
        final AuthenticationToken authenticationToken = tokenRepo.findByToken(token);
        if(Objects.isNull(authenticationToken)) {
            return null;
        }
        // authenticationToken is not null
        return authenticationToken.getUser();
    }

    public void authenticate(String token) throws AuthenticationFailException {
        // null check
        if(Objects.isNull(token)) {
            // throw an exception
            throw new AuthenticationFailException("token not present");
        }
        if(Objects.isNull(getUser(token))) {
            throw new AuthenticationFailException("token not valid");
        }
    }
}
