import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Tour } from './tour.model';
import { TourService } from './tour.service';

@Injectable()
export class TourPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private tourService: TourService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.tourService.find(id).subscribe((tour) => {
                    if (tour.createTime) {
                        tour.createTime = {
                            year: tour.createTime.getFullYear(),
                            month: tour.createTime.getMonth() + 1,
                            day: tour.createTime.getDate()
                        };
                    }
                    if (tour.updateTime) {
                        tour.updateTime = {
                            year: tour.updateTime.getFullYear(),
                            month: tour.updateTime.getMonth() + 1,
                            day: tour.updateTime.getDate()
                        };
                    }
                    this.ngbModalRef = this.tourModalRef(component, tour);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.tourModalRef(component, new Tour());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    tourModalRef(component: Component, tour: Tour): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.tour = tour;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
