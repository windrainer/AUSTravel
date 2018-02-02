import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { CNPageTourEntry } from './cn-page-tour-entry.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class CNPageTourEntryService {

    private resourceUrl = SERVER_API_URL + 'api/c-n-page-tour-entries';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(cNPageTourEntry: CNPageTourEntry): Observable<CNPageTourEntry> {
        const copy = this.convert(cNPageTourEntry);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(cNPageTourEntry: CNPageTourEntry): Observable<CNPageTourEntry> {
        const copy = this.convert(cNPageTourEntry);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<CNPageTourEntry> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to CNPageTourEntry.
     */
    private convertItemFromServer(json: any): CNPageTourEntry {
        const entity: CNPageTourEntry = Object.assign(new CNPageTourEntry(), json);
        entity.createTime = this.dateUtils
            .convertLocalDateFromServer(json.createTime);
        entity.updateTime = this.dateUtils
            .convertLocalDateFromServer(json.updateTime);
        return entity;
    }

    /**
     * Convert a CNPageTourEntry to a JSON which can be sent to the server.
     */
    private convert(cNPageTourEntry: CNPageTourEntry): CNPageTourEntry {
        const copy: CNPageTourEntry = Object.assign({}, cNPageTourEntry);
        copy.createTime = this.dateUtils
            .convertLocalDateToServer(cNPageTourEntry.createTime);
        copy.updateTime = this.dateUtils
            .convertLocalDateToServer(cNPageTourEntry.updateTime);
        return copy;
    }
}
