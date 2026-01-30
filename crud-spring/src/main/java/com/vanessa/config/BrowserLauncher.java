package com.vanessa.config;

import java.awt.Desktop;
import java.net.URI;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class BrowserLauncher {

  private final Environment env;

  public BrowserLauncher(Environment env) {
    this.env = env;
  }

  @EventListener(ApplicationReadyEvent.class)
  public void openBrowser() {
    try {
      if (!Desktop.isDesktopSupported()) return;

      String port = env.getProperty("local.server.port");
      if (port == null) return;

      Desktop.getDesktop().browse(new URI("http://localhost:" + port + "/"));
    } catch (Exception ignored) {
      // se falhar, só não abre automaticamente
    }
  }
}
