import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IFoodReview } from 'app/shared/model/food-review.model';
import { FoodReviewService } from './food-review.service';
import { IUser, UserService } from 'app/core';
import { IFood } from 'app/shared/model/food.model';
import { FoodService } from 'app/entities/food';
import { IPet } from 'app/shared/model/pet.model';
import { PetService } from 'app/entities/pet';

@Component({
    selector: 'jhi-food-review-update',
    templateUrl: './food-review-update.component.html'
})
export class FoodReviewUpdateComponent implements OnInit {
    private _foodReview: IFoodReview;
    isSaving: boolean;

    users: IUser[];

    foods: IFood[];

    pets: IPet[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private foodReviewService: FoodReviewService,
        private userService: UserService,
        private foodService: FoodService,
        private petService: PetService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ foodReview }) => {
            this.foodReview = foodReview;
        });
        this.userService.query().subscribe(
            (res: HttpResponse<IUser[]>) => {
                this.users = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.foodService.query().subscribe(
            (res: HttpResponse<IFood[]>) => {
                this.foods = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.petService.query().subscribe(
            (res: HttpResponse<IPet[]>) => {
                this.pets = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.foodReview.id !== undefined) {
            this.subscribeToSaveResponse(this.foodReviewService.update(this.foodReview));
        } else {
            this.subscribeToSaveResponse(this.foodReviewService.create(this.foodReview));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IFoodReview>>) {
        result.subscribe((res: HttpResponse<IFoodReview>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackUserById(index: number, item: IUser) {
        return item.id;
    }

    trackFoodById(index: number, item: IFood) {
        return item.id;
    }

    trackPetById(index: number, item: IPet) {
        return item.id;
    }
    get foodReview() {
        return this._foodReview;
    }

    set foodReview(foodReview: IFoodReview) {
        this._foodReview = foodReview;
    }
}
