import { Routes } from '@angular/router';

import { TourDetailComponent } from './detail.component';

export const TOUR_DETAIL_ROUTES: Routes = [
    {
        path: 'tour-detail/:id',
        component: TourDetailComponent,
        data: {
            authorities: [],
            pageTitle: 'home.title'
        }
    },
    {
        path: 'cn-tour-detail/:id',
        component: TourDetailComponent,
        data: {
            authorities: [],
            pageTitle: 'home.title'
        }
    }
];
