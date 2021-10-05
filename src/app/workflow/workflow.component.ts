import {AfterViewInit, Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Router} from '@angular/router';
import {FlowableService} from "./flowable.service";
import {EnvService} from "../../env.service";
@Component({
    selector: 'app-workflow',
    templateUrl: './workflow.component.html',
    styleUrls: ['./workflow.component.scss']
})
export class WorkflowComponent implements OnInit {
    @Input() cle;
    @Input() iddossier:any;
    @Input() variable:any;
    @Input() taskid:any;
    @Input() processInstanceId: any;
    @Input() description: any;
    @Input() authentifier: any;
    @Input() comment:any;
    @Input() params:any;
    @Input() processName:any;
    @Output() messageEvent = new EventEmitter<any>();
    @Output() decisionEvent = new EventEmitter<any>();

    decisionTab: any[] = [];
    historicTasks: any[] = [];
    message: string = "Hello!"
    consultjournal: any;
    constructor(private flowable: FlowableService, private router: Router) {
        console.log("Bienvenu Workflow");

    }

    ngOnInit() {


    }



}
