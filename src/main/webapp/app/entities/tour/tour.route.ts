import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { TourComponent } from './tour.component';
import { TourDetailComponent } from './tour-detail.component';
import { TourPopupComponent } from './tour-dialog.component';
import { TourDeletePopupComponent } from './tour-delete-dialog.component';

@Injectable()
export class TourResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const tourRoute: Routes = [
    {
        path: 'tour',
        component: TourComponent,
        resolve: {
            'pagingParams': TourResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ausTravelApp.tour.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'tour/:id',
        component: TourDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ausTravelApp.tour.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const tourPopupRoute: Routes = [
    {
        path: 'tour-new',
        component: TourPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ausTravelApp.tour.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'tour/:id/edit',
        component: TourPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ausTravelApp.tour.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'tour/:id/delete',
        component: TourDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ausTravelApp.tour.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
