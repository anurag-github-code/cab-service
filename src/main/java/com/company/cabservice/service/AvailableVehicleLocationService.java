package com.company.cabservice.service;

import com.company.cabservice.entity.redis.UnOccupiedVehicle;
import com.company.cabservice.repository.redis.UnoccupiedVehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

@Service
public class AvailableVehicleLocationService {

  @Autowired
  UnoccupiedVehicleRepository availableVehicleRepository;


  public void addVehicleLocation(String vehicleId, double latitude,
      double longitude) {

    availableVehicleRepository.save(
        UnOccupiedVehicle.builder().location(new Point(latitude, longitude)).vehicleId(vehicleId)
            .expiration(30L).build());
  }

}
