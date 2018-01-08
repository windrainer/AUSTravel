import { Route } from '@angular/router';

import { TourDetailComponent } from './detail.component';

export const TOUR_DETAIL_ROUTE: Route = {
    path: 'tour-detail/:id',
    component: TourDetailComponent,
    data: {
        authorities: [],
        pageTitle: 'home.title'
    }
};
