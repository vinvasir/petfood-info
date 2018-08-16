import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PetInfoSharedModule } from 'app/shared';
import {
    PetComponent,
    PetDetailComponent,
    PetUpdateComponent,
    PetDeletePopupComponent,
    PetDeleteDialogComponent,
    petRoute,
    petPopupRoute
} from './';

const ENTITY_STATES = [...petRoute, ...petPopupRoute];

@NgModule({
    imports: [PetInfoSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [PetComponent, PetDetailComponent, PetUpdateComponent, PetDeleteDialogComponent, PetDeletePopupComponent],
    entryComponents: [PetComponent, PetUpdateComponent, PetDeleteDialogComponent, PetDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PetInfoPetModule {}
