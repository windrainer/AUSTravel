import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { PageTourEntry } from './page-tour-entry.model';
import { PageTourEntryService } from './page-tour-entry.service';

@Injectable()
export class PageTourEntryPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private pageTourEntryService: PageTourEntryService

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
                this.pageTourEntryService.find(id).subscribe((pageTourEntry) => {
                    if (pageTourEntry.createTime) {
                        pageTourEntry.createTime = {
                            year: pageTourEntry.createTime.getFullYear(),
                            month: pageTourEntry.createTime.getMonth() + 1,
                            day: pageTourEntry.createTime.getDate()
                        };
                    }
                    if (pageTourEntry.updateTime) {
                        pageTourEntry.updateTime = {
                            year: pageTourEntry.updateTime.getFullYear(),
                            month: pageTourEntry.updateTime.getMonth() + 1,
                            day: pageTourEntry.updateTime.getDate()
                        };
                    }
                    this.ngbModalRef = this.pageTourEntryModalRef(component, pageTourEntry);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.pageTourEntryModalRef(component, new PageTourEntry());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    pageTourEntryModalRef(component: Component, pageTourEntry: PageTourEntry): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.pageTourEntry = pageTourEntry;
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
