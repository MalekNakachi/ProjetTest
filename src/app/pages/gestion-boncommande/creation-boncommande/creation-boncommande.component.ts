import {ChangeDetectionStrategy, ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {FlowableService} from "../../gestion-credit/services-demande/flowable.service";
import {DomSanitizer, SafeResourceUrl} from "@angular/platform-browser";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {MAT_MOMENT_DATE_FORMATS, MomentDateAdapter} from "@angular/material-moment-adapter";
import {CheckBoxSelectionService} from "@syncfusion/ej2-angular-dropdowns";
import {DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE} from "@angular/material/core";
import {GestionBoncommandeService} from "../gestion-boncommande.service";

@Component({
  selector: 'app-creation-boncommande',
  templateUrl: './creation-boncommande.component.html',
  styleUrls: ['./creation-boncommande.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
  providers: [GestionBoncommandeService,
    {provide: DateAdapter, useClass: MomentDateAdapter, deps: [MAT_DATE_LOCALE]},
    {provide: MAT_DATE_FORMATS, useValue: MAT_MOMENT_DATE_FORMATS},CheckBoxSelectionService],
})

export class CreationBoncommandeComponent implements OnInit {

  identifiant:any;
  reference:any;


  bonCommande:any = {
    id: null,
    identifiant: null,
    prix: null,
    quantite: null,
    listeArticles: null,
    status: null,
    tel: null,
    date: null,
    nomDemandeur: null,
    application: ['pmm']
  };




  demandes: any[] = [];

  mode: any = false;


  constructor(private router:Router, private activatedroute:ActivatedRoute, private modal: NgbModal, private gestionBoncommandeService: GestionBoncommandeService, private flowableService: FlowableService, private changeDetectorRef: ChangeDetectorRef, public _DomSanitizationService: DomSanitizer) {
    if (this.activatedroute.snapshot.params['id'] != null) {
      this.gestionBoncommandeService.getDemandesbyId(this.activatedroute.snapshot.params['id']).subscribe((data:any)=>{
        this.bonCommande = data;
      });
    }
    this.getAllDemandes();

  }

  ngOnInit() {

  }
  getAllDemandes(){

    this.gestionBoncommandeService.getMesDemandesList('').subscribe((data:any[]) => {

      this.demandes = data;
      console.log('All DEMANDES **** ', this.demandes);
      return data;
    });
  }


  public createBonCommande(bonCommande):void {
    console.log("values", bonCommande);
    this.gestionBoncommandeService.createBonCommande(bonCommande)
      .then(
        (Response:any) => {
          console.log(Response);
          this.router.navigate(['/pages/fournisseurs']);}  ,

        err => {
          // this.toster.error("Erreur")
        })


  }



  identifier(){

    this.gestionBoncommandeService.getReference(3).subscribe((data:any)=>{

      this.bonCommande.identifiant=data.nomenclature+"_"+ new Date().getFullYear()+ "_"+ data.compteur;
    })
  }
}

