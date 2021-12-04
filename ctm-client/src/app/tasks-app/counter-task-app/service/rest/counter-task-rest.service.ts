import {Injectable} from "@angular/core";
import {HttpClient} from '@angular/common/http';
import {Observable} from "rxjs";
import {CounterTask, CounterTaskRequest} from "./model/counterTask";

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

  submitNewCounterTask(task: CounterTaskRequest): Observable<CounterTask> {
    return this.http.post<CounterTask>('/api/v1/countertasks/', task)
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
