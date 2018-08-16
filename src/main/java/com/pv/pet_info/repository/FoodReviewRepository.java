package com.pv.pet_info.repository;

import com.pv.pet_info.domain.FoodReview;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the FoodReview entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FoodReviewRepository extends JpaRepository<FoodReview, Long> {

    @Query("select food_review from FoodReview food_review where food_review.author.login = ?#{principal.username}")
    List<FoodReview> findByAuthorIsCurrentUser();

}
