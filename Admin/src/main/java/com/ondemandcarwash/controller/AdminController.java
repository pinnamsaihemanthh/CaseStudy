package com.ondemandcarwash.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.ondemandcarwash.Service.AdminService;
import com.ondemandcarwash.exception.ApiRequestException;
import com.ondemandcarwash.models.Admin;
import com.ondemandcarwash.models.AuthenticationRequest;
import com.ondemandcarwash.models.AuthenticationResponse;
import com.ondemandcarwash.models.Order;
import com.ondemandcarwash.models.Ratings;
import com.ondemandcarwash.models.WashPack;
import com.ondemandcarwash.models.Washer;
import com.ondemandcarwash.repository.AdminRepository;
import com.ondemandcarwash.repository.RatingRepository;
import com.ondemandcarwash.repository.WashPackRepository;
import com.ondemandcarwash.util.JwtUtil;

import io.swagger.annotations.ApiOperation;


@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/Admin")
public class AdminController {
	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private WashPackRepository washRepository;
	@Autowired
	private RatingRepository ratingRepository;
	@Autowired
	private WashPackRepository washPackRepository;

	@Autowired
	private AdminService adminService;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtUtil;

	// To Subscribe to the green car wash like register

	@PostMapping("/subs")
	private ResponseEntity<?> subscibeCustomer(@RequestBody AuthenticationRequest authenticationRequest) {
		String username = authenticationRequest.getUsername();
		String password = authenticationRequest.getPassword();
		Admin admin = new Admin();
		admin.setUsername(username);
		admin.setPassword(password);
		try {
			adminRepository.save(admin);
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

		UserDetails loadedUser = adminService.loadUserByUsername(username);
		String generatedToken = jwtUtil.generateToken(loadedUser);

		return ResponseEntity.ok(new AuthenticationResponse(generatedToken));

		// return ResponseEntity.ok(new AuthenticationResponse("Successful
		// Authentication for user"+ username));
	}

//Adding Admin
	@PostMapping("/addadmin")
	@ApiOperation("adding a admin")
	public String saveAdmin(@RequestBody Admin admin) {
		adminRepository.save(admin);
		return "Admin Saved Successfully with id:" + admin.getId();
	}

//Reading All admin
	@GetMapping("/alladmins")
	@ApiOperation("reading all the admins")
	public List<Admin> getallAdmins() {
		return adminRepository.findAll();
	}
//Reading Admin by ID

	@GetMapping("/alladmins/{id}")
	@ApiOperation("reading a admin with id")
	public Optional<Admin> getadminbyId(@PathVariable int id) {
		return adminRepository.findById(id);

	}

	// Updating Admin Data by Id
	@PutMapping("/update/{id}")
	@ApiOperation("updating a admin with id")
	public ResponseEntity<Object> updateCustomer(@PathVariable int id, @RequestBody Admin admin) {
		boolean isAdminExist = adminRepository.existsById(id);
		if (isAdminExist) {
			adminRepository.save(admin);
			return new ResponseEntity<Object>("admin updated successfully with id " + id, HttpStatus.OK);
		} else {
			throw new ApiRequestException("CAN NOT UPDATE AS ADMIN NOT FOUND WITH THIS ID ::");
		}
	}

	// Deleting Admin Data by Id
	@PutMapping("/delete/{id}")
	@ApiOperation("deleting a admin with id")
	public ResponseEntity<Object> deleteAdmin(@PathVariable int id, @RequestBody Admin admin) {
		boolean isAdminExist = adminRepository.existsById(id);
		if (isAdminExist) {
			adminRepository.delete(admin);
			return new ResponseEntity<Object>("admin deleted successfully with id " + id, HttpStatus.OK);
		} else {
			throw new ApiRequestException("CAN NOT DELETE AS ADMIN NOT FOUND WITH THIS ID ::");
		}
	}

	@PostMapping("/addpack")
	@ApiOperation("adding a washpack")
	public String savepack(@RequestBody WashPack pack) {
		washRepository.save(pack);

		return "washpack Saved Successfully with id:" + pack.getId();

	}

	@GetMapping("/allpacks")
	@ApiOperation("reading all the washpack")
	public List<WashPack> getpack() {
		return washRepository.findAll();

		// return repo1.findAll();
	}

	// Updating washpack Data by Id
	@PutMapping("/updatewashpack/{id}")
	@ApiOperation("updating a washpack with id")
	public ResponseEntity<Object> updateWashPack(@PathVariable int id, @RequestBody WashPack washPack) {
		boolean isWashPackExist = washPackRepository.existsById(id);
		if (isWashPackExist) {
			washPackRepository.save(washPack);
			return new ResponseEntity<Object>("washpack updated successfully with id " + id, HttpStatus.OK);
		} else {
			throw new ApiRequestException("CAN NOT UPDATE AS WASHPACK NOT FOUND WITH THIS ID ::");
		}
	}

	// deleting washpack Data by Id
	@PutMapping("/deletewashpack/{id}")
	@ApiOperation("deleting a washpack with id")
	public ResponseEntity<Object> deleteWashPack(@PathVariable int id, @RequestBody WashPack washPack) {
		boolean isWashPackExist = washPackRepository.existsById(id);
		if (isWashPackExist) {
			washPackRepository.delete(washPack);
			return new ResponseEntity<Object>("washpack deleted successfully with id " + id, HttpStatus.OK);
		} else {
			throw new ApiRequestException("CAN NOT UPDATE AS WASHPACK NOT FOUND WITH THIS ID ::");
		}
	}

	// Adding Washer
	@PostMapping("/addwasher")
	@ApiOperation("adding a washer")
	public String addWasher(@RequestBody Washer washer) {
		return restTemplate.postForObject("http://localhost:8003/Washer/addwasher", washer, String.class);
	}

	@PostMapping("/addratings")
	@ApiOperation("adding a rating")
	public String saverating(@RequestBody Ratings rating) {

		ratingRepository.save(rating);
		return " Thanks for Your Valuable feedback";
	}

	@GetMapping("/allratings")
	@ApiOperation("reading all the allratings")
	public List<Ratings> getuser() {

		return ratingRepository.findAll();
	}

//Reading orders By
	@GetMapping("/getallorders/{id}")
	@ApiOperation("reading order with id")
	public Order getOrderById(@PathVariable("id") int id) {
		return restTemplate.getForObject("http://localhost:8082/order/orders/" + id, Order.class);
	}

//Reading All orders
	@GetMapping("/getallorders")
	@ApiOperation("reading all orders")
	public String getallOrder() {
		return restTemplate.getForObject("http://localhost:8082/order/allorders", String.class);
	}

	// Reading All customers
	@GetMapping("/getallcustomers")
	@ApiOperation("reading all customers")
	public String getallCustomers() {
		return restTemplate.getForObject("http://localhost:8090/customer/allcustomers", String.class);
	}

	// Reading All washers
	@GetMapping("/getallwashers")
	@ApiOperation("reading all washers")
	public String getallwhashers() {
		return restTemplate.getForObject("http://localhost:8003/washer/allwashers", String.class);
	}

}
