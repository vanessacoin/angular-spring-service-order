package com.vanessa.services;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.property.VerticalAlignment;
import com.vanessa.entities.RequestedService;
import com.vanessa.entities.ServiceOrder;
import com.vanessa.entities.UsedItems;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Service
public class PdfService {

    @Value("${pdf.footer.line1:}")
    private String footerLine1;

    @Value("${pdf.footer.line2:}")
    private String footerLine2;

    private static final Color GREEN = new DeviceRgb(5, 164, 80);
    private static final Color MUTED = new DeviceRgb(124, 124, 124);
    private static final Color BORDER = new DeviceRgb(220, 225, 222);
    private static final Color TEXT = new DeviceRgb(13, 13, 13);
    private static final Color LIGHT_BACKGROUND = new DeviceRgb(232, 255, 242);
    private static final Locale BRAZIL = new Locale("pt", "BR");

    public byte[] generateOrderPdf(ServiceOrder order) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdfDoc = new PdfDocument(writer);
            pdfDoc.getDocumentInfo().setTitle("ordem-servico-" + order.getId());

            Document document = new Document(pdfDoc, PageSize.A4);
            document.setMargins(110, 36, 95, 36);

            PdfFont regular = PdfFontFactory.createFont(StandardFonts.HELVETICA);
            PdfFont bold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);

            String orderNumber = buildOrderNumber(order);

            addCustomerInfo(document, order, regular, bold);
            addVehicleInfo(document, order, regular, bold);
            addRequestedServices(document, order, regular, bold);
            addUsedItems(document, order, regular, bold);
            addTotals(document, order, bold);

            addHeaderAndFooterToAllPages(pdfDoc, order, regular, bold, orderNumber);

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException("Erro ao gerar PDF da ordem", e);
        }

        return baos.toByteArray();
    }

    private void addHeaderAndFooterToAllPages(
            PdfDocument pdfDoc,
            ServiceOrder order,
            PdfFont regular,
            PdfFont bold,
            String orderNumber) {

        int totalPages = pdfDoc.getNumberOfPages();

        for (int pageNumber = 1; pageNumber <= totalPages; pageNumber++) {
            Rectangle pageSize = pdfDoc.getPage(pageNumber).getPageSize();

            PdfCanvas pdfCanvas = new PdfCanvas(
                    pdfDoc.getPage(pageNumber).newContentStreamAfter(),
                    pdfDoc.getPage(pageNumber).getResources(),
                    pdfDoc);

            Canvas canvas = new Canvas(pdfCanvas, pdfDoc, pageSize);

            float left = 36;
            float right = pageSize.getRight() - 36;
            float center = pageSize.getWidth() / 2;
            float top = pageSize.getTop();
            float bottom = pageSize.getBottom();

            Image logo = loadLogo();

            if (logo != null) {
                logo.scaleToFit(145, 70);
                logo.setFixedPosition(pageNumber, left, top - 85);
                canvas.add(logo);
            }

            canvas.showTextAligned(
                    new Paragraph("Ordem de Serviço")
                            .setFont(bold)
                            .setFontSize(18)
                            .setFontColor(TEXT),
                    right,
                    top - 38,
                    TextAlignment.RIGHT,
                    VerticalAlignment.MIDDLE);

            canvas.showTextAligned(
                    new Paragraph("Ficha de atendimento")
                            .setFont(regular)
                            .setFontSize(10)
                            .setFontColor(MUTED),
                    right,
                    top - 56,
                    TextAlignment.RIGHT,
                    VerticalAlignment.MIDDLE);

            canvas.showTextAligned(
                    new Paragraph("Data: " + formatDate(order))
                            .setFont(regular)
                            .setFontSize(10)
                            .setFontColor(MUTED),
                    right,
                    top - 72,
                    TextAlignment.RIGHT,
                    VerticalAlignment.MIDDLE);

            pdfCanvas
                    .setStrokeColor(BORDER)
                    .setLineWidth(0.5f)
                    .moveTo(left, top - 100)
                    .lineTo(right, top - 100)
                    .stroke();

            pdfCanvas
                    .setStrokeColor(BORDER)
                    .setLineWidth(0.5f)
                    .moveTo(left, bottom + 86)
                    .lineTo(right, bottom + 86)
                    .stroke();

            canvas.showTextAligned(
                    new Paragraph("Número controle: " + orderNumber)
                            .setFont(regular)
                            .setFontSize(8)
                            .setFontColor(MUTED),
                    left,
                    bottom + 68,
                    TextAlignment.LEFT,
                    VerticalAlignment.MIDDLE);

            canvas.showTextAligned(
                    new Paragraph("Página " + pageNumber + "/" + totalPages)
                            .setFont(regular)
                            .setFontSize(8)
                            .setFontColor(MUTED),
                    right,
                    bottom + 68,
                    TextAlignment.RIGHT,
                    VerticalAlignment.MIDDLE);

            if (footerLine1 != null && !footerLine1.trim().isEmpty()) {
                canvas.showTextAligned(
                        new Paragraph(footerLine1)
                                .setFont(regular)
                                .setFontSize(8)
                                .setFontColor(MUTED),
                        center,
                        bottom + 34,
                        TextAlignment.CENTER,
                        VerticalAlignment.MIDDLE);
            }

            if (footerLine2 != null && !footerLine2.trim().isEmpty()) {
                canvas.showTextAligned(
                        new Paragraph(footerLine2)
                                .setFont(regular)
                                .setFontSize(8)
                                .setFontColor(MUTED),
                        center,
                        bottom + 22,
                        TextAlignment.CENTER,
                        VerticalAlignment.MIDDLE);
            }

            canvas.close();
        }
    }

    private void addCustomerInfo(Document document, ServiceOrder order, PdfFont regular, PdfFont bold) {
        document.add(sectionTitle("Cliente", bold));

        Table table = infoTable();

        addInfoRow(table, "Nome", order.getCustomerName(), "CPF", order.getCustomerCpf(), regular, bold);
        addInfoRow(table, "Telefone", order.getCustomerPhone(), "E-mail", order.getCustomerEmail(), regular, bold);

        document.add(table);
    }

    private void addVehicleInfo(Document document, ServiceOrder order, PdfFont regular, PdfFont bold) {
        document.add(sectionTitle("Veículo", bold));

        Table table = infoTable();

        addInfoRow(table, "Marca", order.getVehicleBrand(), "Modelo", order.getVehicleModel(), regular, bold);
        addInfoRow(table, "Placa", order.getVehiclePlate(), "KM", order.getVehicleKm(), regular, bold);
        addInfoRow(table, "Ano", order.getVehicleYear(), "Cor", order.getVehicleColor(), regular, bold);

        document.add(table);
    }

    private void addRequestedServices(Document document, ServiceOrder order, PdfFont regular, PdfFont bold) {
        document.add(sectionTitle("Serviços", bold));

        Table table = new Table(UnitValue.createPercentArray(new float[] { 1 }))
                .useAllAvailableWidth();

        table.addHeaderCell(headerCell("Descrição", bold));

        if (order.getRequestedServices() == null || order.getRequestedServices().isEmpty()) {
            table.addCell(valueCell("", regular));
        } else {
            for (RequestedService service : order.getRequestedServices()) {
                table.addCell(valueCell(service.getDescription(), regular));
            }
        }

        document.add(table);
    }

    private void addUsedItems(Document document, ServiceOrder order, PdfFont regular, PdfFont bold) {
        document.add(sectionTitle("Itens/Peças", bold));

        Table table = new Table(UnitValue.createPercentArray(new float[] { 4, 2, 2, 2 }))
                .useAllAvailableWidth();

        table.addHeaderCell(headerCell("Descrição", bold));
        table.addHeaderCell(headerCell("Quantidade", bold));
        table.addHeaderCell(headerCell("Preço unitário", bold));
        table.addHeaderCell(headerCell("Total", bold));

        if (order.getUsedItems() == null || order.getUsedItems().isEmpty()) {
            table.addCell(valueCell("", regular));
            table.addCell(valueCell("", regular));
            table.addCell(valueCell("", regular));
            table.addCell(valueCell("", regular));
        } else {
            for (UsedItems item : order.getUsedItems()) {
                table.addCell(valueCell(item.getDescription(), regular));
                table.addCell(valueCell(quantity(item.getTotalQuantity()), regular));
                table.addCell(valueCell(money(item.getUnitPrice()), regular));
                table.addCell(valueCell(money(item.getAmount()), regular));
            }
        }

        document.add(table);
    }

    private void addTotals(Document document, ServiceOrder order, PdfFont bold) {
        Table laborTable = new Table(UnitValue.createPercentArray(new float[] { 2, 2 }))
                .setWidth(UnitValue.createPercentValue(45))
                .setHorizontalAlignment(HorizontalAlignment.RIGHT)
                .setMarginTop(18);

        laborTable.addCell(summaryLabelCell("Mão de obra", bold));
        laborTable.addCell(summaryValueCell(money(order.getLaborCost()), bold));

        document.add(laborTable);

        Table totalTable = new Table(UnitValue.createPercentArray(new float[] { 2, 2 }))
                .setWidth(UnitValue.createPercentValue(45))
                .setHorizontalAlignment(HorizontalAlignment.RIGHT)
                .setMarginTop(8);

        totalTable.addCell(summaryLabelCell("Total", bold));
        totalTable.addCell(summaryValueCell(money(order.getTotalOrder()), bold)
                .setFontColor(GREEN)
                .setFontSize(13));

        document.add(totalTable);
    }

    private Paragraph sectionTitle(String title, PdfFont bold) {
        return new Paragraph(title)
                .setFont(bold)
                .setFontSize(12)
                .setFontColor(GREEN)
                .setMarginTop(14)
                .setMarginBottom(6);
    }

    private Table infoTable() {
        return new Table(UnitValue.createPercentArray(new float[] { 1, 2, 1, 2 }))
                .useAllAvailableWidth();
    }

    private void addInfoRow(
            Table table,
            String firstLabel,
            Object firstValue,
            String secondLabel,
            Object secondValue,
            PdfFont regular,
            PdfFont bold) {
        table.addCell(labelCell(firstLabel, bold));
        table.addCell(valueCell(firstValue, regular));
        table.addCell(labelCell(secondLabel, bold));
        table.addCell(valueCell(secondValue, regular));
    }

    private Cell labelCell(String text, PdfFont bold) {
        return new Cell()
                .add(new Paragraph(valueOrBlank(text))
                        .setFont(bold)
                        .setFontSize(9)
                        .setFontColor(TEXT))
                .setBackgroundColor(LIGHT_BACKGROUND)
                .setBorder(new SolidBorder(BORDER, 0.5f))
                .setPadding(7);
    }

    private Cell valueCell(Object value, PdfFont regular) {
        return new Cell()
                .add(new Paragraph(valueOrBlank(value))
                        .setFont(regular)
                        .setFontSize(10)
                        .setFontColor(TEXT))
                .setBorder(new SolidBorder(BORDER, 0.5f))
                .setPadding(7);
    }

    private Cell headerCell(String text, PdfFont bold) {
        return new Cell()
                .add(new Paragraph(text)
                        .setFont(bold)
                        .setFontSize(10)
                        .setFontColor(TEXT))
                .setBackgroundColor(LIGHT_BACKGROUND)
                .setBorder(new SolidBorder(BORDER, 0.5f))
                .setPadding(7);
    }

    private Cell summaryLabelCell(String text, PdfFont bold) {
        return new Cell()
                .add(new Paragraph(text)
                        .setFont(bold)
                        .setFontSize(10)
                        .setFontColor(TEXT))
                .setBackgroundColor(LIGHT_BACKGROUND)
                .setBorder(new SolidBorder(BORDER, 0.5f))
                .setPadding(8);
    }

    private Cell summaryValueCell(String text, PdfFont bold) {
        return new Cell()
                .add(new Paragraph(text)
                        .setFont(bold)
                        .setFontSize(10)
                        .setFontColor(TEXT)
                        .setTextAlignment(TextAlignment.RIGHT))
                .setBorder(new SolidBorder(BORDER, 0.5f))
                .setPadding(8);
    }

    private String valueOrBlank(Object value) {
        if (value == null) {
            return "";
        }

        String text = String.valueOf(value).trim();

        if (text.equalsIgnoreCase("null")) {
            return "";
        }

        return text;
    }

    private String formatDate(ServiceOrder order) {
        if (order.getOrderDate() == null) {
            return "";
        }

        return DateTimeFormatter
                .ofPattern("dd/MM/yyyy")
                .withZone(ZoneId.systemDefault())
                .format(order.getOrderDate());
    }

    private String money(Object value) {
        if (value == null) {
            return "";
        }

        BigDecimal amount;

        if (value instanceof BigDecimal) {
            amount = (BigDecimal) value;
        } else if (value instanceof Number) {
            amount = BigDecimal.valueOf(((Number) value).doubleValue());
        } else {
            String text = valueOrBlank(value).replace(",", ".");

            if (text.isEmpty()) {
                return "";
            }

            try {
                amount = new BigDecimal(text);
            } catch (NumberFormatException e) {
                return valueOrBlank(value);
            }
        }

        NumberFormat formatter = NumberFormat.getCurrencyInstance(BRAZIL);
        return formatter.format(amount);
    }

    private String quantity(Object value) {
        if (value == null) {
            return "";
        }

        BigDecimal quantity;

        if (value instanceof BigDecimal) {
            quantity = (BigDecimal) value;
        } else if (value instanceof Number) {
            quantity = BigDecimal.valueOf(((Number) value).doubleValue());
        } else {
            String text = valueOrBlank(value).replace(",", ".");

            if (text.isEmpty()) {
                return "";
            }

            try {
                quantity = new BigDecimal(text);
            } catch (NumberFormatException e) {
                return valueOrBlank(value);
            }
        }

        NumberFormat formatter = NumberFormat.getNumberInstance(BRAZIL);
        formatter.setMinimumFractionDigits(0);
        formatter.setMaximumFractionDigits(3);

        return formatter.format(quantity);
    }

    private Image loadLogo() {
        try {
            ClassPathResource resource = new ClassPathResource("pdf-assets/logo.png");

            if (!resource.exists()) {
                System.out.println("Logo não encontrada em: pdf-assets/logo.png");
                return null;
            }

            ImageData imageData = ImageDataFactory.create(resource.getInputStream().readAllBytes());
            return new Image(imageData);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String buildOrderNumber(ServiceOrder order) {
        String date = LocalDate.now(ZoneId.of("America/Sao_Paulo"))
                .format(DateTimeFormatter.BASIC_ISO_DATE);

        return "OS-" + date + "-" + String.format("%06d", order.getId());
    }
}