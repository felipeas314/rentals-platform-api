package com.rentals.listings.domain;

import jakarta.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import java.util.List;
import java.util.Map;

@Document("listings")
public class Listing {

  @Id
  private String id;

  @NotBlank @Indexed
  private String hostId; // id do usuário HOST

  @NotBlank @Size(min = 5, max = 140)
  private String title;

  @Positive
  private double price;

  @GeoSpatialIndexed(name = "idx_location_2dsphere")
  private GeoJsonPoint location; // longitude, latitude

  private List<@NotBlank String> amenities;

  private Map<String, Object> rules;

  // getters/setters/constructors
  public Listing() {}
  public Listing(String id, String hostId, String title, double price, GeoJsonPoint location,
                 List<String> amenities, Map<String, Object> rules) {
    this.id = id; this.hostId = hostId; this.title = title; this.price = price;
    this.location = location; this.amenities = amenities; this.rules = rules;
  }
  public String getId() { return id; }
  public String getHostId() { return hostId; }
  public String getTitle() { return title; }
  public double getPrice() { return price; }
  public GeoJsonPoint getLocation() { return location; }
  public List<String> getAmenities() { return amenities; }
  public Map<String, Object> getRules() { return rules; }
  public void setId(String id) { this.id = id; }
  public void setHostId(String hostId) { this.hostId = hostId; }
  public void setTitle(String title) { this.title = title; }
  public void setPrice(double price) { this.price = price; }
  public void setLocation(GeoJsonPoint location) { this.location = location; }
  public void setAmenities(List<String> amenities) { this.amenities = amenities; }
  public void setRules(Map<String, Object> rules) { this.rules = rules; }
}
