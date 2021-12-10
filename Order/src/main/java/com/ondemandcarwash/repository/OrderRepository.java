package com.ondemandcarwash.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ondemandcarwash.model.Order;

public interface OrderRepository extends MongoRepository<Order, Integer> {

}
