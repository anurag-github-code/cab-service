package com.company.cabservice.controller;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.status;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import com.company.cabservice.constants.CabType;
import com.company.cabservice.model.VehicleDTO;
import com.company.cabservice.service.CustomerProfileService;
import com.company.cabservice.service.VehicleBookService;
import com.company.cabservice.service.VehicleFinderService;
import java.util.List;
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
@RequestMapping(value = "/v1/vehicles/{serviceType}")
public class VehicleController {

  @Autowired
  VehicleFinderService vehicleFinderService;
  @Autowired
  VehicleBookService vehicleBookService;
  @Autowired
  CustomerProfileService customerProfileService;

  @RequestMapping(value = "/search", produces = "application/json", method = GET)
  public ResponseEntity<?> getNearbyCabs(@PathVariable("cabType") String cabType,
      @RequestParam String latitude,
      @RequestParam String longitude) {

    if (NumberUtils.isCreatable(latitude) && NumberUtils.isCreatable(longitude)) {

      CabType cabTypeEnum = CabType.getCabTypeFromTypeString(cabType);
      if (Objects.isNull(cabTypeEnum)) {
        return status(BAD_REQUEST).body("Invalid cab type requested.");
      }

      List<VehicleDTO> nearbyVehicles = vehicleFinderService
          .findNearbyVehicle(cabTypeEnum, Double.parseDouble(latitude),
              Double.parseDouble(longitude));

      return status(OK).body(nearbyVehicles);
    }
    return ResponseEntity.status(BAD_REQUEST).body("Invalid lat or long.");

  }

  @RequestMapping(value = "/book", produces = "text/plain", method = POST)
  public ResponseEntity<?> bookANearbyCab(@PathVariable("cabType") String cabType,
      @RequestParam String customerId,
      @RequestParam String latitude,
      @RequestParam String longitude) {

    if (NumberUtils.isCreatable(latitude) && NumberUtils.isCreatable(longitude)) {

      CabType cabTypeEnum = CabType.getCabTypeFromTypeString(cabType);
      if (Objects.isNull(cabTypeEnum)) {
        return status(BAD_REQUEST).body("Invalid cab type requested.");
      }

      if (customerProfileService.isValidCustomer(customerId)) {

        final String tripId = vehicleBookService
            .bookNearbyVehicle(customerId, cabTypeEnum, Double.parseDouble(latitude),
                Double.parseDouble(longitude));

        //No cab available at the moment
        if (Objects.isNull(tripId)) {
          return status(OK).body("");
        }

        return status(CREATED).body(tripId);
      }
    }
    return ResponseEntity.status(BAD_REQUEST).body("Invalid lat or long.");
  }

  @RequestMapping(value = "/cancel", produces = "text/plain", method = PUT)
  public ResponseEntity<?> cancelTrip(@PathVariable("tripId") String tripId,
      HttpServletResponse response) {

    String cancelledTripId = vehicleBookService
        .cancelTrip(tripId);

    if (Objects.nonNull(cancelledTripId)) {
      return status(OK).body(tripId);
    }
    return ResponseEntity.status(BAD_REQUEST).body("Invalid tripId.");
  }
}


