import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { PerfectScrollbarModule } from 'ngx-perfect-scrollbar';
import { NgxChartsModule } from '@swimlane/ngx-charts';
import { DirectivesModule } from '../../theme/directives/directives.module';
import { DashboardComponent } from './dashboard.component';
import {DxChartModule, DxDataGridModule, DxSelectBoxModule} from 'devextreme-angular';
import {DiskSpaceComponent} from '../disk-space/disk-space.component';

export const routes = [
  {
    path: '',
    component: DashboardComponent,
    children: [
      { path: 'dashboard', component: DashboardComponent, pathMatch: 'full' }
    ]
  }
];


@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    FormsModule,
    PerfectScrollbarModule,
    NgxChartsModule,
    DirectivesModule,
    DxDataGridModule,
      DxChartModule,
      DxSelectBoxModule,
  ],
  declarations: [
    DashboardComponent,
    DiskSpaceComponent
  ]
})

export class DashboardModule { }
