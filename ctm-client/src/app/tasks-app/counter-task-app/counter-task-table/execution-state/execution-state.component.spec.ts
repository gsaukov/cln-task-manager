import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ExecutionStateComponent } from './execution-state.component';

describe('ExecutionStateComponent', () => {
  let component: ExecutionStateComponent;
  let fixture: ComponentFixture<ExecutionStateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ExecutionStateComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ExecutionStateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
