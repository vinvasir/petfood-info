package com.pv.pet_info.service.impl;

import com.pv.pet_info.service.FoodService;
import com.pv.pet_info.domain.Food;
import com.pv.pet_info.repository.FoodRepository;
import com.pv.pet_info.repository.search.FoodSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Food.
 */
@Service
@Transactional
public class FoodServiceImpl implements FoodService {

    private final Logger log = LoggerFactory.getLogger(FoodServiceImpl.class);

    private final FoodRepository foodRepository;

    private final FoodSearchRepository foodSearchRepository;

    public FoodServiceImpl(FoodRepository foodRepository, FoodSearchRepository foodSearchRepository) {
        this.foodRepository = foodRepository;
        this.foodSearchRepository = foodSearchRepository;
    }

    /**
     * Save a food.
     *
     * @param food the entity to save
     * @return the persisted entity
     */
    @Override
    public Food save(Food food) {
        log.debug("Request to save Food : {}", food);        Food result = foodRepository.save(food);
        foodSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the foods.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Food> findAll(Pageable pageable) {
        log.debug("Request to get all Foods");
        return foodRepository.findAll(pageable);
    }


    /**
     * Get one food by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Food> findOne(Long id) {
        log.debug("Request to get Food : {}", id);
        return foodRepository.findById(id);
    }

    /**
     * Delete the food by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Food : {}", id);
        foodRepository.deleteById(id);
        foodSearchRepository.deleteById(id);
    }

    /**
     * Search for the food corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Food> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Foods for query {}", query);
        return foodSearchRepository.search(queryStringQuery(query), pageable);    }
}
