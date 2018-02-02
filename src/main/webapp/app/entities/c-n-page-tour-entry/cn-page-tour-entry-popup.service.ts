import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { CNPageTourEntry } from './cn-page-tour-entry.model';
import { CNPageTourEntryService } from './cn-page-tour-entry.service';

@Injectable()
export class CNPageTourEntryPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private cNPageTourEntryService: CNPageTourEntryService

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
                this.cNPageTourEntryService.find(id).subscribe((cNPageTourEntry) => {
                    if (cNPageTourEntry.createTime) {
                        cNPageTourEntry.createTime = {
                            year: cNPageTourEntry.createTime.getFullYear(),
                            month: cNPageTourEntry.createTime.getMonth() + 1,
                            day: cNPageTourEntry.createTime.getDate()
                        };
                    }
                    if (cNPageTourEntry.updateTime) {
                        cNPageTourEntry.updateTime = {
                            year: cNPageTourEntry.updateTime.getFullYear(),
                            month: cNPageTourEntry.updateTime.getMonth() + 1,
                            day: cNPageTourEntry.updateTime.getDate()
                        };
                    }
                    this.ngbModalRef = this.cNPageTourEntryModalRef(component, cNPageTourEntry);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.cNPageTourEntryModalRef(component, new CNPageTourEntry());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    cNPageTourEntryModalRef(component: Component, cNPageTourEntry: CNPageTourEntry): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.cNPageTourEntry = cNPageTourEntry;
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
