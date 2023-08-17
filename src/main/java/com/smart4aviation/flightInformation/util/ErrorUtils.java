package com.smart4aviation.flightInformation.util;

import com.smart4aviation.flightInformation.exception.InvalidInputException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

@Component
@Log4j2
public class ErrorUtils {

    public void handleBindingResultErrors(BindingResult result) {
        if (result.hasErrors()) {
            List<FieldError> errorList = result.getFieldErrors();

            List<String> errorMessages = errorList.stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();

            String errorMessage = String.join(", ", errorMessages);
            log.error("Invalid input: {}", errorMessage);
            throw new InvalidInputException(errorMessage);
        }
    }
}
