package com.ondemandcarwash.Service;

import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ondemandcarwash.Model.PaymentModel;
import com.ondemandcarwash.Repository.PaymentRepository;

@Service
	public class PaymentService {

	@Autowired
	private PaymentRepository paymentRepository;

	public PaymentModel doPay(PaymentModel payment) {
	payment.setPaymentStatus(paymentStatus());
	payment.setTxId(UUID.randomUUID().toString());
	return paymentRepository.save(payment);

	}



	private String paymentStatus() {
	// TODO Auto-generated method stub
	return new Random().nextBoolean()?"success":"failure";
	}

	}

