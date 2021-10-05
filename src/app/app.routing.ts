import { Routes, RouterModule, PreloadAllModules  } from '@angular/router';
import { ModuleWithProviders } from '@angular/core';
// import { AppCustomPreloader } from './app-routing-loader';

import {AuthGuardService} from "./auth-guard.service";
import {LoginComponent} from "./pages/login/login.component";

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
    { path: 'login', loadChildren: () => import('./pages/login/login.module').then(m => m.LoginModule) },
  //{ path: 'login', loadChildren: './pages/login/login.module#LoginModule' },
  //   { path: 'pages', loadChildren: () => import('./pages/pages.module').then(m => m.PagesModule)},
  { path: 'pages', loadChildren: './pages/pages.module#PagesModule'},
 // { path: 'register', loadChildren: './pages/register/register.module#RegisterModule' },
];



export const routing: ModuleWithProviders<any> = RouterModule.forRoot(routes, {
    preloadingStrategy: PreloadAllModules,
    useHash: true,
    relativeLinkResolution: 'legacy'
});
