import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { ContactComponent } from './contact.component';

@NgModule({
    imports: [],
    declarations: [
        ContactComponent
    ],
    entryComponents: [
    ],
    providers: [
    ],
    exports: [
        ContactComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ContactModule {}
