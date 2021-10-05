import { Component, OnInit } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {GestionOffresService} from "./gestion-offres.service";

@Component({
  selector: 'app-gestion-offres',
  templateUrl: './gestion-offres.component.html',
  styleUrls: ['./gestion-offres.component.css'],
  providers:[ GestionOffresService ],

})
export class GestionOffresComponent implements OnInit {


  offres: any[] = [];

  constructor(httpClient: HttpClient, private router:Router, private gestionOffresService: GestionOffresService) {

    this.getAllArticles();

  }

  ngOnInit(): void {}

  getAllArticles() {
    this.gestionOffresService.getAllOffres().subscribe((offres:any[])=>{
      console.log("list des offres",offres)
      this.offres=offres;
    });
  }

  createOffre() {
    this.router.navigate(["/pages/offre"]);
  }

  openForm(data) {
    console.log("Offre ::> ",data)
    this.router.navigate(["/pages/offre" + '/' + data.id]);
  }
}
