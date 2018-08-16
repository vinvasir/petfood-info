import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IFood } from 'app/shared/model/food.model';
import { FoodService } from './food.service';

@Component({
    selector: 'jhi-food-update',
    templateUrl: './food-update.component.html'
})
export class FoodUpdateComponent implements OnInit {
    private _food: IFood;
    isSaving: boolean;

    constructor(private foodService: FoodService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ food }) => {
            this.food = food;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.food.id !== undefined) {
            this.subscribeToSaveResponse(this.foodService.update(this.food));
        } else {
            this.subscribeToSaveResponse(this.foodService.create(this.food));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IFood>>) {
        result.subscribe((res: HttpResponse<IFood>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get food() {
        return this._food;
    }

    set food(food: IFood) {
        this._food = food;
    }
}
