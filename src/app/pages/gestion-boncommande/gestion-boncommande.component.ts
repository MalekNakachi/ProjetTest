import { Component, OnInit } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {GestionBoncommandeService} from "./gestion-boncommande.service";

@Component({
  selector: 'app-gestion-boncommande',
  templateUrl: './gestion-boncommande.component.html',
  styleUrls: ['./gestion-boncommande.component.css'],
  providers:[ GestionBoncommandeService ],

})
export class GestionBoncommandeComponent implements OnInit {


  bonCommandes: any[] = [];

  constructor(httpClient: HttpClient, private router:Router, private gestionBoncommandeService: GestionBoncommandeService) {

    this.getAllBonCommande();

  }

  ngOnInit(): void {}

  getAllBonCommande() {
    this.gestionBoncommandeService.getAllBonCommande().subscribe((fournisseurs:any[])=>{
      console.log("list des articles",fournisseurs)
      this.bonCommandes =fournisseurs;
    });
  }

  createFournisseur() {
    this.router.navigate(["/pages/bonCommande"]);
  }

  openForm(data) {
    console.log("BonCommande ::> ",data)
    this.router.navigate(["/pages/bonCommande" + '/' + data.id]);
  }
}
