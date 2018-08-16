package com.pv.pet_info.web.rest;

import com.pv.pet_info.PetInfoApp;

import com.pv.pet_info.domain.FoodReview;
import com.pv.pet_info.repository.FoodReviewRepository;
import com.pv.pet_info.repository.search.FoodReviewSearchRepository;
import com.pv.pet_info.service.FoodReviewService;
import com.pv.pet_info.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;


import static com.pv.pet_info.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.pv.pet_info.domain.enumeration.FoodRating;
/**
 * Test class for the FoodReviewResource REST controller.
 *
 * @see FoodReviewResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PetInfoApp.class)
public class FoodReviewResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_BODY = "AAAAAAAAAA";
    private static final String UPDATED_BODY = "BBBBBBBBBB";

    private static final FoodRating DEFAULT_RATING = FoodRating.ONE;
    private static final FoodRating UPDATED_RATING = FoodRating.TWO;

    @Autowired
    private FoodReviewRepository foodReviewRepository;

    

    @Autowired
    private FoodReviewService foodReviewService;

    /**
     * This repository is mocked in the com.pv.pet_info.repository.search test package.
     *
     * @see com.pv.pet_info.repository.search.FoodReviewSearchRepositoryMockConfiguration
     */
    @Autowired
    private FoodReviewSearchRepository mockFoodReviewSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFoodReviewMockMvc;

    private FoodReview foodReview;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FoodReviewResource foodReviewResource = new FoodReviewResource(foodReviewService);
        this.restFoodReviewMockMvc = MockMvcBuilders.standaloneSetup(foodReviewResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FoodReview createEntity(EntityManager em) {
        FoodReview foodReview = new FoodReview()
            .title(DEFAULT_TITLE)
            .body(DEFAULT_BODY)
            .rating(DEFAULT_RATING);
        return foodReview;
    }

    @Before
    public void initTest() {
        foodReview = createEntity(em);
    }

    @Test
    @Transactional
    public void createFoodReview() throws Exception {
        int databaseSizeBeforeCreate = foodReviewRepository.findAll().size();

        // Create the FoodReview
        restFoodReviewMockMvc.perform(post("/api/food-reviews")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foodReview)))
            .andExpect(status().isCreated());

        // Validate the FoodReview in the database
        List<FoodReview> foodReviewList = foodReviewRepository.findAll();
        assertThat(foodReviewList).hasSize(databaseSizeBeforeCreate + 1);
        FoodReview testFoodReview = foodReviewList.get(foodReviewList.size() - 1);
        assertThat(testFoodReview.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testFoodReview.getBody()).isEqualTo(DEFAULT_BODY);
        assertThat(testFoodReview.getRating()).isEqualTo(DEFAULT_RATING);

        // Validate the FoodReview in Elasticsearch
        verify(mockFoodReviewSearchRepository, times(1)).save(testFoodReview);
    }

    @Test
    @Transactional
    public void createFoodReviewWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = foodReviewRepository.findAll().size();

        // Create the FoodReview with an existing ID
        foodReview.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFoodReviewMockMvc.perform(post("/api/food-reviews")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foodReview)))
            .andExpect(status().isBadRequest());

        // Validate the FoodReview in the database
        List<FoodReview> foodReviewList = foodReviewRepository.findAll();
        assertThat(foodReviewList).hasSize(databaseSizeBeforeCreate);

        // Validate the FoodReview in Elasticsearch
        verify(mockFoodReviewSearchRepository, times(0)).save(foodReview);
    }

    @Test
    @Transactional
    public void getAllFoodReviews() throws Exception {
        // Initialize the database
        foodReviewRepository.saveAndFlush(foodReview);

        // Get all the foodReviewList
        restFoodReviewMockMvc.perform(get("/api/food-reviews?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(foodReview.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].body").value(hasItem(DEFAULT_BODY.toString())))
            .andExpect(jsonPath("$.[*].rating").value(hasItem(DEFAULT_RATING.toString())));
    }
    

    @Test
    @Transactional
    public void getFoodReview() throws Exception {
        // Initialize the database
        foodReviewRepository.saveAndFlush(foodReview);

        // Get the foodReview
        restFoodReviewMockMvc.perform(get("/api/food-reviews/{id}", foodReview.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(foodReview.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.body").value(DEFAULT_BODY.toString()))
            .andExpect(jsonPath("$.rating").value(DEFAULT_RATING.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingFoodReview() throws Exception {
        // Get the foodReview
        restFoodReviewMockMvc.perform(get("/api/food-reviews/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFoodReview() throws Exception {
        // Initialize the database
        foodReviewService.save(foodReview);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockFoodReviewSearchRepository);

        int databaseSizeBeforeUpdate = foodReviewRepository.findAll().size();

        // Update the foodReview
        FoodReview updatedFoodReview = foodReviewRepository.findById(foodReview.getId()).get();
        // Disconnect from session so that the updates on updatedFoodReview are not directly saved in db
        em.detach(updatedFoodReview);
        updatedFoodReview
            .title(UPDATED_TITLE)
            .body(UPDATED_BODY)
            .rating(UPDATED_RATING);

        restFoodReviewMockMvc.perform(put("/api/food-reviews")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFoodReview)))
            .andExpect(status().isOk());

        // Validate the FoodReview in the database
        List<FoodReview> foodReviewList = foodReviewRepository.findAll();
        assertThat(foodReviewList).hasSize(databaseSizeBeforeUpdate);
        FoodReview testFoodReview = foodReviewList.get(foodReviewList.size() - 1);
        assertThat(testFoodReview.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testFoodReview.getBody()).isEqualTo(UPDATED_BODY);
        assertThat(testFoodReview.getRating()).isEqualTo(UPDATED_RATING);

        // Validate the FoodReview in Elasticsearch
        verify(mockFoodReviewSearchRepository, times(1)).save(testFoodReview);
    }

    @Test
    @Transactional
    public void updateNonExistingFoodReview() throws Exception {
        int databaseSizeBeforeUpdate = foodReviewRepository.findAll().size();

        // Create the FoodReview

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFoodReviewMockMvc.perform(put("/api/food-reviews")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foodReview)))
            .andExpect(status().isBadRequest());

        // Validate the FoodReview in the database
        List<FoodReview> foodReviewList = foodReviewRepository.findAll();
        assertThat(foodReviewList).hasSize(databaseSizeBeforeUpdate);

        // Validate the FoodReview in Elasticsearch
        verify(mockFoodReviewSearchRepository, times(0)).save(foodReview);
    }

    @Test
    @Transactional
    public void deleteFoodReview() throws Exception {
        // Initialize the database
        foodReviewService.save(foodReview);

        int databaseSizeBeforeDelete = foodReviewRepository.findAll().size();

        // Get the foodReview
        restFoodReviewMockMvc.perform(delete("/api/food-reviews/{id}", foodReview.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FoodReview> foodReviewList = foodReviewRepository.findAll();
        assertThat(foodReviewList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the FoodReview in Elasticsearch
        verify(mockFoodReviewSearchRepository, times(1)).deleteById(foodReview.getId());
    }

    @Test
    @Transactional
    public void searchFoodReview() throws Exception {
        // Initialize the database
        foodReviewService.save(foodReview);
        when(mockFoodReviewSearchRepository.search(queryStringQuery("id:" + foodReview.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(foodReview), PageRequest.of(0, 1), 1));
        // Search the foodReview
        restFoodReviewMockMvc.perform(get("/api/_search/food-reviews?query=id:" + foodReview.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(foodReview.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].body").value(hasItem(DEFAULT_BODY.toString())))
            .andExpect(jsonPath("$.[*].rating").value(hasItem(DEFAULT_RATING.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FoodReview.class);
        FoodReview foodReview1 = new FoodReview();
        foodReview1.setId(1L);
        FoodReview foodReview2 = new FoodReview();
        foodReview2.setId(foodReview1.getId());
        assertThat(foodReview1).isEqualTo(foodReview2);
        foodReview2.setId(2L);
        assertThat(foodReview1).isNotEqualTo(foodReview2);
        foodReview1.setId(null);
        assertThat(foodReview1).isNotEqualTo(foodReview2);
    }
}
