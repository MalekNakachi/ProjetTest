import {ChangeDetectionStrategy, ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {FlowableService} from "../../gestion-credit/services-demande/flowable.service";
import {DomSanitizer, SafeResourceUrl} from "@angular/platform-browser";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {MAT_MOMENT_DATE_FORMATS, MomentDateAdapter} from "@angular/material-moment-adapter";
import {CheckBoxSelectionService} from "@syncfusion/ej2-angular-dropdowns";
import {DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE} from "@angular/material/core";
import {GestionOffresService} from "../gestion-offres.service";

@Component({
  selector: 'app-creation-offre',
  templateUrl: './creation-offre.component.html',
  styleUrls: ['./creation-offre.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
  providers: [GestionOffresService,
    {provide: DateAdapter, useClass: MomentDateAdapter, deps: [MAT_DATE_LOCALE]},
    {provide: MAT_DATE_FORMATS, useValue: MAT_MOMENT_DATE_FORMATS},CheckBoxSelectionService],
})

export class CreationOffreComponent implements OnInit {

  identifiant:any;
  reference:any;

  articles: any[] =[];
  selectedArticles: any[] = [];

  fournisseurs: any[] = [];
  selectedFournisseurs: any[] =[];


  offre:any = {
    id: null,
    identifiant: null,
    nomFournisseur: null,
    listeDesProduits: null,
    prix: null,
    lieuDeLivraison: null,
    description: null,
    date: null,
    application: null,
  };

  mode: any = false;


  constructor(private router:Router, private activatedroute:ActivatedRoute, private modal: NgbModal, private gestionOffresService: GestionOffresService, private flowableService: FlowableService, private changeDetectorRef: ChangeDetectorRef, public _DomSanitizationService: DomSanitizer) {
    if (this.activatedroute.snapshot.params['id'] != null) {
      this.gestionOffresService.getDemandesbyId(this.activatedroute.snapshot.params['id']).subscribe((data:any)=>{
        this.offre = data;
        this.getAllArticles();
        this.getAllFournisseurs();
      });
    }else{
      this.getAllArticles();
      this.getAllFournisseurs();
    }


  }

  ngOnInit() {

  }

  getAllFournisseurs() {
    this.gestionOffresService.getAllfournisseurs().subscribe((fournisseurs:any[])=>{
      console.log("list des articles",fournisseurs)
      fournisseurs.forEach(elem=>{
        this.fournisseurs.push(elem['nom']);
        if(this.offre.nomFournisseur.indexOf(elem['nom']) !== -1){
          this.selectedFournisseurs.push(elem['nom'])
        }
      });
    });
  }
  getAllArticles() {
    this.gestionOffresService.getAllarticles().subscribe((listarticles:any[])=>{
      console.log("list des articles",listarticles)
      listarticles.forEach(elem=>{
        this.articles.push(elem['nom']);
        if(this.offre.listeDesProduits.indexOf(elem['nom']) !== -1){
          this.selectedArticles.push(elem['nom'])
        }
      });
    });
  }
  public createOffre(offre):void {
    console.log("values", offre);
    offre['listeDesProduits'] = this.ArrayToString(this.selectedArticles);
    offre['nomFournisseur'] = this.ArrayToString(this.selectedFournisseurs)

    this.gestionOffresService.createOffre(offre)
      .then(
        (Response:any) => {
          console.log(Response);
          this.router.navigate(['/pages/offres']);}  ,

        err => {
          // this.toster.error("Erreur")
        })


  }



  identifier(){

    this.gestionOffresService.getReference(1).subscribe((data:any)=>{

      this.offre.identifiant=data.nomenclature+"_"+ new Date().getFullYear()+ "_"+ data.compteur;
    })
  }

  ArrayToString(List) {
    let str = '';
    for (let t of List) {
      str = str + t + ',';
    }
    return str.slice(0, str.length - 1);
  }
}

