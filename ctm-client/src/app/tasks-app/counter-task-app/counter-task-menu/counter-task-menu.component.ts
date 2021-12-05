import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {AbstractControl, FormControl, FormGroup, ValidatorFn, Validators} from "@angular/forms";
import {CounterTaskRestService} from "../service/rest/counter-task-rest.service";
import {CounterTaskSubmitRequest} from "../service/rest/model/counterTask";

@Component({
  selector: 'app-counter-task-menu',
  templateUrl: './counter-task-menu.component.html',
  styleUrls: ['./counter-task-menu.component.scss']
})
export class CounterTaskMenuComponent implements OnInit {
  @Output() submittedNew = new EventEmitter()

  form: FormGroup

  constructor(private counterTaskService: CounterTaskRestService) { }

  ngOnInit(): void {
    this.form = new FormGroup({
      name: new FormControl(null, [Validators.required]),
      x: new FormControl(null, [Validators.required ]),
      y: new FormControl(null, [Validators.required, this.isXGreaterOrEqualY()])
    })
  }

  onSubmit() {
    this.revalidateForm()
    if(this.form.valid){
      const request:CounterTaskSubmitRequest = {
        name: this.form.value.name,
        x: this.form.value.x,
        y: this.form.value.y,
      };
      this.counterTaskService.submitNewCounterTask(request).subscribe(() => {
        this.emitSubmittedNew()
      });
    }
  }

  emitSubmittedNew() {
    this.submittedNew.emit();
  }

  // i dont like this validation below but I dont know how to make it any better
  isXGreaterOrEqualY(): ValidatorFn {
    return (control: AbstractControl): {[key: string]: any} | null => {
      let isXGreaterOrEqualY
      if(this.form){
        let x = this.form.get('x')
        let y = this.form.get('y')
        if(x&&y) {
          isXGreaterOrEqualY = x.value >= y.value
        }
      }
      return isXGreaterOrEqualY ? {'XGreaterOrEqualY': {value: control.value}} : null
    }
  }

  revalidateForm(){
    Object.keys(this.form.controls).forEach(field => {
      const control = this.form.get(field)
      if(control){
        control.updateValueAndValidity()
      }
    });
  }

}
