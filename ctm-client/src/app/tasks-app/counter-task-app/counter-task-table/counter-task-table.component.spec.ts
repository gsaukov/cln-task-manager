import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CounterTaskTableComponent } from './counter-task-table.component';

describe('CounterTaskTableComponent', () => {
  let component: CounterTaskTableComponent;
  let fixture: ComponentFixture<CounterTaskTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CounterTaskTableComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CounterTaskTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
