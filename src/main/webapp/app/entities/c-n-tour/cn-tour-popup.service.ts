import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { CNTour } from './cn-tour.model';
import { CNTourService } from './cn-tour.service';

@Injectable()
export class CNTourPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private cNTourService: CNTourService

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
                this.cNTourService.find(id).subscribe((cNTour) => {
                    if (cNTour.createTime) {
                        cNTour.createTime = {
                            year: cNTour.createTime.getFullYear(),
                            month: cNTour.createTime.getMonth() + 1,
                            day: cNTour.createTime.getDate()
                        };
                    }
                    if (cNTour.updateTime) {
                        cNTour.updateTime = {
                            year: cNTour.updateTime.getFullYear(),
                            month: cNTour.updateTime.getMonth() + 1,
                            day: cNTour.updateTime.getDate()
                        };
                    }
                    this.ngbModalRef = this.cNTourModalRef(component, cNTour);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.cNTourModalRef(component, new CNTour());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    cNTourModalRef(component: Component, cNTour: CNTour): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.cNTour = cNTour;
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
