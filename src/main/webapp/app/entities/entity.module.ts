import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { AusTravelTourModule } from './tour/tour.module';
import { AusTravelPageTourEntryModule } from './page-tour-entry/page-tour-entry.module';

import { AusTravelBannerModule } from './banner/banner.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        AusTravelTourModule,
        AusTravelPageTourEntryModule,
        AusTravelBannerModule
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AusTravelEntityModule {}
