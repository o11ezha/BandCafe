package com.kursovaya.BandCafe.rowMappers;

import com.kursovaya.BandCafe.Entities.ShoppingOrder;
import com.kursovaya.BandCafe.Views.MerchView;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderRowMapper implements RowMapper<ShoppingOrder> {
    @Override
    public ShoppingOrder mapRow(ResultSet rs, int rowNum) throws SQLException {
        ShoppingOrder order = new ShoppingOrder();
        order.setOrderID(rs.getString("order_id"));
        order.setOrderAddDate(rs.getString("order_add_date"));
        order.setOrderStatus(rs.getInt("order_status"));
        order.setOrderConfrim(rs.getBoolean("confirm_payment"));
        order.setOrderAddress(rs.getString("order_address"));
        order.setOrderAmount(rs.getInt("order_amount"));
        order.setShoppingCartID(rs.getString("shopping_cart_id"));
        order.setMerchID(rs.getString("merch_id"));
        return order;
    }
}
