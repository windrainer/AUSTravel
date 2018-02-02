import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { CNTourComponent } from './cn-tour.component';
import { CNTourDetailComponent } from './cn-tour-detail.component';
import { CNTourPopupComponent } from './cn-tour-dialog.component';
import { CNTourDeletePopupComponent } from './cn-tour-delete-dialog.component';

@Injectable()
export class CNTourResolvePagingParams implements Resolve<any> {

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

export const cNTourRoute: Routes = [
    {
        path: 'cn-tour',
        component: CNTourComponent,
        resolve: {
            'pagingParams': CNTourResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ausTravelApp.cNTour.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'cn-tour/:id',
        component: CNTourDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ausTravelApp.cNTour.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const cNTourPopupRoute: Routes = [
    {
        path: 'cn-tour-new',
        component: CNTourPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ausTravelApp.cNTour.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'cn-tour/:id/edit',
        component: CNTourPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ausTravelApp.cNTour.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'cn-tour/:id/delete',
        component: CNTourDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ausTravelApp.cNTour.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
