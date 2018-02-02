import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { CNTour } from './cn-tour.model';
import { CNTourPopupService } from './cn-tour-popup.service';
import { CNTourService } from './cn-tour.service';
import { FileUploader } from 'ng2-file-upload';
import { SERVER_API_URL } from '../../app.constants';

@Component({
    selector: 'jhi-cn-tour-dialog',
    templateUrl: './cn-tour-dialog.component.html'
})
export class CNTourDialogComponent implements OnInit {

    cNTour: CNTour;
    isSaving: boolean;
    createTimeDp: any;
    updateTimeDp: any;

    private resourceUrl: string = SERVER_API_URL + 'api/files/upload';

    public uploader: FileUploader = new FileUploader(
        {   url: this.resourceUrl,
            formatDataFunctionIsAsync: false});

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private cNTourService: CNTourService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        if ( this.cNTour ) {
            this.appendImg('pg_entry_image', this.cNTour.imgUrl1);
        }
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.cNTour.id !== undefined) {
            this.subscribeToSaveResponse(
                this.cNTourService.update(this.cNTour));
        } else {
            this.subscribeToSaveResponse(
                this.cNTourService.create(this.cNTour));
        }
    }

    private subscribeToSaveResponse(result: Observable<CNTour>) {
        result.subscribe((res: CNTour) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: CNTour) {
        this.eventManager.broadcast({ name: 'cNTourListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    keyupHandler(content: string) {
        this.cNTour.content = content;
    }

    uploadFile() {
        this.uploader.queue[0].onSuccess = (response, status, headers) => {
            if (status === 200) {
                // display the uploaded image
                this.appendImg('pg_entry_image', response);

                // update pageTourEntry entity
                this.cNTour.imgUrl1 = response;

                // clear queue
                this.uploader.clearQueue();

            } else {
                // if uploading has errors.
                this.onError('Failed to upload, status:' + status);
            }
        };
        this.uploader.queue[0].upload();
    }

    appendImg(id: string, src: string) {
        // remove old img section first
        const oldImg = document.getElementsByTagName('img')[0];
        if ( oldImg ) {
            oldImg.remove();
        }
        // create new img section
        const elDiv: HTMLElement = document.getElementById(id);
        const elImage: HTMLElement = document.createElement('img');
        elImage.setAttribute('src', src);
        elImage.setAttribute('width', '220');
        elImage.setAttribute('height', '175');
        elDiv.appendChild(elImage);
    }
}

@Component({
    selector: 'jhi-cn-tour-popup',
    template: ''
})
export class CNTourPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private cNTourPopupService: CNTourPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.cNTourPopupService
                    .open(CNTourDialogComponent as Component, params['id']);
            } else {
                this.cNTourPopupService
                    .open(CNTourDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
