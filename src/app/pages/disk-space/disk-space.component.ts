import {Component, Input, ViewEncapsulation} from '@angular/core';

@Component({
  selector: 'app-disk-space',
  templateUrl: './disk-space.component.html',
  encapsulation: ViewEncapsulation.None
})
export class DiskSpaceComponent {

    @Input() public courrierDataSource;
 
  public showLegend = false;
  public gradient = true;
  public colorScheme = {
    domain: ['#2F3E9E', '#D22E2E', '#378D3B']
  }; 
  public showLabels = true;
  public explodeSlices = true;
  public doughnut = false;

  constructor() {

  }

  ngOnInit(){

  }
  
  public onSelect(event) {
    console.log(event);
  }




}
