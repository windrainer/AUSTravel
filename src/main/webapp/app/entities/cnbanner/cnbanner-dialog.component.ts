import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Cnbanner } from './cnbanner.model';
import { CnbannerPopupService } from './cnbanner-popup.service';
import { CnbannerService } from './cnbanner.service';

@Component({
    selector: 'jhi-cnbanner-dialog',
    templateUrl: './cnbanner-dialog.component.html'
})
export class CnbannerDialogComponent implements OnInit {

    cnbanner: Cnbanner;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private cnbannerService: CnbannerService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.cnbanner.id !== undefined) {
            this.subscribeToSaveResponse(
                this.cnbannerService.update(this.cnbanner));
        } else {
            this.subscribeToSaveResponse(
                this.cnbannerService.create(this.cnbanner));
        }
    }

    private subscribeToSaveResponse(result: Observable<Cnbanner>) {
        result.subscribe((res: Cnbanner) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Cnbanner) {
        this.eventManager.broadcast({ name: 'cnbannerListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-cnbanner-popup',
    template: ''
})
export class CnbannerPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private cnbannerPopupService: CnbannerPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.cnbannerPopupService
                    .open(CnbannerDialogComponent as Component, params['id']);
            } else {
                this.cnbannerPopupService
                    .open(CnbannerDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
