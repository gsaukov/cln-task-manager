import {
  Component,
  EventEmitter,
  Input,
  OnInit,
  Output,
  ViewChild
} from '@angular/core';
import {CounterTask} from "../service/rest/model/counterTask";
import {CounterTaskRestService} from "../service/rest/counter-task-rest.service";
import {MatSort} from "@angular/material/sort";
import {MatTableDataSource} from "@angular/material/table";

@Component({
  selector: 'app-counter-task-table',
  templateUrl: './counter-task-table.component.html',
  styleUrls: ['./counter-task-table.component.scss']
})
export class CounterTaskTableComponent implements OnInit {
  @Output() actionMade = new EventEmitter()
  @ViewChild(MatSort) sort: MatSort;

  dataSource: MatTableDataSource<CounterTask>
  columnsToDisplay = ['taskId', 'name', 'x', 'y', 'status', 'updateAt', 'actions']

  constructor(private taskService: CounterTaskRestService) {
  }

  ngOnInit(): void {
  }

  @Input() set data(data: CounterTask[]) {
    this.dataSource = new MatTableDataSource(data);
    this.dataSource.sort = this.sort;
  }

  executeCounterTask(id: string) {
    this.taskService.executeCounterTask(id).subscribe(
      async () => {
      //execution status will be updated in database asynchronously so we need wait
      await this.delay(1000)
      this.emitActionMade()
    })
  }

  stopCounterTask(id: string) {
    this.taskService.stopCounterTask(id).subscribe(
      () => {
        this.emitActionMade()
      }
    )
  }

  deleteCounterTask(id: string) {
    this.taskService.deleteCounterTask(id).subscribe(() => {
      this.emitActionMade()
    })
  }

  emitActionMade() {
    this.actionMade.emit();
  }

  delay(ms: number) {
    return new Promise( resolve => setTimeout(resolve, ms) );
  }
}
