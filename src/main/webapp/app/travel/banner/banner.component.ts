import { Component, OnInit } from '@angular/core';
import { JhiLanguageService } from 'ng-jhipster';
import { Banner } from '../../entities/banner/banner.model';
import {BannerService} from '../../entities/banner/banner.service';
import {JhiAlertService} from 'ng-jhipster';
import { ResponseWrapper } from '../../shared';
import { DomSanitizer, SafeResourceUrl, SafeUrl } from '@angular/platform-browser';

@Component({
    selector: 'jhi-page-banner',
    templateUrl: './banner.component.html',
    styleUrls: [
        '../../../content/css/bootstrap.min.css',
        '../../../content/css/style.css'
    ]
})
export class BannerComponent implements OnInit {

     banner: Banner;
     error: string;

    constructor(
        private languageService: JhiLanguageService,
        private bannerService: BannerService,
        private jhiAlertService: JhiAlertService,
        private _sanitizer: DomSanitizer
        ) {}

    ngOnInit() {
        this.banner = new Banner( 0, '', '', '', '', '', '', '' );
        this.bannerService.findActivated().subscribe(
            (res: Banner) => {
                this.banner = res;
            },
            (res: ResponseWrapper) => this.onError('Error finding a banner'))
    }

    getText1() {
        return this._sanitizer.bypassSecurityTrustHtml(this.banner.text1);
    }

    getText2() {
        return this._sanitizer.bypassSecurityTrustHtml(this.banner.text2);
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }

}
