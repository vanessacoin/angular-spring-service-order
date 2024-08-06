import { Component, Input, OnInit } from '@angular/core';
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


export interface RequestedService {
  id: number;
  description: string;
}

@Component({
  selector: 'app-requested-service',
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
    MatButtonModule
],
  templateUrl: './requested-service.component.html',
  styleUrl: './requested-service.component.scss'
})

export class RequestedServiceComponent implements OnInit{
  @Input() requestedServices: RequestedService[] = [];
  @Input() idRequestedServiceCounter: number = 1;
  displayedColumnsRequestedServices: string[] = ['id', 'description', 'actions'];

  dataSourceRequestedServices = new MatTableDataSource<RequestedService>(this.requestedServices);

  ngOnInit(): void {
    console.log("RequestedServiceComponent initialized");
    this.updateTableData();
  }

  onAddRequestedService() {
    const description = prompt('Descrição do Serviço:');
    if (description) {
      this.addRequestedService({ id: this.idRequestedServiceCounter++, description });
    }
  }

  onEditRequestedService(service: RequestedService) {
    const description = prompt('Editar Descrição do Serviço: ', service.description);
    if (description !== null) {
      service.description = description;
      this.updateTableData();
    }
  }

  onDeleteRequestedService(id: number) {
    this.requestedServices = this.requestedServices.filter(service => service.id !== id);
    this.reorganizeIds();
    this.updateTableData();
  }

  private addRequestedService(service: RequestedService): void {
    this.requestedServices.push(service);
    this.updateTableData();
  }

  private reorganizeIds(): void {
    this.requestedServices.forEach((service, index) => {
      service.id = index + 1;
    });
    this.idRequestedServiceCounter = this.requestedServices.length + 1;
  }

  private updateTableData(): void {
    this.dataSourceRequestedServices.data = [...this.requestedServices];
  }
}
