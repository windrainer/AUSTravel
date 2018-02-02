import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { CnbannerComponent } from './cnbanner.component';
import { CnbannerDetailComponent } from './cnbanner-detail.component';
import { CnbannerPopupComponent } from './cnbanner-dialog.component';
import { CnbannerDeletePopupComponent } from './cnbanner-delete-dialog.component';

@Injectable()
export class CnbannerResolvePagingParams implements Resolve<any> {

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

export const cnbannerRoute: Routes = [
    {
        path: 'cnbanner',
        component: CnbannerComponent,
        resolve: {
            'pagingParams': CnbannerResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ausTravelApp.cnbanner.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'cnbanner/:id',
        component: CnbannerDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ausTravelApp.cnbanner.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const cnbannerPopupRoute: Routes = [
    {
        path: 'cnbanner-new',
        component: CnbannerPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ausTravelApp.cnbanner.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'cnbanner/:id/edit',
        component: CnbannerPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ausTravelApp.cnbanner.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'cnbanner/:id/delete',
        component: CnbannerDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ausTravelApp.cnbanner.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
