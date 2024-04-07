import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Category } from '../type';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class CategoryService {
  constructor(private http: HttpClient) {}

  getCategories(): Observable<Category[]> {
    return this.http.get<Category[]>(`${environment.baseURL}/categories`);
  }

  getCategory(categoryId: number): Observable<Category> {
    return this.http.get<Category>(
      `${environment.baseURL}/categories/${categoryId}`
    );
  }

  createCategory(category: {
    name: string;
    description: string;
  }): Observable<Category> {
    return this.http.post<Category>(
      `${environment.baseURL}/categories`,
      category
    );
  }

  updateCategory(
    categoryId: number,
    category: {
      name: string;
      description: string;
    }
  ): Observable<Category> {
    return this.http.put<Category>(
      `${environment.baseURL}/categories/${categoryId}`,
      category
    );
  }

  deleteCategory(categoryId: number): Observable<void> {
    return this.http.delete<void>(
      `${environment.baseURL}/categories/${categoryId}`
    );
  }
}
