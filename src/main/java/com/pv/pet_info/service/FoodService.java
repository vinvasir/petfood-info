package com.pv.pet_info.service;

import com.pv.pet_info.domain.Food;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Food.
 */
public interface FoodService {

    /**
     * Save a food.
     *
     * @param food the entity to save
     * @return the persisted entity
     */
    Food save(Food food);

    /**
     * Get all the foods.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Food> findAll(Pageable pageable);


    /**
     * Get the "id" food.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Food> findOne(Long id);

    /**
     * Delete the "id" food.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the food corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Food> search(String query, Pageable pageable);
}
