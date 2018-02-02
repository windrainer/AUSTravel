import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AusTravelSharedModule } from '../../shared';
import {
    CNPageTourEntryService,
    CNPageTourEntryPopupService,
    CNPageTourEntryComponent,
    CNPageTourEntryDetailComponent,
    CNPageTourEntryDialogComponent,
    CNPageTourEntryPopupComponent,
    CNPageTourEntryDeletePopupComponent,
    CNPageTourEntryDeleteDialogComponent,
    cNPageTourEntryRoute,
    cNPageTourEntryPopupRoute,
    CNPageTourEntryResolvePagingParams,
} from './';

import { FileUploadModule } from 'ng2-file-upload';

const ENTITY_STATES = [
    ...cNPageTourEntryRoute,
    ...cNPageTourEntryPopupRoute,
];

@NgModule({
    imports: [
        AusTravelSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true }),
        FileUploadModule
    ],
    declarations: [
        CNPageTourEntryComponent,
        CNPageTourEntryDetailComponent,
        CNPageTourEntryDialogComponent,
        CNPageTourEntryDeleteDialogComponent,
        CNPageTourEntryPopupComponent,
        CNPageTourEntryDeletePopupComponent,
    ],
    entryComponents: [
        CNPageTourEntryComponent,
        CNPageTourEntryDialogComponent,
        CNPageTourEntryPopupComponent,
        CNPageTourEntryDeleteDialogComponent,
        CNPageTourEntryDeletePopupComponent,
    ],
    providers: [
        CNPageTourEntryService,
        CNPageTourEntryPopupService,
        CNPageTourEntryResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AusTravelCNPageTourEntryModule {}
