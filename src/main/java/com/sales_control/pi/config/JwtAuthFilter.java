package com.sales_control.pi.config;

import static java.util.Objects.isNull;

import com.sales_control.pi.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

  private final JwtUtil jwtUtil;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    var path = request.getServletPath();

    if (path.startsWith("/auth")
        || path.endsWith(".html")
        || path.startsWith("/css")
        || path.startsWith("/js")
        || path.startsWith("/images")) {
      filterChain.doFilter(request, response);
      return;
    }

    var authHeader = request.getHeader("Authorization");
    if (isNull(authHeader) || !authHeader.startsWith("Bearer ")) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }

    var token = authHeader.substring(7);
    if (!jwtUtil.validateToken(token)) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }

    var username = jwtUtil.extractUsername(token);
    var role = jwtUtil.extractRole(token);

    var auth = new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList());
    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    SecurityContextHolder.getContext().setAuthentication(auth);

    filterChain.doFilter(request, response);
  }
}
