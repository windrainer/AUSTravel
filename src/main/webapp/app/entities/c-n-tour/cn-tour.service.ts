import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { CNTour } from './cn-tour.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class CNTourService {

    private resourceUrl = SERVER_API_URL + 'api/c-n-tours';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(cNTour: CNTour): Observable<CNTour> {
        const copy = this.convert(cNTour);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(cNTour: CNTour): Observable<CNTour> {
        const copy = this.convert(cNTour);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<CNTour> {
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
     * Convert a returned JSON object to CNTour.
     */
    private convertItemFromServer(json: any): CNTour {
        const entity: CNTour = Object.assign(new CNTour(), json);
        entity.createTime = this.dateUtils
            .convertLocalDateFromServer(json.createTime);
        entity.updateTime = this.dateUtils
            .convertLocalDateFromServer(json.updateTime);
        return entity;
    }

    /**
     * Convert a CNTour to a JSON which can be sent to the server.
     */
    private convert(cNTour: CNTour): CNTour {
        const copy: CNTour = Object.assign({}, cNTour);
        copy.createTime = this.dateUtils
            .convertLocalDateToServer(cNTour.createTime);
        copy.updateTime = this.dateUtils
            .convertLocalDateToServer(cNTour.updateTime);
        return copy;
    }
}
