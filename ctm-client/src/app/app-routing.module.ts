import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {SiteLayoutComponent} from "./navigation/site-layout/site-layout.component";
import {CounterTaskAppComponent} from "./tasks-app/counter-task-app/counter-task-app.component";
import {ProjectGenerationTaskAppComponent} from "./tasks-app/project-generation-task-app/project-generation-task-app.component";
import {PageNotFoundComponent} from "./navigation/page-not-found/page-not-found.component";

const routes: Routes = [
  {path: '', component: SiteLayoutComponent, children: [
    { path: '', redirectTo: '/counter-task', pathMatch: 'full' },
    { path: 'counter-task', component: CounterTaskAppComponent},
    { path: 'project-generation-task', component: ProjectGenerationTaskAppComponent},
  ]},
  {path: '**', component: PageNotFoundComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { relativeLinkResolution: 'legacy' })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
