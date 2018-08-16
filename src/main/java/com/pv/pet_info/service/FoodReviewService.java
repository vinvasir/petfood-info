package com.pv.pet_info.service;

import com.pv.pet_info.domain.FoodReview;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing FoodReview.
 */
public interface FoodReviewService {

    /**
     * Save a foodReview.
     *
     * @param foodReview the entity to save
     * @return the persisted entity
     */
    FoodReview save(FoodReview foodReview);

    /**
     * Get all the foodReviews.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<FoodReview> findAll(Pageable pageable);


    /**
     * Get the "id" foodReview.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<FoodReview> findOne(Long id);

    /**
     * Delete the "id" foodReview.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the foodReview corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<FoodReview> search(String query, Pageable pageable);
}
