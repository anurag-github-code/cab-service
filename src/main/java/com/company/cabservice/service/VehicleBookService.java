package com.company.cabservice.service;

import com.company.cabservice.constants.CabType;
import com.company.cabservice.entity.redis.UnOccupiedVehicle;
import com.company.cabservice.model.VehicleDTO;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

@Service
public class VehicleBookService {

  @Autowired
  VehicleFinderService vehicleFinderService;

  @Autowired
  TripService tripService;

  @Autowired
  RedisTemplate<String, Object> redisTemplate;

  public String bookNearbyVehicle(String customerId, CabType cabType, double latitude,
      double longitude) {

    List<VehicleDTO> nearbyVehicles = vehicleFinderService
        .findNearbyVehicle(cabType, latitude, longitude);

    String bookedVehicleId = null;

    for (VehicleDTO v : nearbyVehicles) {
      bookedVehicleId = bookVehiclebyId(v.getId());
      if (Objects.nonNull(bookedVehicleId)) {
        break;
      }
    }

    if (Objects.nonNull(bookedVehicleId)) {
      return tripService.createTrip(customerId, bookedVehicleId, cabType);
    }

    return null;
  }

  public String cancelTrip(String tripId) {
    return tripService.cancelTrip(tripId);
  }

  private String bookVehiclebyId(String vehicleId) {

    //execute a transaction with optimistic locking of the key
    List<UnOccupiedVehicle> txResults = redisTemplate
        .execute(new SessionCallback<List<UnOccupiedVehicle>>() {

          public List<UnOccupiedVehicle> execute(RedisOperations operations)
              throws DataAccessException {

            UnOccupiedVehicle v = (UnOccupiedVehicle) operations.opsForHash()
                .get("unoccupied-vehicles", "key");
            if (Objects.nonNull(v)) {
              operations.watch("unoccupied-vehicles:" + vehicleId);
              operations.multi();

              operations.opsForHash().delete("unoccupied-vehicles", vehicleId);

              // This will contain the results of all operations in the transaction
              List<UnOccupiedVehicle> vehicleAssigned = operations.exec();
              operations.unwatch();

              return vehicleAssigned;
            }
            return null;
          }
        });
    System.out.println("Cab booked is : " + txResults.get(0).getVehicleId());
    return txResults.get(0).getVehicleId();
  }
}
