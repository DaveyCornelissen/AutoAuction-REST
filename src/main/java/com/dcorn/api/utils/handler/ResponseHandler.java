package com.dcorn.api.utils.handler;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Date;

@ResponseStatus
@Getter
@Setter
@NoArgsConstructor
public class ResponseHandler {

    private Date timestamp;
    private int status;
    private Object message;
    private String path;

    public ResponseHandler(HttpStatus status, Object message) {
        this.timestamp = new Date();
        this.status = status.value();
        this.message = message;
        this.path = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getRequestURL().toString();
    }
}
