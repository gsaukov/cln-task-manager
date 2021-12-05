import {Component, OnInit, ViewChild} from '@angular/core';
import {CounterTask} from "./service/rest/model/counterTask";
import {CounterTaskRestService} from "./service/rest/counter-task-rest.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-counter-task-app',
  templateUrl: './counter-task-app.component.html',
  styleUrls: ['./counter-task-app.component.scss']
})
export class CounterTaskAppComponent implements OnInit {
  counterTasks: CounterTask []

  // @ViewChild(CounterTaskPaginationComponent, { static: true }) paginationComponent:CounterTaskPaginationComponent;

  constructor(private counterTaskRestService: CounterTaskRestService,
              private router: Router,
              private route: ActivatedRoute ) {
    this.refreshCounterTasksTable()
  }

  ngOnInit() {
  }

  refreshCounterTasksTable() {
    this.route.queryParams.subscribe(() => {
      this.counterTaskRestService.getCounterTasks().subscribe( response => {
        //actually fixing my bug from the server where I return date as yyyy-MM-dd HH:mm:ss and correct for JS is yyyy-MM-ddTHH:mm:ss
        response.forEach(r => r.updateAt = new Date(r.updateAt.toString().replace(' ', 'T')))
        this.counterTasks = response
      })
    })
  }

}
