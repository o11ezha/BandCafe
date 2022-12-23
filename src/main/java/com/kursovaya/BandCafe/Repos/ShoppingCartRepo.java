package com.kursovaya.BandCafe.Repos;

import com.kursovaya.BandCafe.Entities.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class ShoppingCartRepo {

    @Autowired
    NamedParameterJdbcTemplate template;

    public ShoppingCart getShoppingCartByLogin(String login){
        String sql = "SELECT * FROM shopping_cart WHERE account_login = :login;";
        SqlParameterSource source = new MapSqlParameterSource("login", login);
        return template.queryForObject(sql, source, (rs, rowNum) -> {
            ShoppingCart shoppingCart = new ShoppingCart();
            shoppingCart.setShoppingCartID(rs.getString("shopping_cart_id"));
            shoppingCart.setUserMoney(rs.getBigDecimal("user_money"));
            shoppingCart.setAccountLogin(rs.getString("account_login"));
            return shoppingCart;
        });
    }
}
