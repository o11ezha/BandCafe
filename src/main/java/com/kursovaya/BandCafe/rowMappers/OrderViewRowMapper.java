package com.kursovaya.BandCafe.rowMappers;

import com.kursovaya.BandCafe.Views.OrderView;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderViewRowMapper implements RowMapper<OrderView> {
    @Override
    public OrderView mapRow(ResultSet rs, int rowNum) throws SQLException {
        OrderView order = new OrderView();
        order.setAccountLogin(rs.getString("account_login"));
        order.setOrderID(rs.getString("order_id"));
        order.setOrderAddDate(rs.getString("order_add_date"));
        order.setOrderStatus(rs.getInt("order_status"));
        order.setOrderConfirm(rs.getBoolean("confirm_payment"));
        order.setOrderAddress(rs.getString("order_address"));
        order.setOrderAmount(rs.getInt("order_amount"));
        order.setMerchID(rs.getString("merch_id"));
        order.setMerchName(rs.getString("merch_name"));
        order.setMerchStatus(rs.getBoolean("merch_status"));
        order.setTotalPrice(rs.getBigDecimal("total_price"));
        order.setMerchDescSource(rs.getString("merch_description_source"));
        return order;
    }
}
