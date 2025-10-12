package com.vanessa.services;

import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.vanessa.entities.RequestedService;
import com.vanessa.entities.ServiceOrder;
import com.vanessa.entities.UsedItems;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Service
public class PdfService {

    public byte[] generateOrderPdf(ServiceOrder order) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        System.out.println("Serviços recebidos: " + order.getRequestedServices());
        System.out.println("Itens utilizados: " + order.getUsedItems());

        try {
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            document.add(new Paragraph("Ordem de Serviço")
                    .setBold()
                    .setFontSize(18)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(20));

            document.add(new Paragraph("Dados do Cliente " + order.getCustomerId()).setBold());
            document.add(new Paragraph("Nome: " + order.getCustomerName()));
            document.add(new Paragraph("CPF: " + order.getCustomerCpf()));
            document.add(new Paragraph("Telefone: " + order.getCustomerPhone()));
            document.add(new Paragraph("Email: " + order.getCustomerEmail()));

            document.add(new Paragraph("\n"));

            document.add(new Paragraph("Dados do Veículo " + order.getVehicleId()).setBold());
            document.add(new Paragraph("Marca: " + order.getVehicleBrand()));
            document.add(new Paragraph("Modelo: " + order.getVehicleModel()));
            document.add(new Paragraph("Placa: " + order.getVehiclePlate()));
            document.add(new Paragraph("KM: " + order.getVehicleKm()));
            document.add(new Paragraph("Ano: " + order.getVehicleYear()));
            document.add(new Paragraph("Cor: " + order.getVehicleColor()));

            document.add(new Paragraph("\n"));

            if (order.getOrderDate() != null) {
                String formattedDate = DateTimeFormatter
                        .ofPattern("dd/MM/yyyy HH:mm")
                        .withZone(ZoneId.systemDefault())
                        .format(order.getOrderDate());
                document.add(new Paragraph("Data e Hora da Geracao da Ordem: " + formattedDate));
            }

            document.add(new Paragraph("\n"));

            if (order.getRequestedServices() != null && !order.getRequestedServices().isEmpty()) {
                document.add(new Paragraph("Serviços Solicitados").setBold());

                Table serviceTable = new Table(UnitValue.createPercentArray(new float[]{1, 4, 2}))
                        .useAllAvailableWidth();
                serviceTable.addHeaderCell("Descrição");

                for (RequestedService rs : order.getRequestedServices()) {
                    serviceTable.addCell(rs.getDescription());
                }

                document.add(serviceTable);
                document.add(new Paragraph("\n"));
            }
            if (order.getUsedItems() != null && !order.getUsedItems().isEmpty()) {
                document.add(new Paragraph("Itens Utilizados").setBold());

                Table itemsTable = new Table(UnitValue.createPercentArray(new float[]{4, 2, 2, 2}))
                        .useAllAvailableWidth();
                itemsTable.addHeaderCell("Descrição");
                itemsTable.addHeaderCell("Quantidade");
                itemsTable.addHeaderCell("Preço Unitário");
                itemsTable.addHeaderCell("Total");

                for (UsedItems item : order.getUsedItems()) {
                    itemsTable.addCell(item.getDescription());
                    itemsTable.addCell(String.valueOf(item.getTotalQuantity()));
                    itemsTable.addCell("R$ " + item.getUnitPrice());
                    itemsTable.addCell("R$ " + item.getAmount());
                }

                document.add(itemsTable);
            }

            document.add(new Paragraph("\n"));

            document.add(new Paragraph("Mao de Obra: " + order.getLaborCost()).setBold());

            document.close();

        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException("Erro ao gerar PDF da ordem", e);
        }

        return baos.toByteArray();
    }
}
