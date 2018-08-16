package com.pv.pet_info.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pv.pet_info.domain.Food;
import com.pv.pet_info.service.FoodService;
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
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Food.
 */
@RestController
@RequestMapping("/api")
public class FoodResource {

    private final Logger log = LoggerFactory.getLogger(FoodResource.class);

    private static final String ENTITY_NAME = "food";

    private final FoodService foodService;

    public FoodResource(FoodService foodService) {
        this.foodService = foodService;
    }

    /**
     * POST  /foods : Create a new food.
     *
     * @param food the food to create
     * @return the ResponseEntity with status 201 (Created) and with body the new food, or with status 400 (Bad Request) if the food has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/foods")
    @Timed
    public ResponseEntity<Food> createFood(@RequestBody Food food) throws URISyntaxException {
        log.debug("REST request to save Food : {}", food);
        if (food.getId() != null) {
            throw new BadRequestAlertException("A new food cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Food result = foodService.save(food);
        return ResponseEntity.created(new URI("/api/foods/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /foods : Updates an existing food.
     *
     * @param food the food to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated food,
     * or with status 400 (Bad Request) if the food is not valid,
     * or with status 500 (Internal Server Error) if the food couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/foods")
    @Timed
    public ResponseEntity<Food> updateFood(@RequestBody Food food) throws URISyntaxException {
        log.debug("REST request to update Food : {}", food);
        if (food.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Food result = foodService.save(food);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, food.getId().toString()))
            .body(result);
    }

    /**
     * GET  /foods : get all the foods.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of foods in body
     */
    @GetMapping("/foods")
    @Timed
    public ResponseEntity<List<Food>> getAllFoods(Pageable pageable) {
        log.debug("REST request to get a page of Foods");
        Page<Food> page = foodService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/foods");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /foods/:id : get the "id" food.
     *
     * @param id the id of the food to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the food, or with status 404 (Not Found)
     */
    @GetMapping("/foods/{id}")
    @Timed
    public ResponseEntity<Food> getFood(@PathVariable Long id) {
        log.debug("REST request to get Food : {}", id);
        Optional<Food> food = foodService.findOne(id);
        return ResponseUtil.wrapOrNotFound(food);
    }

    /**
     * DELETE  /foods/:id : delete the "id" food.
     *
     * @param id the id of the food to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/foods/{id}")
    @Timed
    public ResponseEntity<Void> deleteFood(@PathVariable Long id) {
        log.debug("REST request to delete Food : {}", id);
        foodService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/foods?query=:query : search for the food corresponding
     * to the query.
     *
     * @param query the query of the food search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/foods")
    @Timed
    public ResponseEntity<List<Food>> searchFoods(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Foods for query {}", query);
        Page<Food> page = foodService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/foods");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
