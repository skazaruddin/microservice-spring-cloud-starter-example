package io.azar.ms.example.order.client;

import io.azar.ms.example.order.dto.Profile;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("PROFILEMS")
public interface ProfileClient {
	
	@GetMapping("/public/v1/profile/{id}")
	ResponseEntity<Profile> getProfile(@PathVariable(name="id") String profileId);
	

}
