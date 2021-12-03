import {Injectable} from "@angular/core";
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable} from "rxjs";
import {CounterTask, CounterTasks} from "./model/counterTask";

@Injectable({
  providedIn: 'root'
})
export class CounterTaskRestService {
  constructor(private http: HttpClient) {}

  getAll(): Observable<CounterTasks> {
    return this.http.get<CounterTasks>('api/v1/countertasks')
  }

  fetch(params: any = {}): Observable<CounterTasks> {
    return this.http.get<CounterTasks> ('api/v1/countertasks', {
      params: new HttpParams({
        fromObject: params
      })
    })
  }
}
