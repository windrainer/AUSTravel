import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Banner } from './banner.model';
import { BannerPopupService } from './banner-popup.service';
import { BannerService } from './banner.service';

@Component({
    selector: 'jhi-banner-dialog',
    templateUrl: './banner-dialog.component.html'
})
export class BannerDialogComponent implements OnInit {

    banner: Banner;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private bannerService: BannerService,
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
        if (this.banner.id !== undefined) {
            this.subscribeToSaveResponse(
                this.bannerService.update(this.banner));
        } else {
            this.subscribeToSaveResponse(
                this.bannerService.create(this.banner));
        }
    }

    private subscribeToSaveResponse(result: Observable<Banner>) {
        result.subscribe((res: Banner) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Banner) {
        this.eventManager.broadcast({ name: 'bannerListModification', content: 'OK'});
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
    selector: 'jhi-banner-popup',
    template: ''
})
export class BannerPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private bannerPopupService: BannerPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.bannerPopupService
                    .open(BannerDialogComponent as Component, params['id']);
            } else {
                this.bannerPopupService
                    .open(BannerDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
