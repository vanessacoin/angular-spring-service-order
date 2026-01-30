package com.vanessa.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SpaForwardFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String path = request.getRequestURI();

    boolean isGet = "GET".equalsIgnoreCase(request.getMethod());
    boolean isApi = path.startsWith("/api");
    boolean hasExtension = path.contains(".");

   if (isGet && !isApi && !hasExtension) {
  request.getRequestDispatcher("/index.html").forward(request, response);
  return;
}


    filterChain.doFilter(request, response);
  }
}
