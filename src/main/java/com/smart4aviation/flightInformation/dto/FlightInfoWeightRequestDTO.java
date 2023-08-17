package com.smart4aviation.flightInformation.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightInfoWeightRequestDTO {
    @NotNull
    @Min(value = 1, message = "Flight number must be greater than zero")
    @Positive(message = "The flight number must be positive")
    private Integer flightNumber;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime date;
}
