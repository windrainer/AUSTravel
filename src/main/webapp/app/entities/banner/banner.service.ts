import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { Banner } from './banner.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class BannerService {

    private resourceUrl = SERVER_API_URL + 'api/banners';

    constructor(private http: Http) { }

    create(banner: Banner): Observable<Banner> {
        const copy = this.convert(banner);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(banner: Banner): Observable<Banner> {
        const copy = this.convert(banner);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            console.log(jsonResponse);
            return this.convertItemFromServer(jsonResponse);
        });
    }

    findActivated(): Observable<Banner> {
        return this.http.get(`${this.resourceUrl}/activated`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Banner> {
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
     * Convert a returned JSON object to Banner.
     */
    private convertItemFromServer(json: any): Banner {
        const entity: Banner = Object.assign(new Banner(), json);
        return entity;
    }

    /**
     * Convert a Banner to a JSON which can be sent to the server.
     */
    private convert(banner: Banner): Banner {
        const copy: Banner = Object.assign({}, banner);
        return copy;
    }
}
