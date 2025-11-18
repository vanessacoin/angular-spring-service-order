package com.vanessa.services;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.layout.Style;

public class PdfTheme {
  public static final DeviceRgb PRIMARY      = hex("#336940");
  public static final DeviceRgb ON_PRIMARY   = hex("#FFFFFF");
  public static final DeviceRgb SURFACE      = hex("#F6FBF3");
  public static final DeviceRgb ON_SURFACE   = hex("#181D18");
  public static final DeviceRgb OUTLINE      = hex("#717970");

  public final PdfFont regular;
  public final PdfFont bold;

  public final Style h1;
  public final Style h2;
  public final Style body;
  public final Style tableHeader;
  public final Style chip;

  public PdfTheme() {
    try {
      this.regular = PdfFontFactory.createFont(StandardFonts.HELVETICA);
      this.bold    = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
    } catch (Exception e) {
      throw new RuntimeException("Falha ao carregar fontes", e);
    }

    this.h1 = new Style().setFont(bold).setFontSize(18).setFontColor(PRIMARY);
    this.h2 = new Style().setFont(bold).setFontSize(12).setFontColor(PRIMARY);
    this.body = new Style().setFont(regular).setFontSize(10).setFontColor(ON_SURFACE);

    this.tableHeader = new Style()
      .setFont(bold).setFontSize(10)
      .setFontColor(ON_PRIMARY)
      .setBackgroundColor(PRIMARY);

    this.chip = new Style()
      .setFont(bold).setFontSize(9)
      .setFontColor(PRIMARY);
  }

  public static DeviceRgb hex(String hex) {
    String s = hex.replace("#", "");
    int r = Integer.valueOf(s.substring(0, 2), 16);
    int g = Integer.valueOf(s.substring(2, 4), 16);
    int b = Integer.valueOf(s.substring(4, 6), 16);
    return new DeviceRgb(r, g, b);
  }
}
