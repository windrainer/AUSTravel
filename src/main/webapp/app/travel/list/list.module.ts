import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AusTravelSharedModule } from '../../shared';

import { TOUR_LIST_ROUTE, TourListComponent } from './';
import { ContactModule } from '../contact/contact.module';
import {BannerModule} from '../banner/banner.module';
import {CommonModule} from '@angular/common';

@NgModule({
    imports: [
        AusTravelSharedModule,
        RouterModule.forRoot([ TOUR_LIST_ROUTE ], { useHash: true }),
        ContactModule,
        BannerModule,
        CommonModule
    ],
    declarations: [
        TourListComponent,
    ],
    entryComponents: [
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TourListModule {}
