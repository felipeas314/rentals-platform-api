package com.rentals.listings.dto;

import jakarta.validation.constraints.*;
import java.util.List;
import java.util.Map;

public class ListingDtos {
  public record Create(
    @NotBlank String hostId,
    @NotBlank @Size(min = 5, max = 140) String title,
    @Positive double price,
    @NotNull Double lon,
    @NotNull Double lat,
    List<@NotBlank String> amenities,
    Map<String, Object> rules
  ) {}

  public record Update(
    @NotBlank @Size(min = 5, max = 140) String title,
    @Positive double price,
    @NotNull Double lon,
    @NotNull Double lat,
    List<@NotBlank String> amenities,
    Map<String, Object> rules
  ) {}
}
