import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { CubeComponent } from './cube.component';
import { CubeDetailComponent } from './cube-detail.component';
import { CubePopupComponent } from './cube-dialog.component';
import { CubeDeletePopupComponent } from './cube-delete-dialog.component';

@Injectable()
export class CubeResolvePagingParams implements Resolve<any> {

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

export const cubeRoute: Routes = [
    {
        path: 'cube',
        component: CubeComponent,
        resolve: {
            'pagingParams': CubeResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ausTravelApp.cube.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'cube/:id',
        component: CubeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ausTravelApp.cube.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const cubePopupRoute: Routes = [
    {
        path: 'cube-new',
        component: CubePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ausTravelApp.cube.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'cube/:id/edit',
        component: CubePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ausTravelApp.cube.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'cube/:id/delete',
        component: CubeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ausTravelApp.cube.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
