package com.pv.pet_info.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

import com.pv.pet_info.domain.enumeration.FoodRating;

/**
 * A FoodReview.
 */
@Entity
@Table(name = "food_review")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "foodreview")
public class FoodReview implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "jhi_body")
    private String body;

    @Enumerated(EnumType.STRING)
    @Column(name = "rating")
    private FoodRating rating;

    @ManyToOne
    @JsonIgnoreProperties("")
    private User author;

    @ManyToOne
    @JsonIgnoreProperties("reviews")
    private Food food;

    @ManyToOne
    @JsonIgnoreProperties("foodReviews")
    private Pet pet;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public FoodReview title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public FoodReview body(String body) {
        this.body = body;
        return this;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public FoodRating getRating() {
        return rating;
    }

    public FoodReview rating(FoodRating rating) {
        this.rating = rating;
        return this;
    }

    public void setRating(FoodRating rating) {
        this.rating = rating;
    }

    public User getAuthor() {
        return author;
    }

    public FoodReview author(User user) {
        this.author = user;
        return this;
    }

    public void setAuthor(User user) {
        this.author = user;
    }

    public Food getFood() {
        return food;
    }

    public FoodReview food(Food food) {
        this.food = food;
        return this;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public Pet getPet() {
        return pet;
    }

    public FoodReview pet(Pet pet) {
        this.pet = pet;
        return this;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FoodReview foodReview = (FoodReview) o;
        if (foodReview.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), foodReview.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FoodReview{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", body='" + getBody() + "'" +
            ", rating='" + getRating() + "'" +
            "}";
    }
}
