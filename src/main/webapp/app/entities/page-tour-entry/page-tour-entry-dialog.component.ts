import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PageTourEntry } from './page-tour-entry.model';
import { PageTourEntryPopupService } from './page-tour-entry-popup.service';
import { PageTourEntryService } from './page-tour-entry.service';
import { Tour, TourService } from '../tour';
import { ResponseWrapper } from '../../shared';
import { FileUploader } from 'ng2-file-upload';
import { SERVER_API_URL } from '../../app.constants';

@Component({
    selector: 'jhi-page-tour-entry-dialog',
    templateUrl: './page-tour-entry-dialog.component.html'
})
export class PageTourEntryDialogComponent implements OnInit {

    pageTourEntry: PageTourEntry;
    isSaving: boolean;

    tours: Tour[];
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
        private pageTourEntryService: PageTourEntryService,
        private tourService: TourService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;

        this.tourService
            .query({filter: 'pagetourentry-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.pageTourEntry.tour || !this.pageTourEntry.tour.id) {
                    this.tours = res.json;
                } else {
                    this.tourService
                        .find(this.pageTourEntry.tour.id)
                        .subscribe((subRes: Tour) => {
                            this.tours = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));

        if ( this.pageTourEntry ) {
            this.imgUrl1 = this.pageTourEntry.imgUrl1;
            this.appendImg('pg_entry_image', this.imgUrl1);
        }
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.pageTourEntry.id !== undefined) {
            this.subscribeToSaveResponse(
                this.pageTourEntryService.update(this.pageTourEntry));
        } else {
            this.subscribeToSaveResponse(
                this.pageTourEntryService.create(this.pageTourEntry));
        }
    }

    private subscribeToSaveResponse(result: Observable<PageTourEntry>) {
        result.subscribe((res: PageTourEntry) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: PageTourEntry) {
        this.eventManager.broadcast({ name: 'pageTourEntryListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackTourById(index: number, item: Tour) {
        return item.id;
    }

    uploadFile() {
        this.uploader.queue[0].onSuccess = (response, status, headers) => {
            if (status === 200) {
                // display the uploaded image
                this.appendImg('pg_entry_image', response);

                // update pageTourEntry entity
                this.pageTourEntry.imgUrl1 = response;

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
    selector: 'jhi-page-tour-entry-popup',
    template: ''
})
export class PageTourEntryPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private pageTourEntryPopupService: PageTourEntryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.pageTourEntryPopupService
                    .open(PageTourEntryDialogComponent as Component, params['id']);
            } else {
                this.pageTourEntryPopupService
                    .open(PageTourEntryDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
