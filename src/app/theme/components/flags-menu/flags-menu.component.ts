import { Component, OnInit, ViewEncapsulation } from '@angular/core';

@Component({
  selector: 'app-flags-menu',
  templateUrl: './flags-menu.component.html',
  styleUrls: ['./flags-menu.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class FlagsMenuComponent implements OnInit {
ar:any="ar";
fr:any="fr";
en:any="en";
  constructor() { }

  ngOnInit() {
  }

  languechoisir(language: any) {
    console.log("Language"+language)
    sessionStorage.setItem("langue",language);
    parent.document.location.reload();
    
  }
}
