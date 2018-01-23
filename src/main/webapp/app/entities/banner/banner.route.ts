import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { BannerComponent } from './banner.component';
import { BannerDetailComponent } from './banner-detail.component';
import { BannerPopupComponent } from './banner-dialog.component';
import { BannerDeletePopupComponent } from './banner-delete-dialog.component';

export const bannerRoute: Routes = [
    {
        path: 'banner',
        component: BannerComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ausTravelApp.banner.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'banner/:id',
        component: BannerDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ausTravelApp.banner.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const bannerPopupRoute: Routes = [
    {
        path: 'banner-new',
        component: BannerPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ausTravelApp.banner.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'banner/:id/edit',
        component: BannerPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ausTravelApp.banner.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'banner/:id/delete',
        component: BannerDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ausTravelApp.banner.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
