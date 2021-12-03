import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CounterTaskPaginationComponent } from './counter-task-pagination.component';

describe('CounterTaskPaginationComponent', () => {
  let component: CounterTaskPaginationComponent;
  let fixture: ComponentFixture<CounterTaskPaginationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CounterTaskPaginationComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CounterTaskPaginationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
