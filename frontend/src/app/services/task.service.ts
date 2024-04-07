import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Task } from '../type';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class TaskService {
  constructor(private http: HttpClient) {}

  createTask(task: {
    name: string;
    description: string;
    deadline: string;
    categoryId: number;
  }): Observable<Task> {
    return this.http.post<Task>(`${environment.baseURL}/tasks`, task);
  }

  updateTask(
    taskId: number,
    task: {
      name: string;
      description: string;
      deadline: string;
      categoryId: number;
    }
  ): Observable<Task> {
    return this.http.put<Task>(`${environment.baseURL}/tasks/${taskId}`, task);
  }

  deleteTask(taskId: number): Observable<void> {
    return this.http.delete<void>(`${environment.baseURL}/tasks/${taskId}`);
  }
}
