package com.rentals.listings.controller;

import com.rentals.listings.dto.ListingDtos;
import com.rentals.listings.domain.Listing;
import com.rentals.listings.service.ListingService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/listings")
public class ListingController {
  private final ListingService svc;
  public ListingController(ListingService svc) { this.svc = svc; }

  @PostMapping
  public Listing create(@RequestBody @Valid ListingDtos.Create in) { return svc.create(in); }

  @PutMapping("/{id}")
  public Listing update(@PathVariable String id, @RequestBody @Valid ListingDtos.Update in) { return svc.update(id, in); }

  @GetMapping("/{id}")
  public Listing get(@PathVariable String id) { return svc.get(id); }

  @GetMapping("/by-host/{hostId}")
  public Page<Listing> byHost(@PathVariable String hostId,
                              @RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "20") int size) {
    return svc.listByHost(hostId, page, size);
  }

  @GetMapping("/search")
  public Page<Listing> search(@RequestParam double lon,
                              @RequestParam double lat,
                              @RequestParam(defaultValue = "3000") double radiusMeters,
                              @RequestParam(required = false) Double minPrice,
                              @RequestParam(required = false) Double maxPrice,
                              @RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "20") int size) {
    return svc.search(lon, lat, radiusMeters, minPrice, maxPrice, page, size);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable String id) { svc.delete(id); }
}
