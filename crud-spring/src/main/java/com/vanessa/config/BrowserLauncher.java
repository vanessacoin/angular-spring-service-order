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
      String port = env.getProperty("local.server.port");

      if (port == null) {
        port = env.getProperty("server.port", "8080");
      }

      String url = "http://localhost:" + port + "/";

      System.out.println("Abrindo navegador em: " + url);

      String os = System.getProperty("os.name").toLowerCase();

      if (os.contains("win")) {
        new ProcessBuilder("cmd", "/c", "start", "", url).start();
        return;
      }

      if (Desktop.isDesktopSupported()) {
        Desktop.getDesktop().browse(new URI(url));
      }
    } catch (Exception e) {
      System.out.println("Não foi possível abrir o navegador automaticamente.");
      e.printStackTrace();
    }
  }
}