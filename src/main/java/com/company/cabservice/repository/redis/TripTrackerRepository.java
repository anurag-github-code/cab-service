package com.company.cabservice.repository.redis;

import com.company.cabservice.entity.redis.TripVehicle;
import com.company.cabservice.entity.redis.UnOccupiedVehicle;
import java.util.List;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TripTrackerRepository extends CrudRepository<TripVehicle, String> {

}
