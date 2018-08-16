import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFoodReview } from 'app/shared/model/food-review.model';
import { FoodReviewService } from './food-review.service';

@Component({
    selector: 'jhi-food-review-delete-dialog',
    templateUrl: './food-review-delete-dialog.component.html'
})
export class FoodReviewDeleteDialogComponent {
    foodReview: IFoodReview;

    constructor(private foodReviewService: FoodReviewService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.foodReviewService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'foodReviewListModification',
                content: 'Deleted an foodReview'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-food-review-delete-popup',
    template: ''
})
export class FoodReviewDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ foodReview }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(FoodReviewDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.foodReview = foodReview;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
