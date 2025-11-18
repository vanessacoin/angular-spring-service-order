package com.vanessa.services;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;

public class HeaderFooterHandler implements IEventHandler {
  private final PdfTheme theme;
  private Image logoImage;

  public HeaderFooterHandler(PdfTheme theme) {
    this.theme = theme;
    try (InputStream is = new ClassPathResource("static/img/logo.png").getInputStream()) {
      this.logoImage = new Image(ImageDataFactory.create(is.readAllBytes())).scaleToFit(90, 32);
    } catch (Exception ignored) {}
  }

  @Override
  public void handleEvent(Event event) {
    PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
    PdfDocument pdf = docEvent.getDocument();
    Rectangle pageSize = docEvent.getPage().getPageSize();

    PdfCanvas headerCanvas = new PdfCanvas(docEvent.getPage().newContentStreamBefore(), docEvent.getPage().getResources(), pdf);
    headerCanvas.saveState()
      .setFillColor(PdfTheme.PRIMARY)
      .rectangle(pageSize.getLeft(), pageSize.getTop() - 40, pageSize.getWidth(), 40)
      .fill()
      .restoreState();

    Canvas header = new Canvas(headerCanvas, pdf, new Rectangle(pageSize.getLeft() + 36, pageSize.getTop() - 36, pageSize.getWidth() - 72, 28));
    header.setFont(theme.bold).setFontSize(12);
    // if (logoImage != null) {
    //   Image logo = new Image(logoImage.getImageData()).scaleToFit(90, 32);
    //   logo.setFixedPosition(pageSize.getLeft() + 36, pageSize.getTop() - 36);
    //   header.add(logo);
    // }
    header.add(new Paragraph("Ordem de Serviço").setFont(theme.bold).setFontSize(12).setFontColor(PdfTheme.ON_PRIMARY)
      .setMargin(0).setTextAlignment(com.itextpdf.layout.property.TextAlignment.CENTER));
    header.close();

    PdfCanvas footerCanvas = new PdfCanvas(docEvent.getPage().newContentStreamAfter(), docEvent.getPage().getResources(), pdf);
    Canvas footer = new Canvas(footerCanvas, pdf, new Rectangle(pageSize.getLeft() + 36, pageSize.getBottom() + 12, pageSize.getWidth() - 72, 24));
    int pageNum = pdf.getPageNumber(docEvent.getPage());
    footer.setFont(theme.regular).setFontSize(9);
    footer.add(new Paragraph("Página " + pageNum).setFontColor(PdfTheme.hex("#666666"))
      .setMargin(0).setTextAlignment(com.itextpdf.layout.property.TextAlignment.RIGHT));
    footer.close();
  }
}
