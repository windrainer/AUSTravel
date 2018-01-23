import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Banner } from './banner.model';
import { BannerPopupService } from './banner-popup.service';
import { BannerService } from './banner.service';

@Component({
    selector: 'jhi-banner-delete-dialog',
    templateUrl: './banner-delete-dialog.component.html'
})
export class BannerDeleteDialogComponent {

    banner: Banner;

    constructor(
        private bannerService: BannerService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.bannerService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'bannerListModification',
                content: 'Deleted an banner'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-banner-delete-popup',
    template: ''
})
export class BannerDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private bannerPopupService: BannerPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.bannerPopupService
                .open(BannerDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
