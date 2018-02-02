import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AusTravelSharedModule } from '../../shared';

import { TOUR_DETAIL_ROUTES, TourDetailComponent } from './';
import { ContactModule } from '../contact/contact.module';
import { DetailService} from './detail.service';
import {BannerModule} from '../banner/banner.module';

@NgModule({
    imports: [
        AusTravelSharedModule,
        RouterModule.forRoot( TOUR_DETAIL_ROUTES , { useHash: true, enableTracing: true } ),
        ContactModule,
        BannerModule
    ],
    declarations: [
        TourDetailComponent
    ],
    entryComponents: [
    ],
    providers: [
        DetailService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TourDetailModule {}
