import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { CNPageTourEntryComponent } from './cn-page-tour-entry.component';
import { CNPageTourEntryDetailComponent } from './cn-page-tour-entry-detail.component';
import { CNPageTourEntryPopupComponent } from './cn-page-tour-entry-dialog.component';
import { CNPageTourEntryDeletePopupComponent } from './cn-page-tour-entry-delete-dialog.component';

@Injectable()
export class CNPageTourEntryResolvePagingParams implements Resolve<any> {

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

export const cNPageTourEntryRoute: Routes = [
    {
        path: 'cn-page-tour-entry',
        component: CNPageTourEntryComponent,
        resolve: {
            'pagingParams': CNPageTourEntryResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ausTravelApp.cNPageTourEntry.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'cn-page-tour-entry/:id',
        component: CNPageTourEntryDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ausTravelApp.cNPageTourEntry.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const cNPageTourEntryPopupRoute: Routes = [
    {
        path: 'cn-page-tour-entry-new',
        component: CNPageTourEntryPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ausTravelApp.cNPageTourEntry.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'cn-page-tour-entry/:id/edit',
        component: CNPageTourEntryPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ausTravelApp.cNPageTourEntry.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'cn-page-tour-entry/:id/delete',
        component: CNPageTourEntryDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ausTravelApp.cNPageTourEntry.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
