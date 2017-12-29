import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { PageTourEntry } from './page-tour-entry.model';
import { PageTourEntryService } from './page-tour-entry.service';

@Component({
    selector: 'jhi-page-tour-entry-detail',
    templateUrl: './page-tour-entry-detail.component.html'
})
export class PageTourEntryDetailComponent implements OnInit, OnDestroy {

    pageTourEntry: PageTourEntry;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private pageTourEntryService: PageTourEntryService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPageTourEntries();
    }

    load(id) {
        this.pageTourEntryService.find(id).subscribe((pageTourEntry) => {
            this.pageTourEntry = pageTourEntry;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPageTourEntries() {
        this.eventSubscriber = this.eventManager.subscribe(
            'pageTourEntryListModification',
            (response) => this.load(this.pageTourEntry.id)
        );
    }
}
