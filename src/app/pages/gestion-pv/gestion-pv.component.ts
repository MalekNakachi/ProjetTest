import { Component, OnInit } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {GestionPvService} from "./gestion-pv.service";

@Component({
  selector: 'app-gestion-pv',
  templateUrl: './gestion-pv.component.html',
  styleUrls: ['./gestion-pv.component.css'],
  providers:[ GestionPvService ],

})
export class GestionPvComponent implements OnInit {



  pvs: any[] = [];

  constructor(httpClient: HttpClient, private router:Router, private gestionPvService: GestionPvService) {

    this.getAllPvs();

  }

  ngOnInit(): void {}

  getAllPvs() {
    this.gestionPvService.getAllPvs().subscribe((pvs:any[])=>{
      console.log("list des offres",pvs)
      this.pvs=pvs;
    });
  }

  createPvs() {
    this.router.navigate(["/pages/pv"]);
  }

  openForm(data) {
    console.log("Offre ::> ",data)
    this.router.navigate(["/pages/pv" + '/' + data.id]);
  }
}
