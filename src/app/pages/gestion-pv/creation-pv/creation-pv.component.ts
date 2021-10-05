import {ChangeDetectionStrategy, ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {FlowableService} from "../../gestion-credit/services-demande/flowable.service";
import {DomSanitizer, SafeResourceUrl} from "@angular/platform-browser";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {MAT_MOMENT_DATE_FORMATS, MomentDateAdapter} from "@angular/material-moment-adapter";
import {CheckBoxSelectionService} from "@syncfusion/ej2-angular-dropdowns";
import {DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE} from "@angular/material/core";
import {GestionPvService} from "../gestion-pv.service";

@Component({
  selector: 'app-creation-pv',
  templateUrl: './creation-pv.component.html',
  styleUrls: ['./creation-pv.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
  providers: [GestionPvService,
    {provide: DateAdapter, useClass: MomentDateAdapter, deps: [MAT_DATE_LOCALE]},
    {provide: MAT_DATE_FORMATS, useValue: MAT_MOMENT_DATE_FORMATS},CheckBoxSelectionService],
})

export class CreationPvComponent implements OnInit {

  identifiant:any;
  reference:any;

  articles: any[] =[];
  selectedArticles: any[] = [];

  demandeurs: any[] = ['DEMANDEUR 1', 'DEMANDEUR 2'];
  selectedDemandeurs: any[] =[];


  pv:any = {
    id: null,
    identifiant: null,
    nomDemandeur: null,
    date: null,
    listeDesProduits: null,
    lieuDeLivraison: null,
    tel: null,
    prix: null,
    status: null,
    application: null,
  };

  mode: any = false;


  constructor(private router:Router, private activatedroute:ActivatedRoute, private modal: NgbModal, private gestionPvService: GestionPvService, private flowableService: FlowableService, private changeDetectorRef: ChangeDetectorRef, public _DomSanitizationService: DomSanitizer) {
    if (this.activatedroute.snapshot.params['id'] != null) {
      this.gestionPvService.getDemandesbyId(this.activatedroute.snapshot.params['id']).subscribe((data:any)=>{
        this.pv = data;
        this.getAllArticles();
        this.demandeurs.forEach(elem=> {
          if (this.pv.nomDemandeur.indexOf(elem) !== -1) {
            this.selectedDemandeurs.push(elem)
          }
        });
      });
    }else{
      this.getAllArticles();
    }


  }

  ngOnInit() {

  }

  getAllArticles() {
    this.gestionPvService.getAllarticles().subscribe((listarticles:any[])=>{
      console.log("list des articles",listarticles)
      listarticles.forEach(elem=>{
        this.articles.push(elem['nom'])
        if(this.pv.listeDesProduits.indexOf(elem['nom']) !== -1){
          this.selectedArticles.push(elem['nom'])
        }
      });
    });
  }
  public createPv(pv):void {
    console.log("values", pv);
    pv['listeDesProduits'] = this.ArrayToString(this.selectedArticles);
    pv['nomDemandeur'] = this.ArrayToString(this.selectedDemandeurs)

    this.gestionPvService.createPv(pv)
      .then(
        (Response:any) => {
          console.log(Response);
          this.router.navigate(['/pages/pvs']);}  ,

        err => {
          // this.toster.error("Erreur")
        })


  }



  identifier(){

    this.gestionPvService.getReference(5).subscribe((data:any)=>{

      this.pv.identifiant=data.nomenclature+"_"+ new Date().getFullYear()+ "_"+ data.compteur;
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

