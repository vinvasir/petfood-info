package com.pv.pet_info.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pv.pet_info.domain.FoodReview;
import com.pv.pet_info.domain.User;
import com.pv.pet_info.service.FoodReviewService;
import com.pv.pet_info.service.UserService;
import com.pv.pet_info.web.rest.errors.BadRequestAlertException;
import com.pv.pet_info.web.rest.util.HeaderUtil;
import com.pv.pet_info.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing FoodReview.
 */
@RestController
@RequestMapping("/api")
public class FoodReviewResource {

    private final Logger log = LoggerFactory.getLogger(FoodReviewResource.class);

    private static final String ENTITY_NAME = "foodReview";

    private final FoodReviewService foodReviewService;
    private final UserService userService;

    public FoodReviewResource(FoodReviewService foodReviewService, UserService userService) {
        this.foodReviewService = foodReviewService;
        this.userService = userService;
    }

    /**
     * POST  /food-reviews : Create a new foodReview.
     *
     * @param foodReview the foodReview to create
     * @return the ResponseEntity with status 201 (Created) and with body the new foodReview, or with status 400 (Bad Request) if the foodReview has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/food-reviews")
    @Timed
    public ResponseEntity<FoodReview> createFoodReview(@RequestBody FoodReview foodReview) throws URISyntaxException {
        log.debug("REST request to save FoodReview : {}", foodReview);
        if (foodReview.getId() != null) {
            throw new BadRequestAlertException("A new foodReview cannot already have an ID", ENTITY_NAME, "idexists");
        }
        User currentUser = userService.getUserWithAuthorities().get();
        foodReview.setAuthor(currentUser);
        FoodReview result = foodReviewService.save(foodReview);
        return ResponseEntity.created(new URI("/api/food-reviews/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /food-reviews : Updates an existing foodReview.
     *
     * @param foodReview the foodReview to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated foodReview,
     * or with status 400 (Bad Request) if the foodReview is not valid,
     * or with status 500 (Internal Server Error) if the foodReview couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/food-reviews")
    @Timed
    public ResponseEntity<FoodReview> updateFoodReview(@RequestBody FoodReview foodReview) throws URISyntaxException {
        log.debug("REST request to update FoodReview : {}", foodReview);
        if (foodReview.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FoodReview result = foodReviewService.save(foodReview);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, foodReview.getId().toString()))
            .body(result);
    }

    /**
     * GET  /food-reviews : get all the foodReviews.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of foodReviews in body
     */
    @GetMapping("/food-reviews")
    @Timed
    public ResponseEntity<List<FoodReview>> getAllFoodReviews(Pageable pageable) {
        log.debug("REST request to get a page of FoodReviews");
        Page<FoodReview> page = foodReviewService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/food-reviews");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /food-reviews/:id : get the "id" foodReview.
     *
     * @param id the id of the foodReview to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the foodReview, or with status 404 (Not Found)
     */
    @GetMapping("/food-reviews/{id}")
    @Timed
    public ResponseEntity<FoodReview> getFoodReview(@PathVariable Long id) {
        log.debug("REST request to get FoodReview : {}", id);
        Optional<FoodReview> foodReview = foodReviewService.findOne(id);
        return ResponseUtil.wrapOrNotFound(foodReview);
    }

    /**
     * DELETE  /food-reviews/:id : delete the "id" foodReview.
     *
     * @param id the id of the foodReview to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/food-reviews/{id}")
    @Timed
    public ResponseEntity<Void> deleteFoodReview(@PathVariable Long id) {
        log.debug("REST request to delete FoodReview : {}", id);
        foodReviewService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/food-reviews?query=:query : search for the foodReview corresponding
     * to the query.
     *
     * @param query the query of the foodReview search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/food-reviews")
    @Timed
    public ResponseEntity<List<FoodReview>> searchFoodReviews(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of FoodReviews for query {}", query);
        Page<FoodReview> page = foodReviewService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/food-reviews");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
