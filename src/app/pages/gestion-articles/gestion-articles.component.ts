import { Component, OnInit } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {GestionArticlesService} from "./gestion-articles.service";

@Component({
  selector: 'app-gestion-articles',
  templateUrl: './gestion-articles.component.html',
  styleUrls: ['./gestion-articles.component.css'],
  providers:[ GestionArticlesService ],

})
export class GestionArticlesComponent implements OnInit {


  articles: any[] = [];

  constructor(httpClient: HttpClient, private router:Router, private gestionArticleService: GestionArticlesService) {

    this.getAllArticles();

  }

  ngOnInit(): void {}

  getAllArticles() {
    this.gestionArticleService.getAllarticles().subscribe((listarticles:any[])=>{
      console.log("list des articles",listarticles)
      this.articles=listarticles;
    });
  }

  createArticle() {
    this.router.navigate(["/pages/article"]);
  }

  openForm(data) {
    console.log("Article ::> ",data)
    this.router.navigate(["/pages/article" + '/' + data.id]);
  }
}
