import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {AppComponent} from './app.component';
import {SiteLayoutComponent} from "./navigation/site-layout/site-layout.component";
import {PageNotFoundComponent} from "./navigation/page-not-found/page-not-found.component";
import {CounterDirective} from "./navigation/page-not-found/counter.directive";
import {HeaderComponent} from "./navigation/header/header.component";
import {SidenavListComponent} from "./navigation/sidenav-list/sidenav-list.component";
import {TasksAppComponent} from './tasks-app/tasks-app.component';
import {AppRoutingModule} from "./app-routing.module";
import {CounterTaskAppComponent } from './tasks-app/counter-task-app/counter-task-app.component';
import {ProjectGenerationTaskAppComponent} from './tasks-app/project-generation-task-app/project-generation-task-app.component';
import {MatImportsModule} from "./mat-imports.module";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {CounterTaskMenuComponent} from './tasks-app/counter-task-app/counter-task-menu/counter-task-menu.component';
import {CounterTaskTableComponent} from './tasks-app/counter-task-app/counter-task-table/counter-task-table.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {DatePipe} from "@angular/common";
import {ReactiveFormsModule} from "@angular/forms";
import {ExecutionStateComponent} from './tasks-app/counter-task-app/counter-task-table/execution-state/execution-state.component';
import {HeaderInterceptor} from "./header-interceptor";

@NgModule({
  declarations: [
    AppComponent,
    SiteLayoutComponent,
    PageNotFoundComponent,
    CounterDirective,
    HeaderComponent,
    SidenavListComponent,
    TasksAppComponent,
    CounterTaskAppComponent,
    ProjectGenerationTaskAppComponent,
    CounterTaskMenuComponent,
    CounterTaskTableComponent,
    ExecutionStateComponent,
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    AppRoutingModule,
    MatImportsModule
  ],
  providers: [
    DatePipe,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HeaderInterceptor,
      multi: true,
    }],
  bootstrap: [AppComponent]
})
export class AppModule { }
