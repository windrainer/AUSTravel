import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Cube } from './cube.model';
import { CubePopupService } from './cube-popup.service';
import { CubeService } from './cube.service';

@Component({
    selector: 'jhi-cube-delete-dialog',
    templateUrl: './cube-delete-dialog.component.html'
})
export class CubeDeleteDialogComponent {

    cube: Cube;

    constructor(
        private cubeService: CubeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.cubeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'cubeListModification',
                content: 'Deleted an cube'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-cube-delete-popup',
    template: ''
})
export class CubeDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private cubePopupService: CubePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.cubePopupService
                .open(CubeDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
