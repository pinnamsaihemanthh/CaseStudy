package com.ondemandcarwash.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.ondemandcarwash.exception.ApiRequestException;
import com.ondemandcarwash.model.AuthenticationRequest;
import com.ondemandcarwash.model.AuthenticationResponse;
import com.ondemandcarwash.model.Washer;
import com.ondemandcarwash.repository.WasherRepository;
import com.ondemandcarwash.service.WasherService;
import com.ondemandcarwash.util.JwtUtil;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/Washer")
public class WasherController {
	@Autowired
	public RestTemplate restTemplate;

	@Autowired
	private WasherService washerService;

	@Autowired
	private WasherRepository washerRepository;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtUtil;

	// To Subscribe to the green car wash like register

	@PostMapping("/subs")
	private ResponseEntity<?> subscibeCustomer(@RequestBody AuthenticationRequest authenticationRequest) {
		String username = authenticationRequest.getUsername();
		String password = authenticationRequest.getPassword();
		Washer washer = new Washer();
		washer.setUsername(username);
		washer.setPassword(password);
		try {
			washerRepository.save(washer);
		} catch (Exception e) {
			return ResponseEntity.ok(new AuthenticationResponse("Error creating user with username: " + username));
		}
		return ResponseEntity.ok(new AuthenticationResponse("Created user with username: " + username));
	}

	// For authentication
	@PostMapping("/auth")
	private ResponseEntity<?> authenticateCustomer(@RequestBody AuthenticationRequest authenticationRequest) {
		String username = authenticationRequest.getUsername();
		String password = authenticationRequest.getPassword();

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (Exception e) {
			return ResponseEntity.ok(new AuthenticationResponse("No user found with username: " + username));
		}

		UserDetails loadedUser = washerService.loadUserByUsername(username);
		String generatedToken = jwtUtil.generateToken(loadedUser);

		return ResponseEntity.ok(new AuthenticationResponse(generatedToken));

		// return ResponseEntity.ok(new AuthenticationResponse("Successful
		// Authentication for user"+ username));
	}

	// Creating/Adding Washer
	@PostMapping("/addwasher")
	public Washer saveWasher(@RequestBody Washer washer) {
		return washerService.addWasher(washer);
	}

	// Reading all washer
	@GetMapping("/allwashers")
	public List<Washer> findAllWashers() {
		return washerService.getWashers();

	}

	// Reading Washer by ID
	@GetMapping("/allwashers/{id}")
	public Optional<Washer> getWasherById(@PathVariable int id) throws ApiRequestException {
		return Optional.of(washerRepository.findById(id)
				.orElseThrow(() -> new ApiRequestException("WASHER NOT FOUND WITH THIS ID ::")));

	}

	// Updating Customer Data by Id
	@PutMapping("/update/{id}")
	public ResponseEntity<Object> updateWasher(@PathVariable int id, @RequestBody Washer washer) {
		boolean isWasherExist = washerRepository.existsById(id);
		if (isWasherExist) {
			washerRepository.save(washer);
			return new ResponseEntity<Object>("Washer updated successfully with id " + id, HttpStatus.OK);
		} else {
			throw new ApiRequestException("CAN NOT UPDATE AS WASHER NOT FOUND WITH THIS ID ::");
		}

	}

	// Deleting Customer Data by Id
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Object> deleteCustomer(@PathVariable int id) {
		boolean isWasherExist = washerRepository.existsById(id);
		if (isWasherExist) {
			washerService.deleteById(id);
			return new ResponseEntity<Object>("Washer deleted with id" + id, HttpStatus.OK);
		} else {
			throw new ApiRequestException("CAN NOT DELETE AS WASHER NOT FOUND WITH THIS ID ::");
		}

	}

//Reading All orders
	@GetMapping("/getallorders")
	@ApiOperation("Getting all the Orders")
	public String getAllOrder() {
		return restTemplate.getForObject("http://localhost:8082/Order/allorders", String.class);

	}

//Reading all the Ratings
	@GetMapping("/ratings")
	@ApiOperation("Getting all the Ratings")
	public String getAllRatings() {
		return restTemplate.getForObject("http://localhost:8000/Admin/allratings", String.class);
	}
}
