package com.smart4aviation.flightInformation.controller;

import com.smart4aviation.flightInformation.dto.AirportInfoRequestDTO;
import com.smart4aviation.flightInformation.dto.AirportInfoResponseDTO;
import com.smart4aviation.flightInformation.service.AirportInfoService;
import com.smart4aviation.flightInformation.util.ErrorUtils;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/airport")
@Log4j2
public class AirportInfoController {

    private AirportInfoService airportInfoService;
    private ErrorUtils errorUtils;


    @Autowired
    public AirportInfoController(AirportInfoService airportInfoService, ErrorUtils errorUtils) {
        this.airportInfoService = airportInfoService;
        this.errorUtils = errorUtils;
    }

    @GetMapping("/info")
    public ResponseEntity<AirportInfoResponseDTO> getAirportInfo (
            @ModelAttribute @Valid AirportInfoRequestDTO request, BindingResult result
    ) {

        errorUtils.handleBindingResultErrors(result);

        AirportInfoResponseDTO responseDTO = airportInfoService.airportInfo(request);
        return ResponseEntity.ok(responseDTO);

    }
}
