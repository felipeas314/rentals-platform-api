package com.rentals.listings.service;

import com.rentals.listings.dto.ListingDtos;
import com.rentals.listings.domain.Listing;
import com.rentals.listings.repository.ListingRepository;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Service;

@Service
public class ListingService {

  private final ListingRepository repo;

  public ListingService(ListingRepository repo) { this.repo = repo; }

  public Listing create(ListingDtos.Create in) {
    var loc = new GeoJsonPoint(in.lon(), in.lat());
    var listing = new Listing(null, in.hostId(), in.title().trim(), in.price(), loc, in.amenities(), in.rules());
    return repo.save(listing);
  }

  public Listing update(String id, ListingDtos.Update in) {
    var l = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("listing não encontrado"));
    l.setTitle(in.title().trim());
    l.setPrice(in.price());
    l.setLocation(new GeoJsonPoint(in.lon(), in.lat()));
    l.setAmenities(in.amenities());
    l.setRules(in.rules());
    return repo.save(l);
  }

  public Listing get(String id) { return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("listing não encontrado")); }

  public Page<Listing> listByHost(String hostId, int page, int size) {
    return repo.findByHostId(hostId, PageRequest.of(page, size));
  }

  public Page<Listing> search(double lon, double lat, double radiusMeters,
                              Double minPrice, Double maxPrice, int page, int size) {
    return repo.searchByLocationAndPrice(lon, lat, radiusMeters, minPrice, maxPrice, PageRequest.of(page, size));
  }

  public void delete(String id) { repo.deleteById(id); }
}
