/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PetInfoTestModule } from '../../../test.module';
import { FoodReviewDeleteDialogComponent } from 'app/entities/food-review/food-review-delete-dialog.component';
import { FoodReviewService } from 'app/entities/food-review/food-review.service';

describe('Component Tests', () => {
    describe('FoodReview Management Delete Component', () => {
        let comp: FoodReviewDeleteDialogComponent;
        let fixture: ComponentFixture<FoodReviewDeleteDialogComponent>;
        let service: FoodReviewService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PetInfoTestModule],
                declarations: [FoodReviewDeleteDialogComponent]
            })
                .overrideTemplate(FoodReviewDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(FoodReviewDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FoodReviewService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
