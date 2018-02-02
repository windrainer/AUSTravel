import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { Cnbanner } from './cnbanner.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class CnbannerService {

    private resourceUrl = SERVER_API_URL + 'api/cnbanners';

    constructor(private http: Http) { }

    create(cnbanner: Cnbanner): Observable<Cnbanner> {
        const copy = this.convert(cnbanner);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(cnbanner: Cnbanner): Observable<Cnbanner> {
        const copy = this.convert(cnbanner);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Cnbanner> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    findActivated(): Observable<Cnbanner> {
        return this.http.get(`${this.resourceUrl}/activated`).map((res: Response) => {
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
     * Convert a returned JSON object to Cnbanner.
     */
    private convertItemFromServer(json: any): Cnbanner {
        const entity: Cnbanner = Object.assign(new Cnbanner(), json);
        return entity;
    }

    /**
     * Convert a Cnbanner to a JSON which can be sent to the server.
     */
    private convert(cnbanner: Cnbanner): Cnbanner {
        const copy: Cnbanner = Object.assign({}, cnbanner);
        return copy;
    }
}
