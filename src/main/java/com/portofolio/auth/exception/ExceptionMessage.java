package com.portofolio.auth.exception;

import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.portofolio.auth.util.Constants;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ExceptionMessage implements Serializable {
	
	@Serial
    private static final long serialVersionUID = 1L;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.ZONED_DATE_TIME_FORMAT)
    private final ZonedDateTime timestamp;

    private final int status;
    private final String error;
    private final String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, String> fieldErrors;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> generalErrors;
    
}