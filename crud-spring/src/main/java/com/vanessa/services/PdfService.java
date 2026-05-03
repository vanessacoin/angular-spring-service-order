package com.vanessa.services;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.vanessa.entities.RequestedService;
import com.vanessa.entities.ServiceOrder;
import com.vanessa.entities.UsedItems;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Service
public class PdfService {

    @Value("${workshop.footer.text:}")
    private String footerText;

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
            Document document = new Document(pdfDoc, PageSize.A4);
            document.setMargins(32, 36, 55, 36);

            PdfFont regular = PdfFontFactory.createFont(StandardFonts.HELVETICA);
            PdfFont bold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);

            addHeader(document, order, regular, bold);
            addCustomerInfo(document, order, regular, bold);
            addVehicleInfo(document, order, regular, bold);
            addRequestedServices(document, order, regular, bold);
            addUsedItems(document, order, regular, bold);
            addTotals(document, order, bold);
            addFooter(document, pdfDoc, regular);

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException("Erro ao gerar PDF da ordem", e);
        }

        return baos.toByteArray();
    }

    private void addHeader(Document document, ServiceOrder order, PdfFont regular, PdfFont bold) {
        Table header = new Table(UnitValue.createPercentArray(new float[] { 2, 3 }))
                .useAllAvailableWidth()
                .setMarginBottom(24);

        Cell logoCell = new Cell()
                .setBorder(Border.NO_BORDER)
                .setPadding(0);

        Image logo = loadLogo();

        if (logo != null) {
            logo.scaleToFit(180, 110);
            logo.setHorizontalAlignment(HorizontalAlignment.LEFT);
            logoCell.add(logo);
        }

        Cell titleCell = new Cell()
                .setBorder(Border.NO_BORDER)
                .setTextAlignment(TextAlignment.RIGHT)
                .setPadding(0);

        titleCell.add(new Paragraph("Ordem de Serviço")
                .setFont(bold)
                .setFontSize(22)
                .setFontColor(TEXT)
                .setMarginBottom(4));

        titleCell.add(new Paragraph("Ficha de atendimento")
                .setFont(regular)
                .setFontSize(10)
                .setFontColor(MUTED));

        titleCell.add(new Paragraph("Data: " + formatDate(order))
                .setFont(regular)
                .setFontSize(10)
                .setFontColor(MUTED)
                .setMarginTop(2));

        header.addCell(logoCell);
        header.addCell(titleCell);

        document.add(header);
    }

    private void addCustomerInfo(Document document, ServiceOrder order, PdfFont regular, PdfFont bold) {
        document.add(sectionTitle("Dados do cliente", bold));

        Table table = infoTable();

        addInfoRow(table, "Nome", order.getCustomerName(), "CPF", order.getCustomerCpf(), regular, bold);
        addInfoRow(table, "Telefone", order.getCustomerPhone(), "E-mail", order.getCustomerEmail(), regular, bold);

        document.add(table);
    }

    private void addVehicleInfo(Document document, ServiceOrder order, PdfFont regular, PdfFont bold) {
        document.add(sectionTitle("Dados do veículo", bold));

        Table table = infoTable();

        addInfoRow(table, "Marca", order.getVehicleBrand(), "Modelo", order.getVehicleModel(), regular, bold);
        addInfoRow(table, "Placa", order.getVehiclePlate(), "KM", order.getVehicleKm(), regular, bold);
        addInfoRow(table, "Ano", order.getVehicleYear(), "Cor", order.getVehicleColor(), regular, bold);

        document.add(table);
    }

    private void addRequestedServices(Document document, ServiceOrder order, PdfFont regular, PdfFont bold) {
        document.add(sectionTitle("Serviços solicitados", bold));

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
        document.add(sectionTitle("Itens utilizados", bold));

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
                table.addCell(valueCell(item.getTotalQuantity(), regular));
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

    private void addFooter(Document document, PdfDocument pdfDoc, PdfFont regular) {
    if (footerText == null || footerText.trim().isEmpty()) {
        return;
    }

    int lastPage = pdfDoc.getNumberOfPages();

    float left = 36;
    float bottom = 20;
    float width = PageSize.A4.getWidth() - 72;

    document.add(new Paragraph(footerText)
            .setFont(regular)
            .setFontSize(8)
            .setFontColor(MUTED)
            .setTextAlignment(TextAlignment.CENTER)
            .setFixedPosition(lastPage, left, bottom, width));
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

    private Image loadLogo() {
        try {
            ClassPathResource resource = new ClassPathResource("static/images/logo.png");

            if (!resource.exists()) {
                System.out.println("Logo não encontrada em: static/images/logo.png");
                return null;
            }

            ImageData imageData = ImageDataFactory.create(resource.getInputStream().readAllBytes());
            return new Image(imageData);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}