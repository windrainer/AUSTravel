import {Injectable} from '@angular/core';
import { SERVER_API_URL } from '../../app.constants';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import {Tour} from '../../entities/tour/tour.model';
import { JhiDateUtils } from 'ng-jhipster';
import {CNTour} from '../../entities/c-n-tour/cn-tour.model';

@Injectable()
export class DetailService {
    private resourceUrl = SERVER_API_URL + 'api/tours/page';

    private resourceURLCN = SERVER_API_URL + 'api/c-n-tours/page'

    constructor(private http: Http, private dateUtils: JhiDateUtils) {

    }

    find(id: number): Observable<Tour> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    findCN(id: number): Observable<CNTour> {
        return this.http.get(`${this.resourceURLCN}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    /**
     * Convert a returned JSON object to Tour.
     */
    private convertItemFromServer(json: any): Tour {
        const entity: Tour = Object.assign(new Tour(), json);
        entity.createTime = this.dateUtils
            .convertLocalDateFromServer(json.createTime);
        entity.updateTime = this.dateUtils
            .convertLocalDateFromServer(json.updateTime);
        return entity;
    }

}
