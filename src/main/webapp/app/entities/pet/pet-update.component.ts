import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IPet } from 'app/shared/model/pet.model';
import { PetService } from './pet.service';

@Component({
    selector: 'jhi-pet-update',
    templateUrl: './pet-update.component.html'
})
export class PetUpdateComponent implements OnInit {
    private _pet: IPet;
    isSaving: boolean;

    constructor(private petService: PetService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ pet }) => {
            this.pet = pet;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.pet.id !== undefined) {
            this.subscribeToSaveResponse(this.petService.update(this.pet));
        } else {
            this.subscribeToSaveResponse(this.petService.create(this.pet));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPet>>) {
        result.subscribe((res: HttpResponse<IPet>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get pet() {
        return this._pet;
    }

    set pet(pet: IPet) {
        this._pet = pet;
    }
}
