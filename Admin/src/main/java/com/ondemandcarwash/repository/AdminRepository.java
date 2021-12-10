package com.ondemandcarwash.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ondemandcarwash.models.Admin;

public interface AdminRepository extends MongoRepository<Admin, Integer> {

}
