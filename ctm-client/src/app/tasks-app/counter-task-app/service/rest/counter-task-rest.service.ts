import {Injectable} from "@angular/core";
import {HttpClient} from '@angular/common/http';
import {Observable} from "rxjs";
import {CounterTask, CounterTaskExecutionState, CounterTaskRequest} from "./model/counterTask";

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

  getTaskExecutionState(taskId: string): Observable<CounterTaskExecutionState> {
    return new Observable<CounterTaskExecutionState>(observer => {
      const eventSource = new EventSource(`/api/v1/countertasks/${taskId}/taskstate`)
      eventSource.onmessage = x => {
        observer.next(JSON.parse(x.data))
      }
      eventSource.onerror = x => observer.error(x)
      return () => {
        eventSource.close()
      }
    })
  }

}
