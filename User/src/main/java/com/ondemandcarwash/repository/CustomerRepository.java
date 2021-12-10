package com.ondemandcarwash.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ondemandcarwash.model.Customer;

public interface CustomerRepository extends MongoRepository<Customer, Integer>{

}
