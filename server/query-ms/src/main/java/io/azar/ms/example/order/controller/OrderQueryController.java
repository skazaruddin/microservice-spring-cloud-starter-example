package io.azar.ms.example.order.controller;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import io.azar.ms.example.order.client.OrderClient;
import io.azar.ms.example.order.client.ProfileClient;
import io.azar.ms.example.order.dto.APIResponse;
import io.azar.ms.example.order.dto.Order;
import io.azar.ms.example.order.dto.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public/v1/query")
public class OrderQueryController {

	// We will add feign client to make API calls to order microservice and profilemicroservice
	
	@Autowired
	private OrderClient orderClient;
	
	@Autowired
	private ProfileClient profileClient;
	
//	https://myretailstore.com/public/v1/query/profile/{profileId}/order/{orderId}
	@GetMapping("/profile/{profileId}/order/{orderId}")
	public ResponseEntity<APIResponse> findOrder(@PathVariable String profileId,
												 @PathVariable String orderId) {
		
		ResponseEntity<Order> order = orderClient.getOrderById(orderId);
		ResponseEntity<Profile> profile = profileClient.getProfile(profileId);
		
		APIResponse apiResponse = new APIResponse();
		apiResponse.setProfile(profile.getBody());
		apiResponse.setOrders(Arrays.asList(order.getBody()));
		return new ResponseEntity<APIResponse>(apiResponse, HttpStatus.OK);
		
	}
	
	@GetMapping("/profile/{profileId}/orders")
	public ResponseEntity<APIResponse> findOrder(@PathVariable String profileId) throws InterruptedException, ExecutionException {
		
		ResponseEntity<List<Order>> order = orderClient.getAllOrders(profileId);
		ResponseEntity<Profile> profile = profileClient.getProfile(profileId);
		APIResponse apiResponse = new APIResponse();
		apiResponse.setProfile(profile.getBody());
		apiResponse.setOrders(order.getBody());
		return new ResponseEntity<APIResponse>(apiResponse, HttpStatus.OK);
	}
}
