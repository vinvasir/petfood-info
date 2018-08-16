package com.pv.pet_info.repository.search;

import com.pv.pet_info.domain.Pet;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Pet entity.
 */
public interface PetSearchRepository extends ElasticsearchRepository<Pet, Long> {
}
