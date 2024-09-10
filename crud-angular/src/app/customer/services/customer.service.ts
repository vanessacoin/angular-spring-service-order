import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { Customer } from './../model/customer';

@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  private readonly API = 'http://localhost:8080/api/customers';

  constructor(private httpClient: HttpClient) { }

  list(): Observable<Customer[]> {
    return this.httpClient.get<Customer[]>(this.API);
  }
}
