package com.kursovaya.BandCafe.Repos;

import com.kursovaya.BandCafe.Entities.ShoppingOrder;
import com.kursovaya.BandCafe.Views.OrderView;
import com.kursovaya.BandCafe.rowMappers.OrderRowMapper;
import com.kursovaya.BandCafe.rowMappers.OrderViewRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.relational.core.sql.In;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.util.List;

@Repository
public class OrderRepo {

    @Autowired
    NamedParameterJdbcTemplate template;

    public List<ShoppingOrder> getAllOrders(){
        String sql = "SELECT * FROM shopping_order;";
        return template.query(sql, new OrderRowMapper());
    }

    public List<OrderView> getOrdersByProfileID(String login){
        String sql = "SELECT * FROM order_info WHERE account_login = :login;";
        SqlParameterSource source = new MapSqlParameterSource("login", login);
        return template.query(sql, source, new OrderViewRowMapper());
    }

    public OrderView getOrdersByOrderID(String orderID){
        String sql = "SELECT * FROM order_info WHERE order_id = :orderID;";
        SqlParameterSource source = new MapSqlParameterSource("orderID", orderID);
        return template.queryForObject(sql, source, new OrderViewRowMapper());
    }

    public List<String> getOrdersByManagerLogin(String login){
            String sql = "SELECT order_id FROM order_info oi JOIN merch m on m.merch_id = oi.merch_id JOIN member_group mg on m.group_id = mg.group_id WHERE group_manager = :login ;";
        SqlParameterSource source = new MapSqlParameterSource("login", login);
        return template.query(sql, source, (rs, rowNum) -> rs.getString("order_id"));
    }

    public void addOrder(String login, String merchID, Integer amount, String address){
        String sql = "CALL add_order(?,?,?,?)";
        template.getJdbcOperations().update(connection ->{
            CallableStatement cs = connection.prepareCall(sql);
            cs.setString(1, login);
            cs.setString(2, merchID);
            cs.setInt(3, amount);
            cs.setString(4, address);
            return cs;
        });
    }

    public void confrimOrder(String orderID, String orderAddress, Integer orderAmount, Boolean confirmed){
        String sql = "CALL update_order(?,?,?,?)";
        template.getJdbcOperations().update(connection ->{
            CallableStatement cs = connection.prepareCall(sql);
            cs.setString(1, orderID);
            cs.setString(2, orderAddress);
            cs.setInt(3, orderAmount);
            cs.setBoolean(4, confirmed);
            return cs;
        });
    }

    public void donate(String login, BigDecimal money){
        String sql = "CALL add_money(?,?)";
        template.getJdbcOperations().update(connection ->{
            CallableStatement cs = connection.prepareCall(sql);
            cs.setString(1, login);
            cs.setBigDecimal(2, money);
            return cs;
        });
    }

    public void changeOrderStatus(String orderID, String login){
        String sql = "CALL change_order_status(?,?)";
        template.getJdbcOperations().update(connection ->{
            CallableStatement cs = connection.prepareCall(sql);
            cs.setString(1, orderID);
            cs.setString(2, login);
            return cs;
        });
    }

    public void deleteOrder(String orderID){
        String sql = "CALL delete_order(?)";
        template.getJdbcOperations().update(connection ->{
            CallableStatement cs = connection.prepareCall(sql);
            cs.setString(1, orderID);
            return cs;
        });
    }
}
