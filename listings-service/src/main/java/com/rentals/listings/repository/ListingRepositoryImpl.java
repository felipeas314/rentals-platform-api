package com.rentals.listings.repository;

import com.rentals.listings.domain.Listing;
import org.bson.Document;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Repository
public class ListingRepositoryImpl implements ListingRepositoryCustom {

  private final MongoTemplate template;

  public ListingRepositoryImpl(MongoTemplate template) { this.template = template; }

  @Override
  public Page<Listing> searchByLocationAndPrice(double lon, double lat, double radiusMeters,
                                                Double minPrice, Double maxPrice,
                                                Pageable pageable) {

    var nearStage = new Document("$geoNear", new Document()
      .append("near", new Document("type","Point").append("coordinates", List.of(lon, lat)))
      .append("distanceField", "dist")
      .append("spherical", true)
      .append("maxDistance", radiusMeters));

    var priceMatch = new Criteria();
    if (minPrice != null) priceMatch = priceMatch.and("price").gte(minPrice);
    if (maxPrice != null) priceMatch = priceMatch.and("price").lte(maxPrice);

    var pipeline = newAggregation(
      new CustomAggregationOperation(nearStage),
      match(priceMatch),
      sort(Sort.by(Sort.Direction.ASC, "dist")),
      skip(pageable.getOffset()),
      limit(pageable.getPageSize())
    );

    var results = template.aggregate(pipeline, "listings", Listing.class).getMappedResults();

    // Para total, poderíamos rodar uma contagem; para simplicidade, só paginar resultados.
    return new PageImpl<>(results, pageable, Pageable.unpaged().getPageSize());
  }

  record CustomAggregationOperation(Document operation) implements AggregationOperation {
    @Override public Document toDocument(AggregationOperationContext context) { return operation; }
  }
}
