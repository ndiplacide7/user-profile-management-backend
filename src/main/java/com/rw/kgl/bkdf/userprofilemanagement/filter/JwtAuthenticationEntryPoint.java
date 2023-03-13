package com.rw.kgl.bkdf.userprofilemanagement.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rw.kgl.bkdf.userprofilemanagement.constant.SecurityConstant;
import com.rw.kgl.bkdf.userprofilemanagement.domain.HttpResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class JwtAuthenticationEntryPoint extends Http403ForbiddenEntryPoint {
  @Override
  public void commence(
      HttpServletRequest request, HttpServletResponse response, AuthenticationException arg2)
      throws IOException {
    //     logger.debug("Pre-authenticated entry point called. Rejecting access");
    //     response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
    HttpResponse httpResponse =
        new HttpResponse(
            FORBIDDEN.value(),
            FORBIDDEN,
            FORBIDDEN.getReasonPhrase().toUpperCase(),
            SecurityConstant.FORBIDDEN_MESSAGE,
            new Date());
    response.setContentType(APPLICATION_JSON_VALUE);
    response.setStatus(FORBIDDEN.value());
    OutputStream outputStream = response.getOutputStream();
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.writeValue(outputStream, httpResponse);
    outputStream.flush();
  }
}
