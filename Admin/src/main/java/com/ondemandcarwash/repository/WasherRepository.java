package com.ondemandcarwash.repository;

import org.springframework.data.mongodb.repository.MongoRepository;


import com.ondemandcarwash.models.Washer;

public interface WasherRepository extends MongoRepository<Washer, Integer> {
	
	Washer findByUsername(String username);

}
