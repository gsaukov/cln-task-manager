import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CounterTaskMenuComponent } from './counter-task-menu.component';

describe('CounterTaskMenuComponent', () => {
  let component: CounterTaskMenuComponent;
  let fixture: ComponentFixture<CounterTaskMenuComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CounterTaskMenuComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CounterTaskMenuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
