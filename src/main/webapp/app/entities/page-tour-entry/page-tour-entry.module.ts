import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AusTravelSharedModule } from '../../shared';
import {
    PageTourEntryService,
    PageTourEntryPopupService,
    PageTourEntryComponent,
    PageTourEntryDetailComponent,
    PageTourEntryDialogComponent,
    PageTourEntryPopupComponent,
    PageTourEntryDeletePopupComponent,
    PageTourEntryDeleteDialogComponent,
    pageTourEntryRoute,
    pageTourEntryPopupRoute,
    PageTourEntryResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...pageTourEntryRoute,
    ...pageTourEntryPopupRoute,
];

@NgModule({
    imports: [
        AusTravelSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PageTourEntryComponent,
        PageTourEntryDetailComponent,
        PageTourEntryDialogComponent,
        PageTourEntryDeleteDialogComponent,
        PageTourEntryPopupComponent,
        PageTourEntryDeletePopupComponent,
    ],
    entryComponents: [
        PageTourEntryComponent,
        PageTourEntryDialogComponent,
        PageTourEntryPopupComponent,
        PageTourEntryDeleteDialogComponent,
        PageTourEntryDeletePopupComponent,
    ],
    providers: [
        PageTourEntryService,
        PageTourEntryPopupService,
        PageTourEntryResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AusTravelPageTourEntryModule {}
