import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { CNTour } from './cn-tour.model';
import { CNTourService } from './cn-tour.service';

@Component({
    selector: 'jhi-cn-tour-detail',
    templateUrl: './cn-tour-detail.component.html'
})
export class CNTourDetailComponent implements OnInit, OnDestroy {

    cNTour: CNTour;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private cNTourService: CNTourService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCNTours();
    }

    load(id) {
        this.cNTourService.find(id).subscribe((cNTour) => {
            this.cNTour = cNTour;
        });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCNTours() {
        this.eventSubscriber = this.eventManager.subscribe(
            'cNTourListModification',
            (response) => this.load(this.cNTour.id)
        );
    }
}
