package com.rw.kgl.bkdf.userprofilemanagement.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rw.kgl.bkdf.userprofilemanagement.constant.SecurityConstant;
import com.rw.kgl.bkdf.userprofilemanagement.domain.HttpResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
  @Override
  public void handle(
      HttpServletRequest request,
      HttpServletResponse response,
      AccessDeniedException accessDeniedException)
      throws IOException, ServletException {
    HttpResponse httpResponse =
        new HttpResponse(
            UNAUTHORIZED.value(),
            UNAUTHORIZED,
            UNAUTHORIZED.getReasonPhrase().toUpperCase(),
            SecurityConstant.ACCESS_DENIED_MESSAGE,new Date());
    response.setContentType(APPLICATION_JSON_VALUE);
    response.setStatus(UNAUTHORIZED.value());
    OutputStream outputStream = response.getOutputStream();
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.writeValue(outputStream, httpResponse);
    outputStream.flush();
  }
}
