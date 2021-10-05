import { Routes, RouterModule } from '@angular/router';
import { ModuleWithProviders } from '@angular/core';
import { PagesComponent } from './pages.component';
import { SearchComponent } from './search/search.component';
import {StatistiqueComponent} from "./statistique/statistique.component";
import {ProfileComponent} from "./profile/profile.component";


import {GestionRolesService} from "./administrateur/gestion-roles/gestion-roles.service";
import {GestionUtilisateursComponent} from "./administrateur/gestion-utilisateurs/gestion-utilisateurs.component";
import {GestionRolesComponent} from "./administrateur/gestion-roles/gestion-roles.component";
import {GestionArticlesComponent} from "./gestion-articles/gestion-articles.component";
import {CreationArticleComponent} from "./gestion-articles/creation-article/creation-article.component";
import {CreationFournisseurComponent} from "./gestion-fournisseus/creation-fournisseur/creation-fournisseur.component";
import {GestionFournisseursComponent} from "./gestion-fournisseus/gestion-fournisseur.component";
import {GestionOffresComponent} from "./gestion-offres/gestion-offres.component";
import {CreationOffreComponent} from "./gestion-offres/creation-offre/creation-offre.component";
import {GestionPvComponent} from "./gestion-pv/gestion-pv.component";
import {CreationPvComponent} from "./gestion-pv/creation-pv/creation-pv.component";
import {TousDemandeComponent} from "./gestion-credit/tous-credit/tous-demande.component";
import {FormulaireDemandeComponent} from "./gestion-credit/formulaire-credit/formulaire-demande.component";
import {GestionBoncommandeComponent} from "./gestion-boncommande/gestion-boncommande.component";
import {CreationBoncommandeComponent} from "./gestion-boncommande/creation-boncommande/creation-boncommande.component";

export const routes: Routes = [
    {

        path: '',
        component: PagesComponent,
        children:[

          { path:'', redirectTo:'dashboard', pathMatch:'full' },
          { path: 'dashboard', loadChildren: () => import('./dashboard/dashboard.module').then(m => m.DashboardModule) },
          { path: 'articles', component:GestionArticlesComponent , data: { breadcrumb: 'Type' } },
          { path: 'article', component:CreationArticleComponent , data: { breadcrumb: 'Type' } },
          { path: 'article/:id', component:CreationArticleComponent , data: { breadcrumb: 'Type' } },
          { path: 'fournisseurs', component:GestionFournisseursComponent , data: { breadcrumb: 'Type' } },
          { path: 'fournisseur', component:CreationFournisseurComponent , data: { breadcrumb: 'Type' } },
          { path: 'fournisseur/:id', component:CreationFournisseurComponent , data: { breadcrumb: 'Type' } },
          { path: 'offres', component:GestionOffresComponent , data: { breadcrumb: 'Type' } },
          { path: 'offre', component:CreationOffreComponent , data: { breadcrumb: 'Type' } },
          { path: 'offre/:id', component:CreationOffreComponent , data: { breadcrumb: 'Type' } },
          { path: 'pvs', component:GestionPvComponent , data: { breadcrumb: 'Type' } },
          { path: 'pv', component:CreationPvComponent , data: { breadcrumb: 'Type' } },
          { path: 'pv/:id', component:CreationPvComponent , data: { breadcrumb: 'Type' } },
          { path: 'demandes', component:TousDemandeComponent , data: { breadcrumb: 'Type' } },
          { path: 'demande', component:FormulaireDemandeComponent , data: { breadcrumb: 'Type' } },
          { path: 'demande/:id', component:FormulaireDemandeComponent , data: { breadcrumb: 'Type' } },
          { path: 'boncommandes', component:GestionBoncommandeComponent , data: { breadcrumb: 'Type' } },
          { path: 'boncommande', component:CreationBoncommandeComponent , data: { breadcrumb: 'Type' } },
          { path: 'boncommande/:id', component:CreationBoncommandeComponent , data: { breadcrumb: 'Type' } },




          // { path: 'credits', component:TousCreditComponent , data: { breadcrumb: 'Type' } },
          // { path: 'FormulaireDemandeCredit/:id', component:FormulaireDemandeCreditComponent , data: { breadcrumb: 'Type' } },
          // { path: 'FormulaireDemandeCredit', component:FormulaireDemandeCreditComponent , data: { breadcrumb: 'Type' } },
          // { path: 'utilisateurs', component:GestionUtilisateursComponent , data: { breadcrumb: 'Type' } },
          // { path: 'roles', component:GestionRolesComponent , data: { breadcrumb: 'Type' } }





        ],

    }
];

export const routing: ModuleWithProviders<any> = RouterModule.forChild(routes);



