import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Cube } from './cube.model';
import { CubePopupService } from './cube-popup.service';
import { CubeService } from './cube.service';

@Component({
    selector: 'jhi-cube-dialog',
    templateUrl: './cube-dialog.component.html'
})
export class CubeDialogComponent implements OnInit {

    cube: Cube;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private cubeService: CubeService,
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
        if (this.cube.id !== undefined) {
            this.subscribeToSaveResponse(
                this.cubeService.update(this.cube));
        } else {
            this.subscribeToSaveResponse(
                this.cubeService.create(this.cube));
        }
    }

    private subscribeToSaveResponse(result: Observable<Cube>) {
        result.subscribe((res: Cube) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Cube) {
        this.eventManager.broadcast({ name: 'cubeListModification', content: 'OK'});
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
    selector: 'jhi-cube-popup',
    template: ''
})
export class CubePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private cubePopupService: CubePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.cubePopupService
                    .open(CubeDialogComponent as Component, params['id']);
            } else {
                this.cubePopupService
                    .open(CubeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
