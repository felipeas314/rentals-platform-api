package com.rentals.listings.repository;

import com.rentals.listings.domain.Listing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ListingRepository extends MongoRepository<Listing, String>, ListingRepositoryCustom {
  Page<Listing> findByHostId(String hostId, Pageable pageable);
}
