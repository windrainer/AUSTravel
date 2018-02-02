import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Cnbanner } from './cnbanner.model';
import { CnbannerService } from './cnbanner.service';

@Component({
    selector: 'jhi-cnbanner-detail',
    templateUrl: './cnbanner-detail.component.html'
})
export class CnbannerDetailComponent implements OnInit, OnDestroy {

    cnbanner: Cnbanner;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private cnbannerService: CnbannerService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCnbanners();
    }

    load(id) {
        this.cnbannerService.find(id).subscribe((cnbanner) => {
            this.cnbanner = cnbanner;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCnbanners() {
        this.eventSubscriber = this.eventManager.subscribe(
            'cnbannerListModification',
            (response) => this.load(this.cnbanner.id)
        );
    }
}
