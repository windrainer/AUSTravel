import { Component, OnInit } from '@angular/core';
import { Contact } from './contact.model';
import { ContactService } from './contact.service';
import { JhiLanguageService} from 'ng-jhipster';

@Component({
    selector: 'jhi-page-contact',
    templateUrl: './contact.component.html',
    styleUrls: [
        '../../../content/css/bootstrap.min.css',
        '../../../content/css/style.css'
    ]
})
export class ContactComponent implements OnInit {

    contact: Contact;
    error: string;

    constructor(
        private contactService: ContactService) {
    }

    ngOnInit() {
        this.contact = new Contact( '', '', '' );
    }

    sendEnquiryEmail() {
        this.contactService.sendEnquiryEmail( this.contact ).subscribe( (res) => {
            console.log(res.status);
        });
    }

    // TODO: Remove this when we're done
    get diagnostic() { return JSON.stringify(this.contact); }
}
