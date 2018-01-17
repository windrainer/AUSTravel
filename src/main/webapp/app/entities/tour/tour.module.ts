import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AusTravelSharedModule } from '../../shared';
import {
    TourService,
    TourPopupService,
    TourComponent,
    TourDetailComponent,
    TourDialogComponent,
    TourPopupComponent,
    TourDeletePopupComponent,
    TourDeleteDialogComponent,
    tourRoute,
    tourPopupRoute,
    TourResolvePagingParams,
} from './';
import { TinymceModule } from '../../travel/tinymce/tinymce.module';
import { FileUploadModule } from 'ng2-file-upload';

const ENTITY_STATES = [
    ...tourRoute,
    ...tourPopupRoute,
];

@NgModule({
    imports: [
        AusTravelSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true }),
        TinymceModule,
        FileUploadModule
    ],
    declarations: [
        TourComponent,
        TourDetailComponent,
        TourDialogComponent,
        TourDeleteDialogComponent,
        TourPopupComponent,
        TourDeletePopupComponent,
    ],
    entryComponents: [
        TourComponent,
        TourDialogComponent,
        TourPopupComponent,
        TourDeleteDialogComponent,
        TourDeletePopupComponent,
    ],
    providers: [
        TourService,
        TourPopupService,
        TourResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AusTravelTourModule {}
