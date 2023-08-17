package com.smart4aviation.flightInformation.controller;

import com.smart4aviation.flightInformation.dto.FlightInfoWeightRequestDTO;
import com.smart4aviation.flightInformation.dto.FlightInfoWeightResponseDTO;
import com.smart4aviation.flightInformation.exception.InvalidInputException;
import com.smart4aviation.flightInformation.service.FlightInfoService;
import com.smart4aviation.flightInformation.util.ErrorUtils;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flight")
@Log4j2
public class FlightInfoController {

    private FlightInfoService flightInfoService;
    private ErrorUtils errorUtils;

    @Autowired
    public FlightInfoController(FlightInfoService flightInfoService, ErrorUtils errorUtils) {
        this.flightInfoService = flightInfoService;
        this.errorUtils = errorUtils;
    }


    @GetMapping("/info")
    public ResponseEntity<FlightInfoWeightResponseDTO> getFlightInfo(
            @ModelAttribute @Valid FlightInfoWeightRequestDTO request, BindingResult result) {

        errorUtils.handleBindingResultErrors(result);

        FlightInfoWeightResponseDTO flightInfoWeightResponseDTO = flightInfoService.getFlightInfoWeight(request);
        return ResponseEntity.ok(flightInfoWeightResponseDTO);
    }

}