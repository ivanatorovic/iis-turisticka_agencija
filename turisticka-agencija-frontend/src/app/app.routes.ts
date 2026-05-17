import { Routes } from '@angular/router';
import { Home } from './features/home/home';
import { Register } from './features/auth/register/register';
import { Login } from './features/auth/login/login';
import { MyProfile } from './features/user/my-profile/my-profile';
import { ArrangementSearch } from './features/arrangements/arrangement-search/arrangement-search';
import { ArrangementDetails } from './features/arrangements/arrangement-details/arrangement-details';
import { ReservationsOverview } from './features/reservations/reservations-overview/reservations-overview';
import { SalesAnalytics } from './features/sales-analytics/sales-analytics/sales-analytics';

export const routes: Routes = [
  {
    path: '',
    component: Home,
  },
  {
    path: 'register',
    component: Register,
  },
  {
  path: 'sales-analytics',
  component: SalesAnalytics,
},
  {
    path: 'login',
    component: Login,
  },
  {
    path: 'my-profile',
    component: MyProfile,
  },
  {
  path: 'reservations',
  component: ReservationsOverview,
},
  {
  path: 'arrangements/:id',
  component: ArrangementDetails,
},
  {
    path: 'arrangements',
    component: ArrangementSearch,
  },
];