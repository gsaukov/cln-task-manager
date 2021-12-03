import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProjectGenerationTaskAppComponent } from './project-generation-task-app.component';

describe('ProjectGenerationTaskAppComponent', () => {
  let component: ProjectGenerationTaskAppComponent;
  let fixture: ComponentFixture<ProjectGenerationTaskAppComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProjectGenerationTaskAppComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ProjectGenerationTaskAppComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
