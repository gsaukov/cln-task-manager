import {Injectable} from "@angular/core";
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable} from "rxjs";
import {CounterTask} from "./model/counterTask";

@Injectable({
  providedIn: 'root'
})
export class CounterTaskRestService {
  constructor(private http: HttpClient) {}

  getCounterTasks(): Observable<CounterTask[]> {
    return this.http.get<CounterTask[]>('/api/v1/countertasks/')
  }

  getCounterTask(taskId: string): Observable<CounterTask> {
    return this.http.get<CounterTask>(`/api/v1/countertasks/${taskId}`)
  }

  executeCounterTask(taskId: string) {
    return this.http.post(`/api/v1/countertasks/${taskId}/execute`, null)
  }

  stopCounterTask(taskId: string) {
    return this.http.post(`/api/v1/countertasks/${taskId}/stop`, null)
  }

  deleteCounterTask(taskId: string) {
    return this.http.delete(`/api/v1/countertasks/${taskId}`)
  }

}
