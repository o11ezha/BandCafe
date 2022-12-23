package com.kursovaya.BandCafe.Services;

import com.kursovaya.BandCafe.Entities.ShoppingCart;
import com.kursovaya.BandCafe.Repos.ShoppingCartRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartService {

    @Autowired
    ShoppingCartRepo shoppingCartRepo;

    public ShoppingCart getShoppingCartByLogin(String login){
        return shoppingCartRepo.getShoppingCartByLogin(login);
    }
}
