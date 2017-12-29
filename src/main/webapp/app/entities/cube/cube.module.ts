import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AusTravelSharedModule } from '../../shared';
import {
    CubeService,
    CubePopupService,
    CubeComponent,
    CubeDetailComponent,
    CubeDialogComponent,
    CubePopupComponent,
    CubeDeletePopupComponent,
    CubeDeleteDialogComponent,
    cubeRoute,
    cubePopupRoute,
    CubeResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...cubeRoute,
    ...cubePopupRoute,
];

@NgModule({
    imports: [
        AusTravelSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CubeComponent,
        CubeDetailComponent,
        CubeDialogComponent,
        CubeDeleteDialogComponent,
        CubePopupComponent,
        CubeDeletePopupComponent,
    ],
    entryComponents: [
        CubeComponent,
        CubeDialogComponent,
        CubePopupComponent,
        CubeDeleteDialogComponent,
        CubeDeletePopupComponent,
    ],
    providers: [
        CubeService,
        CubePopupService,
        CubeResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AusTravelCubeModule {}
