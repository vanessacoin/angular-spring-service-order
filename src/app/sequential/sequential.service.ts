import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SequentialService {

  constructor() { }

  // Método para obter o valor sequencial
  getSequentialValue(): number {
    // Adicionar a lógica para buscar o valor sequencial de uma fonte de dados
    // Por exemplo, você pode chamar um serviço HTTP para buscar o valor do backend
    // Neste exemplo, estou apenas retornando um valor fixo para fins de demonstração
    return 1000;
  }
}
