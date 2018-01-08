import { Route } from '@angular/router';
import { TourListComponent } from './';

export const TOUR_LIST_ROUTE: Route = {
    path: '',
    component: TourListComponent,
    data: {
        authorities: [],
        pageTitle: 'home.title'
    }
};
