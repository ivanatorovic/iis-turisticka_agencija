import { Routes } from '@angular/router';
import { Home } from './features/home/home';
import { Register } from './features/auth/register/register';
import { Login } from './features/auth/login/login';
import { MyProfile } from './features/user/my-profile/my-profile';
import { ArrangementSearch } from './features/arrangements/arrangement-search/arrangement-search';

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
    path: 'login',
    component: Login,
  },
  {
    path: 'my-profile',
    component: MyProfile,
  },
  {
    path: 'arrangements',
    component: ArrangementSearch,
  },
];