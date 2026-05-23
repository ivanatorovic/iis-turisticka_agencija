import { Routes } from '@angular/router';
import { Home } from './features/home/home';
import { Register } from './features/auth/register/register';
import { Login } from './features/auth/login/login';
import { MyProfile } from './features/user/my-profile/my-profile';
import { ArrangementSearch } from './features/arrangements/arrangement-search/arrangement-search';
import { ArrangementDetails } from './features/arrangements/arrangement-details/arrangement-details';
import { ReservationsOverview } from './features/reservations/reservations-overview/reservations-overview';
import { SalesAnalytics } from './features/sales-analytics/sales-analytics/sales-analytics';
import { WorkflowCreate } from './features/workflows/workflow-create/workflow-create';
import { WorkflowList } from './features/workflows/workflow-list/workflow-list';
import { WorkflowDetails } from './features/workflows/workflow-details/workflow-details';
import { ReceivedWorkflows } from './features/workflows/received-workflows/received-workflows';
import { ManagerDestinations } from './features/destinations/manager-destinations/manager-destinations';
import { ManagerCalendarsComponent } from './features/calendars/manager-calendars/manager-calendars';
import { ManagerArrangementsList } from './features/arrangements/manager-arrangements/manager-arrangements-list/manager-arrangements-list';
import { AdditionalActivitiesManager } from './features/activities/additional-activities-manager/additional-activities-manager';
import { CreateActivities } from './features/activities/create-activities/create-activities';
import { ManagerArrangementTermsList } from './features/arrangements/manager-arrangements/manager-arrangement-terms-list/manager-arrangement-terms-list';
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
  {
    path: 'workflows/create',
    component: WorkflowCreate,
  },
  {
    path: 'workflows',
    component: WorkflowList,
  },
  {
    path: 'workflows/:id',
    component: WorkflowDetails,
  },
  {
    path: 'received-workflows',
    component: ReceivedWorkflows,
  },
  {
    path: 'manager-destinations',
    component: ManagerDestinations,
  },

  {
    path: 'manager-calendars',
    component: ManagerCalendarsComponent,
  },
  {
    path: 'workflows/:workflowId/create-arrangement',
    loadComponent: () =>
      import('./features/arrangements/manager-arrangements/manager-arrangements-form/manager-arrangements-form').then(
        (m) => m.ManagerArrangementForm,
      ),
  },
  {
    path: 'workflows/:workflowId/edit-arrangement/:arrangementId',
    loadComponent: () =>
      import('./features/arrangements/manager-arrangements/manager-arrangements-form/manager-arrangements-form').then(
        (m) => m.ManagerArrangementForm,
      ),
  },
  {
    path: 'my-manager-arrangements',
    loadComponent: () =>
      import('./features/arrangements/manager-arrangements/manager-arrangements-list/manager-arrangements-list').then(
        (m) => m.ManagerArrangementsList,
      ),
  },

  {
    path: 'manager-arrangements',
    component: ManagerArrangementsList,
  },
  {
    path: 'additional-activities-manager',
    component: AdditionalActivitiesManager,
  },
  {
    path: 'create-additional-activity',
    component: CreateActivities,
  },
  {
    path: 'create-additional-activity/:id',
    component: CreateActivities,
  },
  {
    path: 'manager-arrangement-terms',
    component: ManagerArrangementTermsList,
  },
  {
    path: 'manager-arrangements/:id/activities',
    loadComponent: () =>
      import('./features/arrangements/manager-arrangements/manager-arrangements-activities/manager-arrangements-activities').then(
        (m) => m.ManagerArrangementsActivities,
      ),
  },
];
