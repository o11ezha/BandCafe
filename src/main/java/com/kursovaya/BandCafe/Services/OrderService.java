package com.kursovaya.BandCafe.Services;

import com.kursovaya.BandCafe.Entities.ShoppingOrder;
import com.kursovaya.BandCafe.Repos.OrderRepo;
import com.kursovaya.BandCafe.Views.OrderView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    OrderRepo orderRepo;

    public void addOrder(String login, String merchID, Integer amount, String address){
        orderRepo.addOrder(login, merchID, amount, address);
    }

    public void changeOrderStatus(String orderID, String login){
        orderRepo.changeOrderStatus(orderID, login);
    }

    public void donate(String login, BigDecimal amount){
        orderRepo.donate(login, amount);
    }

    public void confrimOrder(String orderID, String address, Integer amount, Boolean confirm){
        orderRepo.confrimOrder(orderID, address, amount, confirm);
    }

    public void deleteOrder(String orderID){
        orderRepo.deleteOrder(orderID);
    }

    public List<ShoppingOrder> getAllOrders(){
        return orderRepo.getAllOrders();
    }

    public List<OrderView> getOrdersByProfileID(String login){
        return orderRepo.getOrdersByProfileID(login);
    }

    public OrderView getOrdersByOrderID(String orderID){
        return orderRepo.getOrdersByOrderID(orderID);
    }

    public List<String> getOrdersByManagerLogin(String login){
        return orderRepo.getOrdersByManagerLogin(login);
    }
}
