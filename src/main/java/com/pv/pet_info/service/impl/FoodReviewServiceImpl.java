package com.pv.pet_info.service.impl;

import com.pv.pet_info.service.FoodReviewService;
import com.pv.pet_info.domain.FoodReview;
import com.pv.pet_info.repository.FoodReviewRepository;
import com.pv.pet_info.repository.search.FoodReviewSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing FoodReview.
 */
@Service
@Transactional
public class FoodReviewServiceImpl implements FoodReviewService {

    private final Logger log = LoggerFactory.getLogger(FoodReviewServiceImpl.class);

    private final FoodReviewRepository foodReviewRepository;

    private final FoodReviewSearchRepository foodReviewSearchRepository;

    public FoodReviewServiceImpl(FoodReviewRepository foodReviewRepository, FoodReviewSearchRepository foodReviewSearchRepository) {
        this.foodReviewRepository = foodReviewRepository;
        this.foodReviewSearchRepository = foodReviewSearchRepository;
    }

    /**
     * Save a foodReview.
     *
     * @param foodReview the entity to save
     * @return the persisted entity
     */
    @Override
    public FoodReview save(FoodReview foodReview) {
        log.debug("Request to save FoodReview : {}", foodReview);
        FoodReview result = foodReviewRepository.save(foodReview);
        foodReviewSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the foodReviews.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FoodReview> findAll(Pageable pageable) {
        log.debug("Request to get all FoodReviews");
        return foodReviewRepository.findAll(pageable);
    }


    /**
     * Get one foodReview by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<FoodReview> findOne(Long id) {
        log.debug("Request to get FoodReview : {}", id);
        return foodReviewRepository.findById(id);
    }

    /**
     * Delete the foodReview by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete FoodReview : {}", id);
        foodReviewRepository.deleteById(id);
        foodReviewSearchRepository.deleteById(id);
    }

    /**
     * Search for the foodReview corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FoodReview> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of FoodReviews for query {}", query);
        return foodReviewSearchRepository.search(queryStringQuery(query), pageable);    }
}
