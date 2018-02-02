import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AusTravelSharedModule } from '../../shared';
import {
    CNTourService,
    CNTourPopupService,
    CNTourComponent,
    CNTourDetailComponent,
    CNTourDialogComponent,
    CNTourPopupComponent,
    CNTourDeletePopupComponent,
    CNTourDeleteDialogComponent,
    cNTourRoute,
    cNTourPopupRoute,
    CNTourResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...cNTourRoute,
    ...cNTourPopupRoute,
];

import { TinymceModule} from '../../travel/tinymce/tinymce.module';
import { FileUploadModule} from 'ng2-file-upload';

@NgModule({
    imports: [
        AusTravelSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true }),
        TinymceModule,
        FileUploadModule
    ],
    declarations: [
        CNTourComponent,
        CNTourDetailComponent,
        CNTourDialogComponent,
        CNTourDeleteDialogComponent,
        CNTourPopupComponent,
        CNTourDeletePopupComponent,
    ],
    entryComponents: [
        CNTourComponent,
        CNTourDialogComponent,
        CNTourPopupComponent,
        CNTourDeleteDialogComponent,
        CNTourDeletePopupComponent,
    ],
    providers: [
        CNTourService,
        CNTourPopupService,
        CNTourResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AusTravelCNTourModule {}
