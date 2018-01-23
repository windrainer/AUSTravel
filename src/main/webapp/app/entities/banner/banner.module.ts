import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AusTravelSharedModule } from '../../shared';
import {
    BannerService,
    BannerPopupService,
    BannerComponent,
    BannerDetailComponent,
    BannerDialogComponent,
    BannerPopupComponent,
    BannerDeletePopupComponent,
    BannerDeleteDialogComponent,
    bannerRoute,
    bannerPopupRoute,
} from './';

const ENTITY_STATES = [
    ...bannerRoute,
    ...bannerPopupRoute,
];

@NgModule({
    imports: [
        AusTravelSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        BannerComponent,
        BannerDetailComponent,
        BannerDialogComponent,
        BannerDeleteDialogComponent,
        BannerPopupComponent,
        BannerDeletePopupComponent,
    ],
    entryComponents: [
        BannerComponent,
        BannerDialogComponent,
        BannerPopupComponent,
        BannerDeleteDialogComponent,
        BannerDeletePopupComponent,
    ],
    providers: [
        BannerService,
        BannerPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AusTravelBannerModule {}
