import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { Cube } from './cube.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class CubeService {

    private resourceUrl = SERVER_API_URL + 'api/cubes';

    constructor(private http: Http) { }

    create(cube: Cube): Observable<Cube> {
        const copy = this.convert(cube);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(cube: Cube): Observable<Cube> {
        const copy = this.convert(cube);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Cube> {
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
     * Convert a returned JSON object to Cube.
     */
    private convertItemFromServer(json: any): Cube {
        const entity: Cube = Object.assign(new Cube(), json);
        return entity;
    }

    /**
     * Convert a Cube to a JSON which can be sent to the server.
     */
    private convert(cube: Cube): Cube {
        const copy: Cube = Object.assign({}, cube);
        return copy;
    }
}
