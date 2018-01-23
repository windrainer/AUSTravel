import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { Banner } from './banner.model';
import { BannerService } from './banner.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-banner',
    templateUrl: './banner.component.html'
})
export class BannerComponent implements OnInit, OnDestroy {
banners: Banner[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private bannerService: BannerService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.bannerService.query().subscribe(
            (res: ResponseWrapper) => {
                this.banners = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInBanners();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Banner) {
        return item.id;
    }
    registerChangeInBanners() {
        this.eventSubscriber = this.eventManager.subscribe('bannerListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
