import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { TinymceComponent } from './tinymce.component';

@NgModule({
    imports: [],
    declarations: [
        TinymceComponent
    ],
    entryComponents: [
    ],
    providers: [
    ],
    exports: [
        TinymceComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TinymceModule {}
