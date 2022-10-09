package io.azar.ms.example.profile.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import io.azar.ms.example.profile.dto.Profile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.micrometer.core.instrument.util.IOUtils;

@RestController
@RequestMapping("/public/v1/profile")
public class ProfileController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProfileController.class);

	Map<String, Profile> profileDatabase = new HashMap<>();
	
	@PostConstruct
	void initializeDatabaseOnStartUp() {
		Profile profile1 = new Profile();
		profile1.setId("1");
		profile1.setDob(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		profile1.setName("Jawad");
		profile1.setGender("Male");
		
		profileDatabase.put("1", profile1);
		
		Profile profile2 = new Profile();
		profile2.setId("2");
		profile2.setDob(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		profile2.setName("Roshan");
		profile2.setGender("Male");
		
		profileDatabase.put("2", profile2);
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Profile> getProfile(@PathVariable String id) {
		
		LOGGER.info("PROFILE MS-INCOMING REQUEST FOR PROFILE ID={}",id);
		Profile profile = profileDatabase.get(id);
		return new ResponseEntity<>(profile, HttpStatus.OK);		
	}
	

	@PostMapping
	public ResponseEntity<?> saveProfile(@RequestBody Profile profile) {

		if(profileDatabase.containsKey(profile.getId())){
			// throw error cant save the profile profile already exists
		  return new ResponseEntity<>("Profile already exist try again", HttpStatus.BAD_REQUEST);
		}
		profileDatabase.put(profile.getId(), profile);
		return new ResponseEntity<>(profile, HttpStatus.OK);		
	}
	
	@PutMapping
	public ResponseEntity<?> updateProfile(@RequestBody Profile profile) {
	
		if(!profileDatabase.containsKey(profile.getId())){
			// throw error profile doesnt exists
		  return new ResponseEntity<>("Profile does not exist try again", HttpStatus.BAD_REQUEST);
		}
		
		profileDatabase.put(profile.getId(), profile);
		
		return new ResponseEntity<>(profile, HttpStatus.OK);		
	}
	
}
