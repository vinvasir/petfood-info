import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PetInfoSharedModule } from 'app/shared';
import { PetInfoAdminModule } from 'app/admin/admin.module';
import {
    FoodReviewComponent,
    FoodReviewDetailComponent,
    FoodReviewUpdateComponent,
    FoodReviewDeletePopupComponent,
    FoodReviewDeleteDialogComponent,
    foodReviewRoute,
    foodReviewPopupRoute
} from './';

const ENTITY_STATES = [...foodReviewRoute, ...foodReviewPopupRoute];

@NgModule({
    imports: [PetInfoSharedModule, PetInfoAdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        FoodReviewComponent,
        FoodReviewDetailComponent,
        FoodReviewUpdateComponent,
        FoodReviewDeleteDialogComponent,
        FoodReviewDeletePopupComponent
    ],
    entryComponents: [FoodReviewComponent, FoodReviewUpdateComponent, FoodReviewDeleteDialogComponent, FoodReviewDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PetInfoFoodReviewModule {}
