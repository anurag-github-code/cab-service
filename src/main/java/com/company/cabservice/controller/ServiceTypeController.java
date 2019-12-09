package com.company.cabservice.controller;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.status;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import com.company.cabservice.entity.redis.UnOccupiedVehicle;
import com.company.cabservice.model.ServiceTypesDTO;
import com.company.cabservice.service.ServiceTypesOfARegionService;
import com.company.cabservice.service.VehicleFinderService;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ComponentScan(basePackages = {"com.company"})
@RequestMapping(value = "/v1/cabs")
public class ServiceTypeController {

  @Autowired
  ServiceTypesOfARegionService serviceTypesOfARegionService;

  @RequestMapping(value = "/services", produces = "application/json", method = GET)
  public ResponseEntity<?> getSupportedServicesInTheRegion(@RequestParam String latitude,
      @RequestParam String longitude) {

    if (NumberUtils.isCreatable(latitude) && NumberUtils.isCreatable(longitude)) {
      List<ServiceTypesDTO> serviceTypes = serviceTypesOfARegionService
          .getSupportedServiceOfRegion(Double.parseDouble(latitude), Double.parseDouble(longitude));

      return status(OK).body(serviceTypes);
    }
    return ResponseEntity.status(BAD_REQUEST).body("invalid request");
  }
}
