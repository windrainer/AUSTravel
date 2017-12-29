import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';
import { Account, LoginModalService, Principal } from '../../shared';
import { DomSanitizer, SafeResourceUrl, SafeUrl } from '@angular/platform-browser';
import { Tour } from './tour';

const tourDetailMock: Tour = new Tour();
tourDetailMock.createBy = 'Anonymouse';
tourDetailMock.id = 1;
tourDetailMock.imgUrl = '../../../content/images/3.jpg';
tourDetailMock.content = '</br><h3>This is a test content header</h3></br><p>Beginning of test ' +
    'paragraph!</p><p>Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, ' +
    'vel illum dolore eu feugiat nulla facilisis. At vero eos et accusam et justo.Lorem ipsum dolor sit ' +
    'amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna ' +
    'aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum.</p>';

@Component({
    selector: 'jhi-tour-detail',
    templateUrl: './detail.component.html',
    styleUrls: [
        'detail.css',
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
        private _sanitizer: DomSanitizer
    ) {
    }

    ngOnInit() {
        this.tourDetail = tourDetailMock;
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

    getTourDetailContent() {
        return this._sanitizer.bypassSecurityTrustHtml(this.tourDetail.content);
    }

    getBackgroundImage(tourDetail: Tour) {
        return this._sanitizer.bypassSecurityTrustStyle(tourDetail.imgUrl);
    }
}
