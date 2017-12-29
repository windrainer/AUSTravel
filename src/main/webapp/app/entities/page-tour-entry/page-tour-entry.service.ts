import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { PageTourEntry } from './page-tour-entry.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class PageTourEntryService {

    private resourceUrl = SERVER_API_URL + 'api/page-tour-entries';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(pageTourEntry: PageTourEntry): Observable<PageTourEntry> {
        const copy = this.convert(pageTourEntry);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(pageTourEntry: PageTourEntry): Observable<PageTourEntry> {
        const copy = this.convert(pageTourEntry);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<PageTourEntry> {
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
     * Convert a returned JSON object to PageTourEntry.
     */
    private convertItemFromServer(json: any): PageTourEntry {
        const entity: PageTourEntry = Object.assign(new PageTourEntry(), json);
        entity.createTime = this.dateUtils
            .convertLocalDateFromServer(json.createTime);
        entity.updateTime = this.dateUtils
            .convertLocalDateFromServer(json.updateTime);
        return entity;
    }

    /**
     * Convert a PageTourEntry to a JSON which can be sent to the server.
     */
    private convert(pageTourEntry: PageTourEntry): PageTourEntry {
        const copy: PageTourEntry = Object.assign({}, pageTourEntry);
        copy.createTime = this.dateUtils
            .convertLocalDateToServer(pageTourEntry.createTime);
        copy.updateTime = this.dateUtils
            .convertLocalDateToServer(pageTourEntry.updateTime);
        return copy;
    }
}
