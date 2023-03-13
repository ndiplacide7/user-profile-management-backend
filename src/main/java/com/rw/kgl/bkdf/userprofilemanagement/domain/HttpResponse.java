package com.rw.kgl.bkdf.userprofilemanagement.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HttpResponse {
    private int httpStatusCode; // 200,201,404,500 etc..
    private HttpStatus httpStatus; //OK , CREATED, etc....
    private String reason; //HttpStatus.Series.INFORMATIONAL ,HttpStatus.Series.SUCCESSFUL , etc....
    private String message; // my customs' message as a Developer
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "MM-dd-yyyy hh:mm:ss",timezone = "Africa/Harare")
    private Date timeStamp;
}
