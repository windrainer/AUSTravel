import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { Tour } from './tour.model';
import { TourPopupService } from './tour-popup.service';
import { TourService } from './tour.service';
import { FileUploader } from 'ng2-file-upload';
import { SERVER_API_URL } from '../../app.constants';

@Component({
    selector: 'jhi-tour-dialog',
    templateUrl: './tour-dialog.component.html'
})
export class TourDialogComponent implements OnInit {

    tour: Tour;
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
        private tourService: TourService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        if ( this.tour ) {
            this.appendImg('pg_entry_image', this.tour.imgUrl1);
        }
    }

    keyupHandler(content: string) {
        this.tour.content = content;
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
        if (this.tour.id !== undefined) {
            this.subscribeToSaveResponse(
                this.tourService.update(this.tour));
        } else {
            this.subscribeToSaveResponse(
                this.tourService.create(this.tour));
        }
    }

    private subscribeToSaveResponse(result: Observable<Tour>) {
        result.subscribe((res: Tour) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Tour) {
        this.eventManager.broadcast({ name: 'tourListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    uploadFile() {
        this.uploader.queue[0].onSuccess = (response, status, headers) => {
            if (status === 200) {
                // display the uploaded image
                this.appendImg('pg_entry_image', response);

                // update pageTourEntry entity
                this.tour.imgUrl1 = response;

                // clear queue
                this.uploader.clearQueue();

            } else {
                // if uploading has errors.
                alert('Failed to upload, status:' + status);
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
    selector: 'jhi-tour-popup',
    template: ''
})
export class TourPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private tourPopupService: TourPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.tourPopupService
                    .open(TourDialogComponent as Component, params['id']);
            } else {
                this.tourPopupService
                    .open(TourDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
