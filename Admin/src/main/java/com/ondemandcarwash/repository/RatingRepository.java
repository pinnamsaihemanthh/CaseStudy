package com.ondemandcarwash.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ondemandcarwash.models.Ratings;

public interface RatingRepository extends MongoRepository<Ratings, Integer> {

}
