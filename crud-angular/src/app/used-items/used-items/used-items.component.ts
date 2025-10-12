import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCard, MatCardContent } from '@angular/material/card';
import { MatLabel } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
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
  totalQuantity: number;
  unitPrice: number;
  amount: number;
}

@Component({
  selector: 'app-used-items',
  standalone: true,
  imports: [
    MatTable,
    MatCard,
    MatIconModule,
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

    const totalQuantity = totalQuantityString ? parseFloat(totalQuantityString.replace(',', '.')) : 0;
    const unitPrice = unitPriceString ? parseFloat(unitPriceString.replace(',', '.')) : 0;

    if (description && totalQuantity > 0 && unitPrice > 0) {
      this.usedItemsService.calculateAmount(totalQuantity, unitPrice). subscribe(
        (calculatedAmount) => {
          this.addUsedItems({
            id: this.idItemCounter++,
            description,
            totalQuantity: totalQuantity,
            unitPrice: unitPrice,
            amount: calculatedAmount
          });
        },
        (error) => console.error('Erro ao calcular o valor: ', error)
      );
    }
  }

  onEditUsedItems(item: UsedItem) {
    const description = prompt('Editar Descrição do Item:', item.description);
    const totalQuantityString = prompt('Editar Quantidade Total:', item.totalQuantity.toString());
    const unitPriceString = prompt('Editar Preço Unitário:', item.unitPrice.toString());

    const totalQuantity = totalQuantityString ? parseFloat(totalQuantityString.replace(',', '.')) : item.totalQuantity;
    const unitPrice = unitPriceString ? parseFloat(unitPriceString.replace(',', '.')) : item.unitPrice;

    if (description && totalQuantity > 0 && unitPrice > 0) {
      this.usedItemsService.calculateAmount(totalQuantity, unitPrice).subscribe(
        (calculatedAmount) => {
          item.description = description;
          item.totalQuantity = totalQuantity;
          item.unitPrice = unitPrice;
          item.amount = calculatedAmount;
          this.updateTableData();
        },
        (error) => console.error('Erro ao calcular o valor:', error)
      );
    }
  }

  onDeleteUsedItems(id: number) {
    if (confirm('Tem certeza que deseja excluir o item ${id}?')) {
      this.usedItemsService.deleteUsedItem(id).subscribe({
        next: () => {
          this.usedItems = this.usedItems.filter(item => item.id !== id);
          this.reorganizeIds();
          this.updateTableData();
        },
        error: (err) => console.error('Erro ao deletar item:', err),
      });
    }
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
