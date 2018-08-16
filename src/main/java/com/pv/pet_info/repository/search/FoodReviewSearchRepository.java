package com.pv.pet_info.repository.search;

import com.pv.pet_info.domain.FoodReview;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the FoodReview entity.
 */
public interface FoodReviewSearchRepository extends ElasticsearchRepository<FoodReview, Long> {
}
