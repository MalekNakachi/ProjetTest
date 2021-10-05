import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { PERFECT_SCROLLBAR_CONFIG } from 'ngx-perfect-scrollbar';
import { PerfectScrollbarConfigInterface } from 'ngx-perfect-scrollbar';
const DEFAULT_PERFECT_SCROLLBAR_CONFIG: PerfectScrollbarConfigInterface = {
  suppressScrollX: true
};

import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatSelectModule} from "@angular/material/select";
import {MatCardModule} from "@angular/material/card";
import {MatDatepickerModule} from "@angular/material/datepicker";
import {MatCheckboxModule} from "@angular/material/checkbox";
import {MatRadioModule} from '@angular/material/radio';
import {MatIconModule} from '@angular/material/icon';
import {MatProgressBarModule} from '@angular/material/progress-bar';
import {MatButtonModule} from '@angular/material/button';
import {MatDividerModule} from '@angular/material/divider';
import {MatChipsModule} from '@angular/material/chips';
import {MatAutocompleteModule} from '@angular/material/autocomplete';
import {MatDialogModule} from "@angular/material/dialog";
import {MatListModule} from '@angular/material/list';
import {MatSlideToggleModule} from '@angular/material/slide-toggle';
import {MatTableModule} from '@angular/material/table';
import {NgbModalModule, NgbModule} from '@ng-bootstrap/ng-bootstrap';
import { CustomFormsModule } from 'ng2-validation'
import { PipesModule } from '../theme/pipes/pipes.module';
import { routing } from './pages.routing';
import { PagesComponent } from './pages.component';
import { HeaderComponent } from '../theme/components/header/header.component';
import { FooterComponent } from '../theme/components/footer/footer.component';
import { SidebarComponent } from '../theme/components/sidebar/sidebar.component';
import { VerticalMenuComponent } from '../theme/components/menu/vertical-menu/vertical-menu.component';
import { HorizontalMenuComponent } from '../theme/components/menu/horizontal-menu/horizontal-menu.component';
import { BreadcrumbComponent } from '../theme/components/breadcrumb/breadcrumb.component';
import { BackTopComponent } from '../theme/components/back-top/back-top.component';
import { FullScreenComponent } from '../theme/components/fullscreen/fullscreen.component';
import { ApplicationsComponent } from '../theme/components/applications/applications.component';
import { MessagesComponent } from '../theme/components/messages/messages.component';
import { UserMenuComponent } from '../theme/components/user-menu/user-menu.component';
import { FlagsMenuComponent } from '../theme/components/flags-menu/flags-menu.component';
import { SideChatComponent } from '../theme/components/side-chat/side-chat.component';
import { FavoritesComponent } from '../theme/components/favorites/favorites.component';
import { SearchComponent } from './search/search.component';
import { StatistiqueComponent } from './statistique/statistique.component';
import { ProfileComponent } from './profile/profile.component';
import {HTTP_INTERCEPTORS, HttpClient, HttpClientModule} from "@angular/common/http";
import {MyInterceptor} from "../noop-interceptor.service";
import {DirectivesModule} from "../theme/directives/directives.module";


import {WorkflowModule} from "../workflow/workflow.module";
import {ResizableDirective} from "./resizable.directive";
import {fileModuleModule} from "../fileModule/fileModule.module";
import {AlfrescoService} from "../fileModule/alfresco_services/AlfrescoApi.service";
import {Service} from "../fileModule/alfresco_services/document-library.service";
import {ServiceFileService} from "../fileModule/alfresco_services/service-file.service";
import {NgxDocViewerModule} from "ngx-doc-viewer";
import {ModalFileModuleComponent} from "../fileModule/Modal_Scan-profil/modalFileModule.component";
import {SharedDevExtremeModuleModule} from "../shared-dev-extreme/sharedDevExtreme.module";
import {
  DevExtremeModule,
  DxDataGridModule,
  DxLoadPanelModule,
  DxTagBoxModule,
  DxTemplateModule
} from "devextreme-angular";
import {ToastrModule, ToastrService} from "ngx-toastr";
import {SplitterModule} from "@syncfusion/ej2-angular-layouts";
import {TranslateLoader, TranslateModule} from "@ngx-translate/core";
import {TranslateHttpLoader} from "@ngx-translate/http-loader";
import {BrowserModule} from "@angular/platform-browser";
import {platformBrowserDynamic} from "@angular/platform-browser-dynamic";
import {MultiSelectModule} from "@syncfusion/ej2-angular-dropdowns";
import {MultiselectDropdownModule} from "angular-2-dropdown-multiselect";
import {MatTooltipModule} from "@angular/material/tooltip";
import {MatRippleModule} from "@angular/material/core";
import {GestionRolesComponent} from "./administrateur/gestion-roles/gestion-roles.component";
import {GestionUtilisateursComponent} from "./administrateur/gestion-utilisateurs/gestion-utilisateurs.component";
import {NgxPaginationModule} from "ngx-pagination";
import { GestionArticlesComponent } from './gestion-articles/gestion-articles.component';
import { CreationArticleComponent } from './gestion-articles/creation-article/creation-article.component';
import {GestionFournisseursComponent} from "./gestion-fournisseus/gestion-fournisseur.component";
import {CreationFournisseurComponent} from "./gestion-fournisseus/creation-fournisseur/creation-fournisseur.component";
import {GestionOffresComponent} from "./gestion-offres/gestion-offres.component";
import {CreationOffreComponent} from "./gestion-offres/creation-offre/creation-offre.component";
import {CreationPvComponent} from "./gestion-pv/creation-pv/creation-pv.component";
import {GestionPvComponent} from "./gestion-pv/gestion-pv.component";
import {TousDemandeComponent} from "./gestion-credit/tous-credit/tous-demande.component";
import {FormulaireDemandeComponent} from "./gestion-credit/formulaire-credit/formulaire-demande.component";
import {DashboardComponent} from "./dashboard/dashboard.component";
import {GestionBoncommandeComponent} from "./gestion-boncommande/gestion-boncommande.component";
import {CreationBoncommandeComponent} from "./gestion-boncommande/creation-boncommande/creation-boncommande.component";
import {GridActivityComponent} from "./gestion-boncommande/creation-boncommande/activitytask.component";

// export function HttpLoaderFactory(httpClient: HttpClient) {
//     return new TranslateHttpLoader(httpClient,'./assets/i18/','.json');
// }
@NgModule({
    imports: [
        DevExtremeModule,
        DxDataGridModule,
        ToastrModule,
        SplitterModule,
        fileModuleModule,
        WorkflowModule,
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        ToastrModule,
        NgbModule,
        PipesModule,
      NgxPaginationModule,
        DirectivesModule,
        routing,
        NgbModalModule,
        NgxDocViewerModule,
        SharedDevExtremeModuleModule,
        DxLoadPanelModule,
        DxTemplateModule,
        CustomFormsModule,
        MatFormFieldModule,
        MatInputModule,
        MatCardModule,
        MatDatepickerModule,
        MatCheckboxModule,
        MatRadioModule,
        MatIconModule,
        MatProgressBarModule,
        MatButtonModule,
        MatDividerModule,
        MatChipsModule,
        MatAutocompleteModule,
        MatDialogModule,
        MatListModule,
        MatSlideToggleModule,
        MatTableModule,
        MatSelectModule,
      MatButtonModule,
      MatRippleModule,
      MatTooltipModule,
      DxTagBoxModule,
      MultiselectDropdownModule,
      MultiSelectModule,
        // TranslateModule.forRoot({
        //     loader: {
        //         provide: TranslateLoader,
        //         useFactory: HttpLoaderFactory,
        //         deps: [HttpClient]
        //     }
        // })

    ],
    declarations: [
        ResizableDirective,
        PagesComponent,
        HeaderComponent,
        FooterComponent,
        SidebarComponent,
        VerticalMenuComponent,
        HorizontalMenuComponent,
        BreadcrumbComponent,
        BackTopComponent,
        FullScreenComponent,
        ApplicationsComponent,
        MessagesComponent,
        UserMenuComponent,
        FlagsMenuComponent,
        SideChatComponent,
        FavoritesComponent,
        SearchComponent,
        GestionArticlesComponent,
        CreationArticleComponent,
      GestionFournisseursComponent,
      CreationFournisseurComponent,
      GestionOffresComponent,
      CreationOffreComponent,
      CreationPvComponent,
      GestionPvComponent,
      TousDemandeComponent,
      FormulaireDemandeComponent,
      GestionBoncommandeComponent,
      CreationBoncommandeComponent,
      GridActivityComponent
    ],
    bootstrap: [PagesComponent],
  providers:[
    AlfrescoService,
    ServiceFileService,
      {provide: ToastrService, useClass: ToastrService},
    Service,
    {
      provide: PERFECT_SCROLLBAR_CONFIG,
      useValue: DEFAULT_PERFECT_SCROLLBAR_CONFIG
    },
      {provide: HTTP_INTERCEPTORS, useClass: MyInterceptor, multi: true}
  ],

  entryComponents: [ModalFileModuleComponent],
  exports: []
})
export class PagesModule { }
platformBrowserDynamic().bootstrapModule(PagesModule);
