import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Banner } from './banner.model';
import { BannerService } from './banner.service';

@Component({
    selector: 'jhi-banner-detail',
    templateUrl: './banner-detail.component.html'
})
export class BannerDetailComponent implements OnInit, OnDestroy {

    banner: Banner;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private bannerService: BannerService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInBanners();
    }

    load(id) {
        this.bannerService.find(id).subscribe((banner) => {
            this.banner = banner;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInBanners() {
        this.eventSubscriber = this.eventManager.subscribe(
            'bannerListModification',
            (response) => this.load(this.banner.id)
        );
    }
}
