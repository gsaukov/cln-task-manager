import {Component, Input, OnInit} from '@angular/core';
import {CounterTask} from "../service/rest/model/counterTask";

@Component({
  selector: 'app-counter-task-table',
  templateUrl: './counter-task-table.component.html',
  styleUrls: ['./counter-task-table.component.scss']
})
export class CounterTaskTableComponent implements OnInit {
  @Input() dataSource: CounterTask[]
  columnsToDisplay = ['taskId', 'name', 'x', 'y', 'status', 'updateAt', 'actions']

  constructor() { }

  ngOnInit(): void {
  }

  deleteCounterTask(id:string) {

  }

  executeCounterTask(id:string) {

  }

  stopCounterTask(id:string) {
    
  }
}
