package com.company.cabservice.service;

import com.company.cabservice.entity.redis.TripVehicle;
import com.company.cabservice.repository.redis.TripTrackerRepository;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

@Service
public class TripLocationService {

  @Autowired
  TripTrackerRepository tripTrackerRepository;


  public void addVehicleLocation(String tripId, double latitude,
      double longitude) {

    Optional<TripVehicle> tripTrackingData = tripTrackerRepository.findById(tripId);
    TripVehicle tripData;
    if (tripTrackingData.isPresent()) {
      tripData = tripTrackingData.get();
      tripData.getLocations().add(new Point(latitude, longitude));
    } else {
      List<Point> pointList = new LinkedList<>();
      pointList.add(new Point(latitude, longitude));

      tripData = TripVehicle.builder().tripId(tripId).locations(pointList).build();
    }

    tripTrackerRepository.save(
        tripData);
  }

}
