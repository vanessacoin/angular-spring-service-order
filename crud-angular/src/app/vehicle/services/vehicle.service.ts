import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { Vehicle } from './../model/vehicle';

@Injectable({
  providedIn: 'root'
})
export class VehicleService {

  private readonly API = 'api/vehicles';

  constructor(private httpClient: HttpClient) { }

  list(): Observable<Vehicle[]>{
    return this.httpClient.get<Vehicle[]>(this.API);
  }
}
