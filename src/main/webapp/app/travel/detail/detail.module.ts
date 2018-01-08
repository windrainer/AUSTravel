import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AusTravelSharedModule } from '../../shared';

import { TOUR_DETAIL_ROUTE, TourDetailComponent } from './';
import { ContactModule } from '../contact/contact.module';
import { DetailService} from './detail.service';

@NgModule({
    imports: [
        AusTravelSharedModule,
        RouterModule.forRoot([ TOUR_DETAIL_ROUTE ], { useHash: true, enableTracing: true } ),
        ContactModule
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
