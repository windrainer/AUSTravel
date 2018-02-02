import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CNPageTourEntry } from './cn-page-tour-entry.model';
import { CNPageTourEntryPopupService } from './cn-page-tour-entry-popup.service';
import { CNPageTourEntryService } from './cn-page-tour-entry.service';

@Component({
    selector: 'jhi-cn-page-tour-entry-delete-dialog',
    templateUrl: './cn-page-tour-entry-delete-dialog.component.html'
})
export class CNPageTourEntryDeleteDialogComponent {

    cNPageTourEntry: CNPageTourEntry;

    constructor(
        private cNPageTourEntryService: CNPageTourEntryService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.cNPageTourEntryService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'cNPageTourEntryListModification',
                content: 'Deleted an cNPageTourEntry'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-cn-page-tour-entry-delete-popup',
    template: ''
})
export class CNPageTourEntryDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private cNPageTourEntryPopupService: CNPageTourEntryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.cNPageTourEntryPopupService
                .open(CNPageTourEntryDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
