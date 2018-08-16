import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { FoodReview } from 'app/shared/model/food-review.model';
import { FoodReviewService } from './food-review.service';
import { FoodReviewComponent } from './food-review.component';
import { FoodReviewDetailComponent } from './food-review-detail.component';
import { FoodReviewUpdateComponent } from './food-review-update.component';
import { FoodReviewDeletePopupComponent } from './food-review-delete-dialog.component';
import { IFoodReview } from 'app/shared/model/food-review.model';

@Injectable({ providedIn: 'root' })
export class FoodReviewResolve implements Resolve<IFoodReview> {
    constructor(private service: FoodReviewService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((foodReview: HttpResponse<FoodReview>) => foodReview.body));
        }
        return of(new FoodReview());
    }
}

export const foodReviewRoute: Routes = [
    {
        path: 'food-review',
        component: FoodReviewComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'FoodReviews'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'food-review/:id/view',
        component: FoodReviewDetailComponent,
        resolve: {
            foodReview: FoodReviewResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FoodReviews'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'food-review/new',
        component: FoodReviewUpdateComponent,
        resolve: {
            foodReview: FoodReviewResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FoodReviews'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'food-review/:id/edit',
        component: FoodReviewUpdateComponent,
        resolve: {
            foodReview: FoodReviewResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FoodReviews'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const foodReviewPopupRoute: Routes = [
    {
        path: 'food-review/:id/delete',
        component: FoodReviewDeletePopupComponent,
        resolve: {
            foodReview: FoodReviewResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FoodReviews'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
