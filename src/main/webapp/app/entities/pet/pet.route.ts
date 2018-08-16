import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Pet } from 'app/shared/model/pet.model';
import { PetService } from './pet.service';
import { PetComponent } from './pet.component';
import { PetDetailComponent } from './pet-detail.component';
import { PetUpdateComponent } from './pet-update.component';
import { PetDeletePopupComponent } from './pet-delete-dialog.component';
import { IPet } from 'app/shared/model/pet.model';

@Injectable({ providedIn: 'root' })
export class PetResolve implements Resolve<IPet> {
    constructor(private service: PetService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((pet: HttpResponse<Pet>) => pet.body));
        }
        return of(new Pet());
    }
}

export const petRoute: Routes = [
    {
        path: 'pet',
        component: PetComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Pets'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'pet/:id/view',
        component: PetDetailComponent,
        resolve: {
            pet: PetResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Pets'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'pet/new',
        component: PetUpdateComponent,
        resolve: {
            pet: PetResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Pets'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'pet/:id/edit',
        component: PetUpdateComponent,
        resolve: {
            pet: PetResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Pets'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const petPopupRoute: Routes = [
    {
        path: 'pet/:id/delete',
        component: PetDeletePopupComponent,
        resolve: {
            pet: PetResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Pets'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
