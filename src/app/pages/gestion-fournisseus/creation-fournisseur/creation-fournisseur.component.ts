import {ChangeDetectionStrategy, ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {FlowableService} from "../../gestion-credit/services-demande/flowable.service";
import {DomSanitizer, SafeResourceUrl} from "@angular/platform-browser";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {MAT_MOMENT_DATE_FORMATS, MomentDateAdapter} from "@angular/material-moment-adapter";
import {CheckBoxSelectionService} from "@syncfusion/ej2-angular-dropdowns";
import {DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE} from "@angular/material/core";
import {GestionFournisseurService} from "../gestion-fournisseur.service";

@Component({
  selector: 'app-creation-fournisseur',
  templateUrl: './creation-fournisseur.component.html',
  styleUrls: ['./creation-fournisseur.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
  providers: [GestionFournisseurService,
    {provide: DateAdapter, useClass: MomentDateAdapter, deps: [MAT_DATE_LOCALE]},
    {provide: MAT_DATE_FORMATS, useValue: MAT_MOMENT_DATE_FORMATS},CheckBoxSelectionService],
})

export class CreationFournisseurComponent implements OnInit {

  identifiant:any;
  reference:any;


  fournisseur:any = {
    id: null,
    identifiant: null,
    nom: null,
    tel: null,
    email: null,
    type: null,
    codePostal: null,
    fax: null,
    region: null,
    note: null,
    application: ['pmm']
  };

  mode: any = false;


  constructor(private router:Router, private activatedroute:ActivatedRoute, private modal: NgbModal, private gestionFournisseurService: GestionFournisseurService, private flowableService: FlowableService, private changeDetectorRef: ChangeDetectorRef, public _DomSanitizationService: DomSanitizer) {
    if (this.activatedroute.snapshot.params['id'] != null) {
      this.gestionFournisseurService.getDemandesbyId(this.activatedroute.snapshot.params['id']).subscribe((data:any)=>{
        this.fournisseur = data;
      });
    }
  }

  ngOnInit() {

  }


  public createFournisseur(fournisseur):void {
    console.log("values", fournisseur);
    this.gestionFournisseurService.createArticle(fournisseur)
      .then(
        (Response:any) => {
          console.log(Response);
          this.router.navigate(['/pages/fournisseurs']);}  ,

        err => {
          // this.toster.error("Erreur")
        })


  }



  identifier(){

    this.gestionFournisseurService.getReference(2).subscribe((data:any)=>{

      this.fournisseur.identifiant=data.nomenclature+"_"+ new Date().getFullYear()+ "_"+ data.compteur;
    })
  }
}

