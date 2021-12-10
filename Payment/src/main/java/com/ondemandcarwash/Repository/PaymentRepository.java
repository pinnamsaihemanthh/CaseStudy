package com.ondemandcarwash.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ondemandcarwash.Model.PaymentModel;


public interface PaymentRepository extends MongoRepository<PaymentModel, Integer> {




}
