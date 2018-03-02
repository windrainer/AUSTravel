import { Component, OnInit} from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import {JhiEventManager, JhiLanguageService} from 'ng-jhipster';
import { Account, LoginModalService } from '../../shared';
import { DomSanitizer } from '@angular/platform-browser';
import { Tour } from '../../entities/tour/tour.model'
import {DetailService} from './detail.service';
import { ActivatedRoute } from '@angular/router';

@Component({
    selector: 'jhi-tour-detail',
    templateUrl: './detail.component.html',
    styleUrls: [
        '../../../content/css/bootstrap.min.css',
        '../../../content/css/style.css'
    ]

})
export class TourDetailComponent implements OnInit {
    account: Account;
    modalRef: NgbModalRef;
    tourDetail: Tour;

    constructor(
        private loginModalService: LoginModalService,
        private eventManager: JhiEventManager,
        private languageService: JhiLanguageService,
        private _sanitizer: DomSanitizer,
        private tourDetailService: DetailService,
        private route: ActivatedRoute
    ) {
        this.tourDetail = new Tour();
    }

    ngOnInit() {
        const id = +this.route.snapshot.paramMap.get('id');
        this.load(id);
        this.registerLanguageChangeEvent();
    }

    registerLanguageChangeEvent() {
        this.eventManager.subscribe('lang_changed', () => {
            const id = +this.route.snapshot.paramMap.get('id');
            this.load(id);
        });
    }

    load(id) {
        this.languageService.getCurrent().then((language) => {
            if ( language === 'en') {
                this.tourDetailService.find(id).subscribe((tour) => {
                    this.tourDetail = tour;
                });
            } else if (language === 'zh-cn') {
                this.tourDetailService.findCN(id).subscribe((tour) => {
                    this.tourDetail = tour;
                });
            }
        });
    }

    login() {
        this.modalRef = this.loginModalService.open();
    }

    getTourDetailContent() {
        return this._sanitizer.bypassSecurityTrustHtml(this.tourDetail.content);
    }

    getBackgroundImage(tourDetail: Tour) {
        return this._sanitizer.bypassSecurityTrustStyle(tourDetail.imgUrl1);
    }
}
