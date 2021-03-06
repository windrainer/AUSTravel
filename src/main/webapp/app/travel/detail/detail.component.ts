import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';
import { Account, LoginModalService, Principal } from '../../shared';
import { DomSanitizer, SafeResourceUrl, SafeUrl } from '@angular/platform-browser';
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
        private principal: Principal,
        private loginModalService: LoginModalService,
        private eventManager: JhiEventManager,
        private _sanitizer: DomSanitizer,
        private tourService: DetailService,
        private route: ActivatedRoute
    ) {
        this.tourDetail = new Tour();
    }

    ngOnInit() {
        const id = +this.route.snapshot.paramMap.get('id');
        this.load(id);
        this.principal.identity().then((account) => {
            this.account = account;
        });
        this.registerAuthenticationSuccess();
    }

    load(id) {
        this.tourService.find(id).subscribe((tour) => {
            this.tourDetail = tour;
        });
    }
    registerAuthenticationSuccess() {
        this.eventManager.subscribe('authenticationSuccess', (message) => {
            this.principal.identity().then((account) => {
                this.account = account;
            });
        });
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
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
