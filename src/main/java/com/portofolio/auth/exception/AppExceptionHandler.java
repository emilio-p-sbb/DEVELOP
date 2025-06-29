package com.portofolio.auth.exception;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class AppExceptionHandler {
	
	@ExceptionHandler(value = {
            MethodArgumentNotValidException.class,
            HttpMessageNotReadableException.class
    })
	public <T extends BindException> ResponseEntity<ExceptionMessage> handleValidationException(final T e) {
	    log.info("ApiExceptionHandler controller, handle validation exception");

	    final HttpStatus badRequest = HttpStatus.BAD_REQUEST;

	    Map<String, String> fieldErrors = e.getBindingResult()
	            .getFieldErrors()
	            .stream()
	            .collect(Collectors.toMap(
	                    FieldError::getField,
	                    FieldError::getDefaultMessage,
	                    (msg1, msg2) -> msg1 // ambil yang pertama jika duplikat
	            ));

	    // Ambil semua pesan error (bukan per field) untuk generalErrors
	    List<String> generalErrors = e.getBindingResult()
	            .getAllErrors()
	            .stream()
	            .map(error -> error.getDefaultMessage())
	            .distinct()
	            .collect(Collectors.toList());

	    ExceptionMessage response = ExceptionMessage.builder()
	            .timestamp(ZonedDateTime.now(ZoneId.systemDefault()))
	            .status(badRequest.value())
	            .error(badRequest.getReasonPhrase())
	            .message("Validasi gagal") // Bisa diganti dengan generalErrors.get(0) jika kamu mau
	            .fieldErrors(fieldErrors)
	            .generalErrors(generalErrors)
	            .build();

	    return new ResponseEntity<>(response, badRequest);
	}
	
	@ExceptionHandler({AccessDeniedException.class, BadCredentialsException.class})
	public ResponseEntity<ExceptionMessage> handleAccessDeniedException(Exception ex) {
	    log.error("Access denied: {}", ex.getMessage());

	    final HttpStatus forbidden = HttpStatus.FORBIDDEN;

	    ExceptionMessage response = ExceptionMessage.builder()
	            .timestamp(ZonedDateTime.now(ZoneId.systemDefault()))
	            .status(forbidden.value())
	            .error(forbidden.getReasonPhrase())
	            .message("Access denied")
	            .generalErrors(List.of(ex.getMessage()))
	            .build();

	    return ResponseEntity.status(forbidden).body(response);
	}
	
    @ExceptionHandler(AppException.class)
    public ResponseEntity<ExceptionMessage> appExceptionHandler(AppException e, WebRequest request) {
    	final HttpStatus badRequest = HttpStatus.BAD_REQUEST;
    	ExceptionMessage response = ExceptionMessage.builder()
	            .timestamp(ZonedDateTime.now(ZoneId.systemDefault()))
	            .status(e.getStatus().value())
	            .error(badRequest.getReasonPhrase())
	            .message(e.getMessage()) // Bisa diganti dengan generalErrors.get(0) jika kamu mau
	            .generalErrors(new ArrayList<>())
	            .build();
        return new ResponseEntity<>(response, e.getStatus());
    }
    
    @ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<ExceptionMessage> handleAuthenticationException(AuthenticationException ex) {
	    log.error("Authentication failed: {}", ex.getMessage());

	    final HttpStatus unauthorized = HttpStatus.UNAUTHORIZED;

	    ExceptionMessage response = ExceptionMessage.builder()
	            .timestamp(ZonedDateTime.now(ZoneId.systemDefault()))
	            .status(unauthorized.value())
	            .error(unauthorized.getReasonPhrase())
//	            .message("Autentikasi gagal")
	            .message(ex.getMessage())
	            .generalErrors(List.of(ex.getMessage()))
	            .build();

	    return ResponseEntity.status(unauthorized).body(response);
	}
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionMessage> globalExceptionHandler(Exception e, WebRequest request) {
        ExceptionMessage response = ExceptionMessage.builder()
	            .timestamp(ZonedDateTime.now(ZoneId.systemDefault()))
	            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
	            .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
	            .message(e.getMessage())
	            .generalErrors(new ArrayList<>())
	            .build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @ExceptionHandler(value = {
//	        UserNotFoundException.class,
//	        RoleNotFoundException.class,
//	        PasswordNotFoundException.class,
//	        EmailOrUsernameNotFoundException.class,
    		AlreadyExistsException.class,
	        ResourceNotFoundException.class
	})
	public <T extends RuntimeException> ResponseEntity<ExceptionMessage> handleApiRequestException(final T e) {
	    log.info("ApiExceptionHandler controller, handle API request");

	    final HttpStatus badRequest = HttpStatus.BAD_REQUEST;

	    ExceptionMessage response = ExceptionMessage.builder()
	            .timestamp(ZonedDateTime.now(ZoneId.systemDefault()))
	            .status(badRequest.value())
	            .error(badRequest.getReasonPhrase())
//	            .message("Request not valid")
	            .message(e.getMessage())
	            .generalErrors(List.of(e.getMessage()))
	            .build();

	    return new ResponseEntity<>(response, badRequest);
	}
}