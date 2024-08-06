import { Component, Input } from '@angular/core';

export interface UsedItem {
  id: number;
  description: string;
  quantity: number;
  price: number;
  amount: number;
}

@Component({
  selector: 'app-used-items',
  standalone: true,
  imports: [],
  templateUrl: './used-items.component.html',
  styleUrl: './used-items.component.scss'
})
export class UsedItemsComponent {
  @Input() requestedItems: UsedItem[] = [];
  @Input() idItemCounter: number = 1;
  displayedColumnsItems: string[] = ['id', 'description', 'quantity', 'price', 'amount', 'actions'];
}
