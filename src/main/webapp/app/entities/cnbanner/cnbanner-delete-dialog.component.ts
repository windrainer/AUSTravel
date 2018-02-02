import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Cnbanner } from './cnbanner.model';
import { CnbannerPopupService } from './cnbanner-popup.service';
import { CnbannerService } from './cnbanner.service';

@Component({
    selector: 'jhi-cnbanner-delete-dialog',
    templateUrl: './cnbanner-delete-dialog.component.html'
})
export class CnbannerDeleteDialogComponent {

    cnbanner: Cnbanner;

    constructor(
        private cnbannerService: CnbannerService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.cnbannerService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'cnbannerListModification',
                content: 'Deleted an cnbanner'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-cnbanner-delete-popup',
    template: ''
})
export class CnbannerDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private cnbannerPopupService: CnbannerPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.cnbannerPopupService
                .open(CnbannerDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
