/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PetInfoTestModule } from '../../../test.module';
import { FoodReviewUpdateComponent } from 'app/entities/food-review/food-review-update.component';
import { FoodReviewService } from 'app/entities/food-review/food-review.service';
import { FoodReview } from 'app/shared/model/food-review.model';

describe('Component Tests', () => {
    describe('FoodReview Management Update Component', () => {
        let comp: FoodReviewUpdateComponent;
        let fixture: ComponentFixture<FoodReviewUpdateComponent>;
        let service: FoodReviewService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PetInfoTestModule],
                declarations: [FoodReviewUpdateComponent]
            })
                .overrideTemplate(FoodReviewUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(FoodReviewUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FoodReviewService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new FoodReview(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.foodReview = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new FoodReview();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.foodReview = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
