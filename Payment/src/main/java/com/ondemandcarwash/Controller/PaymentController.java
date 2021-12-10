package com.ondemandcarwash.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ondemandcarwash.Model.PaymentModel;
import com.ondemandcarwash.Service.PaymentService;

@RestController
public class PaymentController {
	@Autowired
	public PaymentService paymentService;

	@PostMapping("/payment")
	public PaymentModel doPayment(@RequestBody PaymentModel payment) {
		return paymentService.doPay(payment);
	}
}
