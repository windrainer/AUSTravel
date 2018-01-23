import './vendor.ts';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { Ng2Webstorage } from 'ng2-webstorage';

import { AusTravelSharedModule, UserRouteAccessService } from './shared';
import { AusTravelAdminModule } from './admin/admin.module';
import { AusTravelAccountModule } from './account/account.module';
import { AusTravelEntityModule } from './entities/entity.module';

import { customHttpProvider } from './blocks/interceptor/http.provider';
import { PaginationConfig } from './blocks/config/uib-pagination.config';

import { TourListModule } from './travel/list/list.module';
import { TourDetailModule } from './travel/detail/detail.module';
import { ContactModule} from './travel/contact/contact.module';
import { BannerModule} from './travel/banner/banner.module';

// jhipster-needle-angular-add-module-import JHipster will add new module here

import {
    JhiMainComponent,
    LayoutRoutingModule,
    NavbarComponent,
    FooterComponent,
    ProfileService,
    PageRibbonComponent,
    ActiveMenuDirective,
    ErrorComponent
} from './layouts';

@NgModule({
    imports: [
        BrowserModule,
        LayoutRoutingModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-'}),
        AusTravelSharedModule,
        TourListModule,
        TourDetailModule,
        AusTravelAdminModule,
        AusTravelAccountModule,
        AusTravelEntityModule,
        ContactModule,
        BannerModule

        // jhipster-needle-angular-add-module JHipster will add new module here
    ],
    declarations: [
        JhiMainComponent,
        NavbarComponent,
        ErrorComponent,
        PageRibbonComponent,
        ActiveMenuDirective,
        FooterComponent
    ],
    providers: [
        ProfileService,
        customHttpProvider(),
        PaginationConfig,
        UserRouteAccessService
    ],
    bootstrap: [ JhiMainComponent ]
})
export class AusTravelAppModule {}
