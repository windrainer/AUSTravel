import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Account, LoginModalService, Principal, ResponseWrapper } from '../../shared';
import { PageTourEntry } from '../../entities/page-tour-entry/page-tour-entry.model';
import { PageTourEntryService } from '../../entities/page-tour-entry/page-tour-entry.service';
import { DomSanitizer, SafeResourceUrl, SafeUrl } from '@angular/platform-browser';

@Component({
    selector: 'jhi-tour-list',
    templateUrl: './list.component.html',
    styleUrls: [
        '../../../content/css/bootstrap.min.css',
        '../../../content/css/style.css'
    ]

})
export class TourListComponent implements OnInit {
    account: Account;
    modalRef: NgbModalRef;
    homeCubes: PageTourEntry[];

    constructor(
        private principal: Principal,
        private loginModalService: LoginModalService,
        private eventManager: JhiEventManager,
        private jhiAlertService: JhiAlertService,
        private _sanitizer: DomSanitizer,
        private cubeService: PageTourEntryService
    ) {
    }

    ngOnInit() {
        this.loadAllCubes();
        this.principal.identity().then((account) => {
            this.account = account;
        });
        this.registerAuthenticationSuccess();
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

    getBackgroundImage(homeCube: PageTourEntry) {
        const background: string = 'background-image: url(\'' + homeCube.imgUrl1 + '\')';
        return this._sanitizer.bypassSecurityTrustStyle(background);
    }

    loadAllCubes() {
        this.cubeService.query().subscribe(
            (res: ResponseWrapper) => {this.homeCubes = res.json; },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
