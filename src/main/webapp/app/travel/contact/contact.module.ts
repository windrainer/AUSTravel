import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { ContactComponent } from './contact.component';
import {ContactService} from './contact.service';
import {FormsModule} from '@angular/forms';
import { CommonModule } from '@angular/common';

@NgModule({
    imports: [
        FormsModule,
        CommonModule
    ],
    declarations: [
        ContactComponent
    ],
    entryComponents: [
    ],
    providers: [
        ContactService
    ],
    exports: [
        ContactComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ContactModule {}
