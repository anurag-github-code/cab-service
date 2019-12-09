package com.company.cabservice.repository.redis;

import com.company.cabservice.entity.redis.UnOccupiedVehicle;
import java.util.List;
import java.util.Set;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnoccupiedVehicleRepository extends CrudRepository<UnOccupiedVehicle, String> {

  //this is redis Geo location supported
  List<UnOccupiedVehicle> findByLocationNear(Point point, Distance distance);
}
