/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PetInfoTestModule } from '../../../test.module';
import { FoodReviewDetailComponent } from 'app/entities/food-review/food-review-detail.component';
import { FoodReview } from 'app/shared/model/food-review.model';

describe('Component Tests', () => {
    describe('FoodReview Management Detail Component', () => {
        let comp: FoodReviewDetailComponent;
        let fixture: ComponentFixture<FoodReviewDetailComponent>;
        const route = ({ data: of({ foodReview: new FoodReview(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PetInfoTestModule],
                declarations: [FoodReviewDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(FoodReviewDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(FoodReviewDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.foodReview).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
