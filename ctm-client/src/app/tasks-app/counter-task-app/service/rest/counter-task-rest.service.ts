import {Injectable} from "@angular/core";
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable} from "rxjs";
import {CounterTask} from "./model/counterTask";

@Injectable({
  providedIn: 'root'
})
export class CounterTaskRestService {
  constructor(private http: HttpClient) {}

  getDealHistory(): Observable<CounterTask> {
    return this.http.get<CounterTask>('api/v1/countertasks')
  }

  fetch(params: any = {}): Observable<CounterTask> {
    return this.http.get<CounterTask> ('api/v1/countertasks', {
      params: new HttpParams({
        fromObject: params
      })
    })
  }
}
