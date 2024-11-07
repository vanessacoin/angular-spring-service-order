import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UsedItemsService {
  private readonly apiUrl = 'api/used-items';

  constructor(private readonly httpClient: HttpClient) { }

  calculateAmount(totalQuantity: number, unitPrice: number): Observable<number> {
    const params = new HttpParams()
      .set('totalQuantity', totalQuantity.toString())
      .set('unitPrice', unitPrice.toString());

    return this.httpClient.post<number>(`${this.apiUrl}/calculateAmount`, null, { params });
  }

  deleteUsedItem(id: number): Observable<void> {
    return this.httpClient.delete<void>(`/api/used-items/${id}`);
  }
}
