import {Component, Input, OnInit} from "@angular/core";

@Component({
    selector: 'Doc_viewer',
    template: `

        <div >
            <ngx-doc-viewer [url]="pdfSrcc"  [viewer]="visionneuse" style="width:100%;height:75vh;"></ngx-doc-viewer>

        </div>

    `,

})
export class DocViewerComponent implements OnInit{
@Input() pdfSrcc;
@Input() visionneuse;
    constructor(){

    }
    ngOnInit() {


    }
}
