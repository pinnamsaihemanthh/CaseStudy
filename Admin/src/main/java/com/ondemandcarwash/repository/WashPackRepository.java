package com.ondemandcarwash.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ondemandcarwash.models.WashPack;

public interface WashPackRepository extends MongoRepository<WashPack, Integer> {

}
