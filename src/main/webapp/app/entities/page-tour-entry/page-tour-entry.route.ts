import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { PageTourEntryComponent } from './page-tour-entry.component';
import { PageTourEntryDetailComponent } from './page-tour-entry-detail.component';
import { PageTourEntryPopupComponent } from './page-tour-entry-dialog.component';
import { PageTourEntryDeletePopupComponent } from './page-tour-entry-delete-dialog.component';

@Injectable()
export class PageTourEntryResolvePagingParams implements Resolve<any> {

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

export const pageTourEntryRoute: Routes = [
    {
        path: 'page-tour-entry',
        component: PageTourEntryComponent,
        resolve: {
            'pagingParams': PageTourEntryResolvePagingParams
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ausTravelApp.pageTourEntry.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'page-tour-entry/:id',
        component: PageTourEntryDetailComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ausTravelApp.pageTourEntry.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const pageTourEntryPopupRoute: Routes = [
    {
        path: 'page-tour-entry-new',
        component: PageTourEntryPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ausTravelApp.pageTourEntry.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'page-tour-entry/:id/edit',
        component: PageTourEntryPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ausTravelApp.pageTourEntry.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'page-tour-entry/:id/delete',
        component: PageTourEntryDeletePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ausTravelApp.pageTourEntry.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
