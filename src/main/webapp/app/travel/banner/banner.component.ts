import { Component, OnInit } from '@angular/core';
import {JhiEventManager, JhiLanguageService} from 'ng-jhipster';
import { Banner } from '../../entities/banner/banner.model';
import {BannerService} from '../../entities/banner/banner.service';
import {JhiAlertService} from 'ng-jhipster';
import { ResponseWrapper } from '../../shared';
import { DomSanitizer, SafeResourceUrl, SafeUrl } from '@angular/platform-browser';
import {CnbannerService} from '../../entities/cnbanner/cnbanner.service';
import { JhiLanguageHelper } from '../../shared';

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
     lang_key: string;

    constructor(
        private languageService: JhiLanguageService,
        private jhiEventManager: JhiEventManager,
        private bannerService: BannerService,
        private cnbannerService: CnbannerService,
        private jhiAlertService: JhiAlertService,
        private _sanitizer: DomSanitizer,
        private jhiLanguageHelper: JhiLanguageHelper
        ) {}

    ngOnInit() {
        this.banner = new Banner( 0, '', '', '', '', '', '', '' );
        this.showActivatedBanner();
        this.registerLanguageChangeEvent();
    }

    registerLanguageChangeEvent() {
        this.jhiEventManager.subscribe('lang_changed', () => {
            this.showActivatedBanner();
        });
    }

    showActivatedBanner() {
        this.languageService.getCurrent().then( (language) => {
                // console.log('banner lang_key changed to ' + language);
                if ( language === 'en') {
                    this.bannerService.findActivated().subscribe(
                        (res: Banner) => {
                            this.banner = res;
                        },
                        (res: ResponseWrapper) => this.onError('Error finding a banner')
                    )
                } else if ( language === 'zh-cn') {
                    this.cnbannerService.findActivated().subscribe(
                        (res: Banner) => {
                            this.banner = res;
                        },
                        (res: ResponseWrapper) => this.onError('Error finding a banner')
                    )
                }
            }
        )
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
