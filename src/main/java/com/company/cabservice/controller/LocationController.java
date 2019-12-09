package com.company.cabservice.controller;

import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.ResponseEntity.status;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import com.company.cabservice.service.AvailableVehicleLocationService;
import com.company.cabservice.service.TripLocationService;
import java.util.Objects;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ComponentScan(basePackages = {"com.company"})
@RequestMapping(value = "/v1/vehicles/{vehicleId}/locations")
public class LocationController {

  @Autowired
  TripLocationService tripLocationService;

  @Autowired
  AvailableVehicleLocationService availableVehicleLocationService;


  @RequestMapping(value = "/location", method = PUT)
  public ResponseEntity<?> updateVehicleLocation(@PathVariable String vehicleId,
      @RequestParam String latitude,
      @RequestParam String longitude, @RequestParam String tripId) {

    if (NumberUtils.isCreatable(latitude) && NumberUtils.isCreatable(longitude)) {

      if (Objects.isNull(tripId)) {
        //update redis store for available vehicles.
        availableVehicleLocationService.addVehicleLocation(vehicleId, Double.parseDouble(latitude),
            Double.parseDouble(longitude));

      } else {
        // call Trip tracking service to update co-ordinates for this tripId
        tripLocationService.addVehicleLocation(tripId, Double.parseDouble(latitude),
            Double.parseDouble(longitude));

      }
    }

    return ResponseEntity.status(ACCEPTED).body("success");
  }

}

  
