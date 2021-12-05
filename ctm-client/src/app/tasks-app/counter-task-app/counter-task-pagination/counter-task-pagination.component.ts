import {Component, Input, OnInit} from '@angular/core';
import {CounterTask} from "../service/rest/model/counterTask";

@Component({
  selector: 'app-counter-task-pagination',
  templateUrl: './counter-task-pagination.component.html',
  styleUrls: ['./counter-task-pagination.component.scss']
})
export class CounterTaskPaginationComponent implements OnInit {
  @Input() data: CounterTask[]

  constructor() { }

  ngOnInit(): void {
  }

}
