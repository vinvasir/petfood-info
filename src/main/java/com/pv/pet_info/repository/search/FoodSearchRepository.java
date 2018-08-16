package com.pv.pet_info.repository.search;

import com.pv.pet_info.domain.Food;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Food entity.
 */
public interface FoodSearchRepository extends ElasticsearchRepository<Food, Long> {
}
