package com.dcorn.api.utils.handler;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@ResponseStatus
public class AuthenticationErrorHandler implements AuthenticationEntryPoint {

    private ResponseHandler responseHandler;

    public AuthenticationErrorHandler() {
        this.responseHandler = new ResponseHandler();
    }

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        responseHandler.setStatus(HttpStatus.UNAUTHORIZED.value());
        responseHandler.setMessage(e.getMessage());
        responseHandler.setTimestamp(new Date());
        responseHandler.setPath(httpServletRequest.getRequestURL().toString());

        httpServletResponse.setContentType("application/json");
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setStatus(responseHandler.getStatus());
        httpServletResponse.getWriter().write(new Gson().toJson(responseHandler));
    }
}
