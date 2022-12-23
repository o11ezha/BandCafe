package com.kursovaya.BandCafe.Controllers;

import com.kursovaya.BandCafe.Entities.*;
import com.kursovaya.BandCafe.Services.MerchService;
import com.kursovaya.BandCafe.Services.OrderService;
import com.kursovaya.BandCafe.Services.ShoppingCartService;
import com.kursovaya.BandCafe.Views.OrderView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.Proxy;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    ShoppingCartService shoppingCartService;

    @Autowired
    MerchService merchService;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/profile/{profileID}/orders")
    public String returnOrders(@PathVariable("profileID") String profileID,
                               Principal principal,
                               Model model) {
        ShoppingCart shoppingCart = shoppingCartService.getShoppingCartByLogin(principal.getName());
        List<OrderView> orders = orderService.getOrdersByProfileID(principal.getName());

        model.addAttribute("orders", orders);
        model.addAttribute("shoppingCart", shoppingCart);
        return "shoppingCartView";
    }

    @GetMapping("/manageOrders")
    public String returnAllOrders(Principal principal,
                                  Model model) {
        List<String> ordersIDs = orderService.getOrdersByManagerLogin(principal.getName());
        List<OrderView> orders = new ArrayList<>();
        for (String orderID : ordersIDs) {
            orders.add(orderService.getOrdersByOrderID(orderID));
        }

        System.out.println(orders);
        model.addAttribute("orders", orders);
        return "manageOrders";
    }

    @PostMapping("/manageOrders")
    public String updateStatus(@RequestParam("orderID") String orderIDD,
                               @RequestParam("status") String status,
                               Principal principal,
                               Model model) {
        if (!(status.equals("")) && status != null){
            Integer statusInt = Integer.parseInt(status);
            if (statusInt < 2) {
                orderService.changeOrderStatus(orderIDD, principal.getName());
            }
        }
        return "redirect:/manageOrders";
    }

    @RequestMapping("/profile/{profileID}/orders/donate")
    public String donate(@PathVariable("profileID") String profileID,
                         @RequestParam BigDecimal money,
                         Principal principal,
                         Model model) {
        System.out.println(money);
        ShoppingCart shoppingCart = shoppingCartService.getShoppingCartByLogin(principal.getName());
        List<OrderView> orders = orderService.getOrdersByProfileID(principal.getName());

        if (money.compareTo(BigDecimal.ZERO) > 0) {
            orderService.donate( principal.getName(), money);
        }

        model.addAttribute("orders", orders);
        model.addAttribute("shoppingCart", shoppingCart);

        return "redirect:/profile/" + profileID + "/orders";
    }

    OrderView order2 = new OrderView();

    @GetMapping("/profile/{profileID}/orders/{orderID}")
    public String getOrder(@PathVariable("profileID") String profileID,
                         @PathVariable("orderID") String orderID,
                         Principal principal,
                         Model model) {
        OrderView order = orderService.getOrdersByOrderID(orderID);
        order2 = order;
        Merch merch = merchService.getMerchByID(order.getMerchID());
        model.addAttribute("order", order);
        model.addAttribute("merch", merch);
        model.addAttribute("login", principal.getName());

        return "orderView";
    }

    @PostMapping("/profile/{profileID}/orders/{orderID}")
    public String updateOrder(@PathVariable("profileID") String profileID,
                              @PathVariable("orderID") String orderID,
                              @RequestParam(value = "amount", required = false) Integer amount,
                              @RequestParam(value = "address", required = false) String address,
                              Principal principal,
                              String errorAmount,
                              String errorAddress,
                              String errorMoney,
                              Model model) {
        ShoppingCart shoppingCart = shoppingCartService.getShoppingCartByLogin(principal.getName());

        Merch merch = merchService.getMerchByID(order2.getMerchID());
        model.addAttribute("order", order2);
        model.addAttribute("merch", merch);
        model.addAttribute("login", principal.getName());

        if (amount <=  0 || !(amount.equals(order2.getOrderAmount()))   || amount > merch.getMerchAmount()) {
            model.addAttribute("errorAmount", "Укажите количество верно");
            return "orderView";
        }

        if (address == null || address.isEmpty()) {
            model.addAttribute("errorAddress", "Укажите адрес доставки");
            return  "orderView";
        }

        if ( (order2.getTotalPrice().compareTo(shoppingCart.getUserMoney())) > 0) {
            model.addAttribute("errorMoney", "Недостаточно средств");
            return "orderView";
        }

        orderService.confrimOrder(orderID, address, amount, true);
        return "redirect:/profile/" + profileID + "/orders";
    }


    @PostMapping("/merch/{merchName}")
    public String returnOrderPage(@PathVariable("merchName") String merchName,
                                  @RequestParam Integer inputQuantity,
                                  @RequestParam String address,
                                  Principal principal,
                                  Model model) {
        String trueName = merchName.replace("%20", " ")
                .replace("+", " ");
        Merch merch = merchService.getMerchByName(trueName);
        model.addAttribute("merch", merch);

        if (inputQuantity == null || inputQuantity < 1) {
            return "redirect:/merch/" + trueName;
        }

        if (inputQuantity > merch.getMerchAmount()) {
            return "redirect:/merch/" + trueName;
        }

        if (address == null || address.equals("")) {
            return "redirect:/merch/" + trueName;
        }

        orderService.addOrder(principal.getName(), merch.getMerchID(), inputQuantity, address);
        return "redirect:/merch/" + trueName;
    }

    @GetMapping("/profile/{profileID}/orders/{orderID}/delete")
    public String deleteOrder(@PathVariable("profileID") String profileID,
                              @PathVariable("orderID") String orderID,
                              Principal principal,
                              Model model) {
        OrderView order = orderService.getOrdersByOrderID(orderID);
        if (!order.getOrderConfirm()) {
            orderService.deleteOrder(orderID);
        }
        return "redirect:/profile/" + profileID + "/orders";
    }
}
