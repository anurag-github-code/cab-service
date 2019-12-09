package com.company.cabservice.service;

import com.company.cabservice.constants.Constants;
import com.company.cabservice.entity.redis.RegisteredVehicle;
import com.company.cabservice.entity.redis.UnOccupiedVehicle;
import com.company.cabservice.model.VehicleDTO;
import com.company.cabservice.repository.redis.RegisteredVehiclesRepository;
import com.company.cabservice.repository.redis.UnoccupiedVehicleRepository;
import com.company.cabservice.constants.CabType;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class VehicleFinderService {

  @Autowired
  UnoccupiedVehicleRepository availableVehicleRepository;

  @Autowired
  RegisteredVehiclesRepository registeredVehiclesRepository;

  @Autowired
  RedisTemplate<String, Object> redisTemplate;

  public List<VehicleDTO> findNearbyVehicle(CabType cabType, double latitude,
      double longitude) {

    //find all available cabs in radius of 2 Kms
    List<UnOccupiedVehicle> nearByVehicles = availableVehicleRepository
        .findByLocationNear(new Point(latitude, longitude), new Distance(Constants.RADIUS));

    Map<String, UnOccupiedVehicle> unOccupiedVehicleMap = nearByVehicles.stream()
        .collect(Collectors.toMap(UnOccupiedVehicle::getVehicleId, Function.identity()));

    List<RegisteredVehicle> allByIdAndCabType = registeredVehiclesRepository
        .findAllByIdAndCabType(unOccupiedVehicleMap.keySet(), cabType);

    List<VehicleDTO> all = allByIdAndCabType.stream()
        .map(x -> createVehicle(x, unOccupiedVehicleMap.get(x.getId())))
        .collect(Collectors.toList());

    return all;
  }

  private VehicleDTO createVehicle(RegisteredVehicle regVehicle,
      UnOccupiedVehicle unOccupiedVehicle) {
    VehicleDTO vehicle = new VehicleDTO();
    vehicle.setId(regVehicle.getId());
    vehicle.setDriverId(regVehicle.getDriverId());
    vehicle.setLatitude(unOccupiedVehicle.getLocation().getX());
    vehicle.setLatitude(unOccupiedVehicle.getLocation().getY());
    vehicle.setSeatCapacity(regVehicle.getSeatCapacity());
    return vehicle;
  }

}
