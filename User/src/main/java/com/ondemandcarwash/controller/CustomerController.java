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
import com.ondemandcarwash.model.Customer;
import com.ondemandcarwash.model.Order;
import com.ondemandcarwash.model.PaymentModel;
import com.ondemandcarwash.model.Ratings;
import com.ondemandcarwash.repository.CustomerRepository;
import com.ondemandcarwash.service.CustomerService;
import com.ondemandcarwash.util.JwtUtil;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/Customer")
public class CustomerController {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtil jwtUtil;

//To Subscribe to the green car wash like register
	
	@PostMapping("/subs")
	private ResponseEntity<?> subscibeCustomer(@RequestBody AuthenticationRequest authenticationRequest) {
		String username = authenticationRequest.getUsername();
		String password = authenticationRequest.getPassword();
		Customer customer = new Customer();
		customer.setUsername(username);
		customer.setPassword(password);
		try {
			customerRepository.save(customer);
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

		UserDetails loadedUser = customerService.loadUserByUsername(username);
		String generatedToken = jwtUtil.generateToken(loadedUser);

		return ResponseEntity.ok(new AuthenticationResponse(generatedToken));

		// return ResponseEntity.ok(new AuthenticationResponse("Successful
		// Authentication for user"+ username));
	}

	// Creating/ADDING Customer
	@PostMapping("/addcustomer")
	@ApiOperation("adding a customer")
	public Customer saveCustomer(@RequestBody Customer customer) {
		return customerService.addCustomer(customer);

	}

	// Reading all Customer
	@GetMapping("/allcustomers")
	@ApiOperation("reading all the customers")
	public List<Customer> findAllCustomers() {
		return customerService.getCustomers();

	}

	// Reading Customer by ID
	@GetMapping("/allcustomers/{id}")
	@ApiOperation("reading a customer with id")
	public Optional<Customer> getCustomerById(@PathVariable int id) throws ApiRequestException {
		return Optional.of(customerRepository.findById(id)
				.orElseThrow(() -> new ApiRequestException("CUSTOMER NOT FOUND WITH THIS ID ::")));
	}

	// Updating Customer Data by Id
	@PutMapping("/update/{id}")
	@ApiOperation("updating a customer with id")
	public ResponseEntity<Object> updateCustomer(@PathVariable int id, @RequestBody Customer customer) {
		boolean isCustomerExist = customerRepository.existsById(id);
		if (isCustomerExist) {
			customerRepository.save(customer);
			return new ResponseEntity<Object>("user updated successfully with id " + id, HttpStatus.OK);
		} else {
			throw new ApiRequestException("CAN NOT UPDATE AS USER NOT FOUND WITH THIS ID ::");
		}

	}

	// Deleting Customer Data by Id
	@DeleteMapping("/delete/{id}")
	@ApiOperation("deleting a customer with id")
	public ResponseEntity<Object> deleteCustomer(@PathVariable int id) {
		boolean isCustomerExist = customerRepository.existsById(id);
		if (isCustomerExist) {
			customerService.deleteById(id);
			return new ResponseEntity<Object>("user deleted with id" + id, HttpStatus.OK);
		} else {
			throw new ApiRequestException("CAN NOT DELETE AS USER NOT FOUND WITH THIS ID ::");
		}

	}

	
	// For Adding Order

	@PostMapping("/addorder")
	@ApiOperation("adding order")
	public String addOrder(@RequestBody Order order) {
		return restTemplate.postForObject("http://localhost:8082/Order/addorder", order, String.class);

	}

	// for Deleting Order

	@DeleteMapping("/cancelorder/{id}")
	public String deleteorder(@PathVariable("id") int id) {
		restTemplate.delete("http://localhost:8082/order/delete/" + id, String.class);
		return "Your Order is successfully Canceled " + id;
	}

	@PostMapping("/payment")
	@ApiOperation("for the payment")
	public String doPayment(@RequestBody PaymentModel payment) {
		return restTemplate.postForObject("http://localhost:8004/payment", payment, String.class);

	}

	// Reading All orders
	@GetMapping("/getallorders")
	@ApiOperation("Getting all the Orders")
	public String getAllOrder() {
		return restTemplate.getForObject("http://localhost:8082/Order/allorders", String.class);

	}

	// for adding ratings
	@PostMapping("/addratings")
	@ApiOperation("adding rating")
	public String addratings(@RequestBody Ratings ratings) {
		return restTemplate.postForObject("http://localhost:8000/Admin/addratings", ratings, String.class);

	}

	// Reading all the Ratings
	@GetMapping("/ratings")
	@ApiOperation("Getting all the Ratings")
	public String getAllRatings() {
		return restTemplate.getForObject("http://localhost:8000/Admin/allratings", String.class);
	}

	// Reading all the waskpacks
	@GetMapping("/washpack")
	@ApiOperation("Getting all the washpack")
	public String getAllPacks() {
		return restTemplate.getForObject("http://localhost:8000/Admin/allpacks", String.class);
	}

}
