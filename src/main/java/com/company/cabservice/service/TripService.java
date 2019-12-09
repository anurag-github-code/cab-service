package com.company.cabservice.service;

import com.company.cabservice.constants.CabType;
import com.company.cabservice.constants.TripStatus;
import com.company.cabservice.jpa.Trip;
import com.company.cabservice.repository.jpa.TripRepository;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class TripService {

  DateTimeFormatter format = DateTimeFormatter.ofPattern("HHmm, dd MMM yyyy");

  @Autowired
  TripRepository tripRepository;


  public String createTrip(String customerId, String vehicleId, CabType cabType) {

    String tripId = UUID.randomUUID().toString();

    Trip t = Trip.builder().id(tripId).customerId(customerId).vehicleId(vehicleId).cabType(cabType)
        .bookedAt(getCurrentTime()).build();
    tripRepository.save(t);

    return tripId;
  }

  public String startTrip(String tripId, Double startPointLatitude,
      @RequestParam Double startPointLongitude) {

    Optional<Trip> tripData = tripRepository.findById(tripId);

    if (tripData.isPresent() && tripData.get().getTripStatus().equals(TripStatus.Booked)) {
      Trip trip = tripData.get();
      trip.setStartPointLatitude(startPointLatitude);
      trip.setStartPointLongitude(startPointLongitude);
      trip.setTripStatus(TripStatus.Started);
      trip.setStartedAt(getCurrentTime());
      tripRepository.save(trip);
      return trip.getId();
    }

    return null;
  }

  public String cancelTrip(String tripId) {

    Optional<Trip> tripData = tripRepository.findById(tripId);

    if (tripData.isPresent() && tripData.get().getTripStatus().equals(TripStatus.Booked)) {
      Trip trip = tripData.get();
      trip.setTripStatus(TripStatus.Cancelled);
      trip.setCancelledAt(getCurrentTime());
      tripRepository.save(trip);
      return trip.getId();
    }

    return null;
  }

  public String endTrip(String tripId, Double endPointLatitude,
      @RequestParam Double endPointLongitude) {

    Optional<Trip> tripData = tripRepository.findById(tripId);

    if (tripData.isPresent() && tripData.get().getTripStatus().equals(TripStatus.Started)) {
      Trip trip = tripData.get();
      trip.setStartPointLatitude(endPointLatitude);
      trip.setStartPointLongitude(endPointLongitude);
      trip.setTripStatus(TripStatus.Ended);
      trip.setEndedAt(getCurrentTime());
      tripRepository.save(trip);
      return trip.getId();
    }

    return null;
  }

  private LocalDateTime getCurrentTime() {
    LocalDateTime ldt = LocalDateTime.now();
    return ldt;
  }
}
