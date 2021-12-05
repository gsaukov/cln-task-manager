import {ChangeDetectorRef, Component, Input, OnDestroy, OnInit} from '@angular/core';
import {CounterTaskRestService} from "../../service/rest/counter-task-rest.service";
import {Subscription} from "rxjs";
import {CounterTask} from "../../service/rest/model/counterTask";

@Component({
  selector: 'app-execution-state',
  templateUrl: './execution-state.component.html',
  styleUrls: ['./execution-state.component.scss']
})
export class ExecutionStateComponent implements OnInit, OnDestroy {

  @Input() counterTask: CounterTask
  sseEventsConnection: Subscription
  isConsuming: boolean = false
  progress: number = 0

  constructor(private counterTaskService: CounterTaskRestService,
              private changeDetectorRef: ChangeDetectorRef) { }

  ngOnInit(): void {
  }

  executionBoxClass(percent: number): string {
    return this.progress >= percent ? 'execution-box-active' : 'execution-box-inactive'
  }

  subscribeToExecutionEvents() {
    this.isConsuming = true;
    this.sseEventsConnection = this.counterTaskService.getTaskExecutionState(this.counterTask.id).subscribe(
      state => {
        this.progress = Math.round(((state.x - this.counterTask.x)/(state.y - this.counterTask.x))*100)
        console.log(state)
        this.changeDetectorRef.detectChanges()
      },
      error => {
        this.isConsuming = false;
        this.changeDetectorRef.detectChanges()
      }, () => {
        this.isConsuming = false;
        this.changeDetectorRef.detectChanges()
      }
    )
  }

  ngOnDestroy(): void {
    if(this.sseEventsConnection) {
      this.sseEventsConnection.unsubscribe()
    }
  }


  isRunning():boolean {
    return this.counterTask.status != 'RUNNING'
  }
}
