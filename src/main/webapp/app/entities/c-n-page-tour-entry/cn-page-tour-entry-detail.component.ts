import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { CNPageTourEntry } from './cn-page-tour-entry.model';
import { CNPageTourEntryService } from './cn-page-tour-entry.service';

@Component({
    selector: 'jhi-cn-page-tour-entry-detail',
    templateUrl: './cn-page-tour-entry-detail.component.html'
})
export class CNPageTourEntryDetailComponent implements OnInit, OnDestroy {

    cNPageTourEntry: CNPageTourEntry;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private cNPageTourEntryService: CNPageTourEntryService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCNPageTourEntries();
    }

    load(id) {
        this.cNPageTourEntryService.find(id).subscribe((cNPageTourEntry) => {
            this.cNPageTourEntry = cNPageTourEntry;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCNPageTourEntries() {
        this.eventSubscriber = this.eventManager.subscribe(
            'cNPageTourEntryListModification',
            (response) => this.load(this.cNPageTourEntry.id)
        );
    }
}
