package io.azar.ms.example.order.client;

import java.util.List;

import io.azar.ms.example.order.dto.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("ORDERMS")
public interface OrderClient {

	@GetMapping("/public/v1/order")
	ResponseEntity<List<Order>> getAllOrders(@RequestParam("profileId") String profileId);

	@GetMapping("/public/v1/order/{orderId}")
	ResponseEntity<Order> getOrderById(@PathVariable("orderId") String orderId);
	

}
