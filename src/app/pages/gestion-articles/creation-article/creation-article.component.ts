import {ChangeDetectionStrategy, ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {FlowableService} from "../../gestion-credit/services-demande/flowable.service";
import {DomSanitizer, SafeResourceUrl} from "@angular/platform-browser";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {MAT_MOMENT_DATE_FORMATS, MomentDateAdapter} from "@angular/material-moment-adapter";
import {CheckBoxSelectionService} from "@syncfusion/ej2-angular-dropdowns";
import {DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE} from "@angular/material/core";
import {GestionArticlesService} from "../gestion-articles.service";

@Component({
  selector: 'app-creation-article',
  templateUrl: './creation-article.component.html',
  styleUrls: ['./creation-article.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
  providers: [GestionArticlesService,
    {provide: DateAdapter, useClass: MomentDateAdapter, deps: [MAT_DATE_LOCALE]},
    {provide: MAT_DATE_FORMATS, useValue: MAT_MOMENT_DATE_FORMATS},CheckBoxSelectionService],
})

export class CreationArticleComponent implements OnInit {

  identifiant:any;
  reference:any;


  article:any = {
    id: null,
    identifiant: null,
    nom: null,
    prixHt: null,
    quantite: null,
    staut: null,
    application: null,
  };

  mode: any = false;


  constructor(private router:Router,private activatedroute:ActivatedRoute,private modal: NgbModal,private gestionArticleService: GestionArticlesService, private flowableService: FlowableService,private changeDetectorRef: ChangeDetectorRef,public _DomSanitizationService: DomSanitizer) {
    if (this.activatedroute.snapshot.params['id'] != null) {
      this.gestionArticleService.getDemandesbyId(this.activatedroute.snapshot.params['id']).subscribe((data:any)=>{
        this.article = data;
      });
    }
  }

  ngOnInit() {

  }


  public createArticle(article):void {
    console.log("values", article);
    this.gestionArticleService.createArticle(article)
      .then(
        (Response:any) => {
          console.log(Response);
          this.router.navigate(['/pages/articles']);}  ,

        err => {
          // this.toster.error("Erreur")
        })


  }



  identifier(){

    this.gestionArticleService.getReference(7).subscribe((data:any)=>{

      this.article.identifiant=data.nomenclature+"_"+ new Date().getFullYear()+ "_"+ data.compteur;
    })
  }
}

