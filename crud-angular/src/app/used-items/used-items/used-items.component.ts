import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCard, MatCardContent } from '@angular/material/card';
import { MatLabel } from '@angular/material/form-field';
import { MatIcon } from '@angular/material/icon';
import {
  MatCell,
  MatHeaderCell,
  MatHeaderRow,
  MatRow,
  MatTable,
  MatTableDataSource,
  MatTableModule,
} from '@angular/material/table';

import { CommonModule } from '@angular/common';
import { UsedItemsService } from '../services/used-items.service';

export interface UsedItem {
  id: number;
  description: string;
  total_quantity: number;
  unit_price: number;
  amount: number;
}

@Component({
  selector: 'app-used-items',
  standalone: true,
  imports: [
    MatTable,
    MatCard,
    MatIcon,
    MatLabel,
    MatCardContent,
    MatCell,
    MatHeaderCell,
    MatHeaderRow,
    MatRow,
    MatTableModule,
    MatButtonModule,
    CommonModule],
  templateUrl: './used-items.component.html',
  styleUrl: './used-items.component.scss',
  providers: [UsedItemsService]
})
export class UsedItemsComponent implements OnInit {
  @Input() usedItems: UsedItem[] = [];
  @Input() idItemCounter: number = 1;
  @Output() usedItemsChange = new EventEmitter<UsedItem[]>();
  @Output() totalAmountChange = new EventEmitter<number>();

  displayedColumnsUsedItems: string[] = ['id', 'description', 'total_quantity', 'unit_price', 'amount', 'actions'];
  dataSourceUsedItems = new MatTableDataSource<UsedItem>([]);

  constructor(private readonly usedItemsService: UsedItemsService) {}

  ngOnInit(): void {
    console.log("UsedItemsComponent initialized");
    this.updateTableData();
  }

  onAddUsedItems() {
    const description = prompt('Descrição do Item:');
    const totalQuantityString = prompt('Quantidade Total:');
    const unitPriceString = prompt('Preço Unitário:');

    const totalQuantity = totalQuantityString ? parseInt(totalQuantityString) : 0;
    const unitPrice = unitPriceString ? parseFloat(unitPriceString) : 0;

    if (description && totalQuantity > 0 && unitPrice > 0) {
      this.usedItemsService.calculateAmount(totalQuantity, unitPrice). subscribe(
        (calculatedAmount) => {
          this.addUsedItems({
            id: this.idItemCounter++,
            description,
            total_quantity: totalQuantity,
            unit_price: unitPrice,
            amount: calculatedAmount
          });
        },
        (error) => console.error('Erro ao calcular o valor: ', error)
      );
    }
  }

  onEditUsedItems(item: UsedItem) {
    const description = prompt('Editar Descrição do Item:', item.description);
    const totalQuantityString = prompt('Editar Quantidade Total:', item.total_quantity.toString());
    const unitPriceString = prompt('Editar Preço Unitário:', item.unit_price.toString());

    const totalQuantity = totalQuantityString ? parseFloat(totalQuantityString) : item.total_quantity;
    const unitPrice = unitPriceString ? parseFloat(unitPriceString) : item.unit_price;

    if (description && totalQuantity > 0 && unitPrice > 0) {
      this.usedItemsService.calculateAmount(totalQuantity, unitPrice).subscribe(
        (calculatedAmount) => {
          item.description = description;
          item.total_quantity = totalQuantity;
          item.unit_price = unitPrice;
          item.amount = calculatedAmount;
          this.updateTableData();
        },
        (error) => console.error('Erro ao calcular o valor:', error)
      );
    }
  }

  onDeleteUsedItems(id: number) {
    this.usedItems = this.usedItems.filter(service => service.id !== id);
    this.reorganizeIds();
    this.updateTableData();
  }

  private addUsedItems(service: UsedItem): void {
    this.usedItems.push(service);
    this.updateTableData();
  }

  private reorganizeIds(): void {
    this.usedItems.forEach((service, index) => {
      service.id = index + 1;
    });
    this.idItemCounter = this.usedItems.length + 1;
  }

  private updateTableData(): void {
    this.dataSourceUsedItems.data = this.usedItems;
    this.usedItemsChange.emit(this.usedItems);
    this.emitTotalAmount();
  }

  protected emitTotalAmount(): void {
    const totalAmount = this.usedItems.reduce((acc, item) => acc + item.amount, 0);
    this.totalAmountChange.emit(Number(totalAmount.toFixed(2)));
  }
}
