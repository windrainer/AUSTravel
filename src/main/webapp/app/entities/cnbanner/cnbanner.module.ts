import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AusTravelSharedModule } from '../../shared';
import {
    CnbannerService,
    CnbannerPopupService,
    CnbannerComponent,
    CnbannerDetailComponent,
    CnbannerDialogComponent,
    CnbannerPopupComponent,
    CnbannerDeletePopupComponent,
    CnbannerDeleteDialogComponent,
    cnbannerRoute,
    cnbannerPopupRoute,
    CnbannerResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...cnbannerRoute,
    ...cnbannerPopupRoute,
];

@NgModule({
    imports: [
        AusTravelSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CnbannerComponent,
        CnbannerDetailComponent,
        CnbannerDialogComponent,
        CnbannerDeleteDialogComponent,
        CnbannerPopupComponent,
        CnbannerDeletePopupComponent,
    ],
    entryComponents: [
        CnbannerComponent,
        CnbannerDialogComponent,
        CnbannerPopupComponent,
        CnbannerDeleteDialogComponent,
        CnbannerDeletePopupComponent,
    ],
    providers: [
        CnbannerService,
        CnbannerPopupService,
        CnbannerResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AusTravelCnbannerModule {}
