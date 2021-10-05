import { Component, OnInit } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {GestionFournisseurService} from "./gestion-fournisseur.service";

@Component({
  selector: 'app-gestion-fournisseur',
  templateUrl: './gestion-fournisseur.component.html',
  styleUrls: ['./gestion-fournisseur.component.css'],
  providers:[ GestionFournisseurService ],

})
export class GestionFournisseursComponent implements OnInit {


  fournisseurs: any[] = [];

  constructor(httpClient: HttpClient, private router:Router, private gestionFournisseurService: GestionFournisseurService) {

    this.getAllFournisseurs();

  }

  ngOnInit(): void {}

  getAllFournisseurs() {
    this.gestionFournisseurService.getAllfournisseurs().subscribe((fournisseurs:any[])=>{
      console.log("list des articles",fournisseurs)
      this.fournisseurs =fournisseurs;
    });
  }

  createFournisseur() {
    this.router.navigate(["/pages/fournisseur"]);
  }

  openForm(data) {
    console.log("Fournisseur ::> ",data)
    this.router.navigate(["/pages/fournisseur" + '/' + data.id]);
  }
}
