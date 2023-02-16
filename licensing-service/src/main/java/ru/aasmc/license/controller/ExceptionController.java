package ru.aasmc.license.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.aasmc.license.model.utils.ErrorMessage;
import ru.aasmc.license.model.utils.ResponseWrapper;
import ru.aasmc.license.model.utils.RestErrorList;

import javax.servlet.http.HttpServletRequest;

import static java.util.Collections.singletonMap;

@ControllerAdvice
@EnableWebMvc
public class ExceptionController extends ResponseEntityExceptionHandler {

    /**
     * Handles all the Exceptions receiving a request, responseWrapper.
     */
    @ExceptionHandler(value = {Exception.class})
    public @ResponseBody
    ResponseEntity<ResponseWrapper> handleException(HttpServletRequest request,
                                                    ResponseWrapper responseWrapper) {
        return ResponseEntity.ok(responseWrapper);
    }

    /**
     * Handles all the Authentication Exceptions in the application.
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ResponseWrapper> handleIOException(HttpServletRequest request,
                                                             RuntimeException e) {
        RestErrorList errorList = new RestErrorList(HttpStatus.NOT_ACCEPTABLE, new ErrorMessage(e.getMessage(), e.getMessage()));
        ResponseWrapper wrapper = new ResponseWrapper(null, singletonMap("status", HttpStatus.NOT_ACCEPTABLE), errorList);

        return ResponseEntity.ok(wrapper);
    }
}
