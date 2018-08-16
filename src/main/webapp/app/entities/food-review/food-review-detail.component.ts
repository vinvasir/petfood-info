import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFoodReview } from 'app/shared/model/food-review.model';

@Component({
    selector: 'jhi-food-review-detail',
    templateUrl: './food-review-detail.component.html'
})
export class FoodReviewDetailComponent implements OnInit {
    foodReview: IFoodReview;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ foodReview }) => {
            this.foodReview = foodReview;
        });
    }

    previousState() {
        window.history.back();
    }
}
