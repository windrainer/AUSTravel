import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CNPageTourEntry } from './cn-page-tour-entry.model';
import { CNPageTourEntryPopupService } from './cn-page-tour-entry-popup.service';
import { CNPageTourEntryService } from './cn-page-tour-entry.service';
import { CNTour, CNTourService } from '../c-n-tour';
import { ResponseWrapper } from '../../shared';
import { FileUploader } from 'ng2-file-upload';
import { SERVER_API_URL } from '../../app.constants';

@Component({
    selector: 'jhi-cn-page-tour-entry-dialog',
    templateUrl: './cn-page-tour-entry-dialog.component.html'
})
export class CNPageTourEntryDialogComponent implements OnInit {

    cNPageTourEntry: CNPageTourEntry;
    isSaving: boolean;

    cntours: CNTour[];
    createTimeDp: any;
    updateTimeDp: any;
    imgUrl1: any;

    private resourceUrl: string = SERVER_API_URL + 'api/files/upload';

    public uploader: FileUploader = new FileUploader(
        {   url: this.resourceUrl,
            formatDataFunctionIsAsync: false});

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private cNPageTourEntryService: CNPageTourEntryService,
        private cNTourService: CNTourService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.cNTourService
            .query({filter: 'cnpagetourentry-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.cNPageTourEntry.cntour || !this.cNPageTourEntry.cntour.id) {
                    this.cntours = res.json;
                } else {
                    this.cNTourService
                        .find(this.cNPageTourEntry.cntour.id)
                        .subscribe((subRes: CNTour) => {
                            this.cntours = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));

        if ( this.cNPageTourEntry ) {
            this.imgUrl1 = this.cNPageTourEntry.imgUrl1;
            this.appendImg('pg_entry_image', this.imgUrl1);
        }
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.cNPageTourEntry.id !== undefined) {
            this.subscribeToSaveResponse(
                this.cNPageTourEntryService.update(this.cNPageTourEntry));
        } else {
            this.subscribeToSaveResponse(
                this.cNPageTourEntryService.create(this.cNPageTourEntry));
        }
    }

    private subscribeToSaveResponse(result: Observable<CNPageTourEntry>) {
        result.subscribe((res: CNPageTourEntry) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: CNPageTourEntry) {
        this.eventManager.broadcast({ name: 'cNPageTourEntryListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackCNTourById(index: number, item: CNTour) {
        return item.id;
    }

    uploadFile() {
        this.uploader.queue[0].onSuccess = (response, status, headers) => {
            if (status === 200) {
                // display the uploaded image
                this.appendImg('pg_entry_image', response);

                // update pageTourEntry entity
                this.cNPageTourEntry.imgUrl1 = response;

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
    selector: 'jhi-cn-page-tour-entry-popup',
    template: ''
})
export class CNPageTourEntryPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private cNPageTourEntryPopupService: CNPageTourEntryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.cNPageTourEntryPopupService
                    .open(CNPageTourEntryDialogComponent as Component, params['id']);
            } else {
                this.cNPageTourEntryPopupService
                    .open(CNPageTourEntryDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
