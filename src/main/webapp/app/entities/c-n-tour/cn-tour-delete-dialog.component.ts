import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CNTour } from './cn-tour.model';
import { CNTourPopupService } from './cn-tour-popup.service';
import { CNTourService } from './cn-tour.service';

@Component({
    selector: 'jhi-cn-tour-delete-dialog',
    templateUrl: './cn-tour-delete-dialog.component.html'
})
export class CNTourDeleteDialogComponent {

    cNTour: CNTour;

    constructor(
        private cNTourService: CNTourService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.cNTourService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'cNTourListModification',
                content: 'Deleted an cNTour'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-cn-tour-delete-popup',
    template: ''
})
export class CNTourDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private cNTourPopupService: CNTourPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.cNTourPopupService
                .open(CNTourDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
