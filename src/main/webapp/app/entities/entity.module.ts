import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { PetInfoPetModule } from './pet/pet.module';
import { PetInfoFoodModule } from './food/food.module';
import { PetInfoFoodReviewModule } from './food-review/food-review.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        PetInfoPetModule,
        PetInfoFoodModule,
        PetInfoFoodReviewModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PetInfoEntityModule {}
