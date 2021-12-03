import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CounterTaskAppComponent } from './counter-task-app.component';

describe('CounterTaskAppComponent', () => {
  let component: CounterTaskAppComponent;
  let fixture: ComponentFixture<CounterTaskAppComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CounterTaskAppComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CounterTaskAppComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
