import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { LoginComponent } from './login.component';
import {DxLoadPanelModule} from "devextreme-angular";
import {ToastrModule, ToastrService} from "ngx-toastr";
export const routes = [
  { path: '', component: LoginComponent, pathMatch: 'full' }
];
@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule.forChild(routes),
    ToastrModule,
    DxLoadPanelModule,
  ],
  declarations: [LoginComponent],
  providers: [
    {provide: ToastrService, useClass: ToastrService},
  ]
})

export class LoginModule { }
