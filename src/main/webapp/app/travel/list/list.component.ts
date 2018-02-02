import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiLanguageService, JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Account, LoginModalService, Principal, ResponseWrapper } from '../../shared';
import { PageTourEntry } from '../../entities/page-tour-entry/page-tour-entry.model';
import { CNPageTourEntryService } from '../../entities/c-n-page-tour-entry/cn-page-tour-entry.service';
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
    lang_key: string;

    constructor(
        private principal: Principal,
        private loginModalService: LoginModalService,
        private eventManager: JhiEventManager,
        private jhiAlertService: JhiAlertService,
        private jhiLanguageService: JhiLanguageService,
        private _sanitizer: DomSanitizer,
        private cubeService: PageTourEntryService,
        private cubeServiceCN: CNPageTourEntryService
    ) {
    }

    ngOnInit() {
        this.loadAllCubes();
        this.registerAuthenticationSuccess();
        this.registerLanguageChange();
    }

    registerAuthenticationSuccess() {
        this.eventManager.subscribe('authenticationSuccess', (message) => {
            this.principal.identity().then((account) => {
                this.account = account;
            });
        });
    }

    registerLanguageChange() {
        this.eventManager.subscribe( 'lang_changed', (response) => this.loadAllCubes());
    }

    login() {
        this.modalRef = this.loginModalService.open();
    }

    getBackgroundImage(homeCube: PageTourEntry) {
        const background: string = 'background-image: url(\'' + homeCube.imgUrl1 + '\')';
        return this._sanitizer.bypassSecurityTrustStyle(background);
    }

    loadAllCubes() {
        this.jhiLanguageService.getCurrent().then(
            (languages) => {
                this.lang_key = languages;
                // console.log('lang_key is: ' + this.lang_key);
                if (this.lang_key === 'en') {
                    this.cubeService.query().subscribe(
                        (res: ResponseWrapper) => {
                            this.homeCubes = res.json;
                        },
                        (res: ResponseWrapper) => this.onError(res.json)
                    );
                } else if (this.lang_key === 'zh-cn') {
                    this.cubeServiceCN.query().subscribe(
                        (res: ResponseWrapper) => {
                            this.homeCubes = res.json;
                            // console.log(JSON.stringify(this.homeCubes));
                        },
                        (res: ResponseWrapper) => this.onError(res.json)
                    );
                }
            }
        )

    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
