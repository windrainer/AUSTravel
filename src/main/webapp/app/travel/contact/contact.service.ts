import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';
import { Contact } from './contact.model';

@Injectable()
export class ContactService {
    private resourceUrl = SERVER_API_URL + 'api/tours/enquiry';

    constructor(private http: Http) { }

    sendEnquiryEmail(contact: Contact): Observable<Response> {
        return this.http.post(this.resourceUrl, contact);
    }

}
