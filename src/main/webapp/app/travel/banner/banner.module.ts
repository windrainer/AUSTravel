import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { CommonModule } from '@angular/common';
import { BannerComponent } from './banner.component';
import {BannerService} from '../../entities/banner/banner.service';

@NgModule({
    imports: [
        CommonModule
    ],
    declarations: [
        BannerComponent
    ],
    entryComponents: [
    ],
    providers: [
        BannerService
    ],
    exports: [
        BannerComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BannerModule {}
