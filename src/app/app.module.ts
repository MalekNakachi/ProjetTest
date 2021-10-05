import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';
import { routing } from './app.routing';
import { AppSettings } from './app.settings';
import { AppComponent } from './app.component';
// import { NotFoundComponent } from './pages/errors/not-found/not-found.component';
import {AuthGuardService} from "./auth-guard.service";
import {AuthService} from "./auth.service";
import {HTTP_INTERCEPTORS, HttpClientModule,HttpClient} from "@angular/common/http";
import {MyInterceptor} from "./noop-interceptor.service";
import {EnvServiceProvider} from "../env.service.provider";
import {SharedDevExtremeComponent} from "./shared-dev-extreme/shared-dev-extreme.component";
import {ToastrModule} from "ngx-toastr";
import {TranslateLoader, TranslateModule} from "@ngx-translate/core";
import {TranslateHttpLoader} from "@ngx-translate/http-loader";

// export function HttpLoaderFactory(httpClient: HttpClient) {
//   return new TranslateHttpLoader(httpClient,'./assets/i18/','.json');
// }
@NgModule({
  declarations: [
    AppComponent,
   SharedDevExtremeComponent,

  ],
  imports: [

    BrowserModule,
    BrowserAnimationsModule,
      HttpClientModule,
    ToastrModule.forRoot(),    // MultiSelectAllModule,
      // NgxSoapModule,

    // AgmCoreModule.forRoot({
    //   apiKey: 'AIzaSyBNcjxo_35qnEG17dQvvftWa68eZWepYE0'
    // }),
    //CalendarModule.forRoot(),

    routing,

   // BsDatepickerModule.forRoot(),
   // SharedDevExtremeModuleModule,
    //fileModuleModule,
      //WorkflowModule
    // NgxSoapModule,
    ToastrModule,
    // TranslateModule.forRoot({
    //   loader: {
    //     provide: TranslateLoader,
    //     useFactory: HttpLoaderFactory,
    //     deps: [HttpClient]
    //   }
    // }),
  ],

  providers: [ AppSettings, AuthGuardService, AuthService,EnvServiceProvider,
      {provide: HTTP_INTERCEPTORS, useClass: MyInterceptor, multi: true}
  ],
  bootstrap: [ AppComponent ]
})
export class AppModule {

}
