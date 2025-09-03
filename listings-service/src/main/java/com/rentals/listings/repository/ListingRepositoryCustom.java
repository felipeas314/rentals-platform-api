package com.rentals.listings.repository;

import com.rentals.listings.domain.Listing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ListingRepositoryCustom {
  Page<Listing> searchByLocationAndPrice(double lon, double lat, double radiusMeters,
                                         Double minPrice, Double maxPrice,
                                         Pageable pageable);
}
