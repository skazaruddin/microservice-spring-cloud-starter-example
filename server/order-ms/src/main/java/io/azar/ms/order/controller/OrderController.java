package io.azar.ms.order.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import io.azar.ms.order.dto.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public/v1/order")
public class OrderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    Map<String, Order> orderDatabase = new HashMap<>();

    @PostConstruct
    void initializeDatabaseWithOrdersOnStartUp() {
        Order order1 = new Order();
        order1.setId("1");
        order1.setArticleNumber("0001");
        order1.setProfileId("1");
        order1.setCreatedDate(new Date());
        order1.setQuantity("10");
        order1.setArticleCode("APPLE_IPHONE14_SKY_BLUE");

        orderDatabase.put("1", order1);

        Order order2 = new Order();
        order2.setId("2");
        order2.setArticleNumber("0002");
        order2.setProfileId("2");
        order2.setCreatedDate(new Date());
        order2.setQuantity("1");
        order2.setArticleCode("APPLE_IPHONE12_SKY_BLUE");

        orderDatabase.put("2", order2);

        Order order3 = new Order();
        order3.setId("3");
        order3.setArticleNumber("0054");
        order3.setProfileId("2");
        order3.setCreatedDate(new Date());
        order3.setQuantity("1");
        order3.setArticleCode("SAMSUNG_TV");

        orderDatabase.put("3", order3);

    }
    // return all orders against a profile
    @GetMapping
    public ResponseEntity<List<Order>> findOrdersByProfileId(@RequestParam("profileId") String profileId) {
        LOGGER.info("INCOMING REQUEST FOR PROFILE ID={}", profileId);
        List<Order> orders = new ArrayList<>();
        orderDatabase.forEach((key, value) -> {
            if (value.getProfileId().contentEquals(profileId)) {
                orders.add(value);
            }
        });
        LOGGER.info("RESPONSE FOR PROFILE ID={}, {}", profileId, orders);
        return new ResponseEntity<>(orders, orders.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }
    // return details of a single order
    // If order was not found, return NOT_FOUND
    // If order id belongs to profileid or not. If order found but didnt match profile, then return BAD_REQUEST
    @GetMapping("/{orderId}")
    public ResponseEntity<Order> findOrder(@PathVariable("orderId") String orderId, @RequestParam("profileId") String profileId) {
        LOGGER.info("INCOMING REQUEST FOR ORDERID={}", orderId);
        Order order = orderDatabase.getOrDefault(orderId, null);
        if (order == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        if (!order.getProfileId().contentEquals(profileId)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        LOGGER.info("RESPONSE FOR ORDERID={}, {}", orderId, order);
        return new ResponseEntity<>(order,HttpStatus.OK);
    }




}
