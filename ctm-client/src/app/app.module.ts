import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import {SiteLayoutComponent} from "./navigation/site-layout/site-layout.component";
import {HeaderComponent} from "./navigation/header/header.component";
import {SidenavListComponent} from "./navigation/sidenav-list/sidenav-list.component";
import { TasksAppComponent } from './tasks-app/tasks-app.component';
import {AppRoutingModule} from "./app-routing.module";
import { CounterTaskAppComponent } from './tasks-app/counter-task-app/counter-task-app.component';
import { ProjectGenerationTaskAppComponent } from './tasks-app/project-generation-task-app/project-generation-task-app.component';
import {MatImportsModule} from "./mat-imports.module";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";

@NgModule({
  declarations: [
    AppComponent,
    SiteLayoutComponent,
    HeaderComponent,
    SidenavListComponent,
    TasksAppComponent,
    CounterTaskAppComponent,
    ProjectGenerationTaskAppComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    MatImportsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
