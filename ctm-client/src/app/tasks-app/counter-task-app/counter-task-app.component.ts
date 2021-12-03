import {Component, OnInit, ViewChild} from '@angular/core';
import {CounterTask, CounterTasks} from "./service/rest/model/counterTask";
import {CounterTaskRestService} from "./service/rest/counter-task-rest.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-counter-task-app',
  templateUrl: './counter-task-app.component.html',
  styleUrls: ['./counter-task-app.component.scss']
})
export class CounterTaskAppComponent implements OnInit {
  response: CounterTasks
  counterTasks: CounterTask []

  // @ViewChild(CounterTaskPaginationComponent, { static: true }) paginationComponent:CounterTaskPaginationComponent;

  constructor(private counterTaskRestService: CounterTaskRestService,
              private router: Router,
              private route: ActivatedRoute ) {
    this.route.queryParams.subscribe(() => {
      this.counterTaskRestService.fetch().subscribe( response => {
        this.response = response
        this.counterTasks = response.tasks
        // this.startingDealNumber = filterAndPage.page * filterAndPage.size
        // let page: DealPage = {}
        // page = {page: filterAndPage.page, size: filterAndPage.size}
        // this.paginationComponent.emitPaginatorData(page, response.totalElements)
      })
    })
  }

  ngOnInit() {
  }

}
