import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PageTourEntry } from './page-tour-entry.model';
import { PageTourEntryPopupService } from './page-tour-entry-popup.service';
import { PageTourEntryService } from './page-tour-entry.service';

@Component({
    selector: 'jhi-page-tour-entry-delete-dialog',
    templateUrl: './page-tour-entry-delete-dialog.component.html'
})
export class PageTourEntryDeleteDialogComponent {

    pageTourEntry: PageTourEntry;

    constructor(
        private pageTourEntryService: PageTourEntryService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.pageTourEntryService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'pageTourEntryListModification',
                content: 'Deleted an pageTourEntry'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-page-tour-entry-delete-popup',
    template: ''
})
export class PageTourEntryDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private pageTourEntryPopupService: PageTourEntryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.pageTourEntryPopupService
                .open(PageTourEntryDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
