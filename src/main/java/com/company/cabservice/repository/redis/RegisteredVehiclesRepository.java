package com.company.cabservice.repository.redis;

import com.company.cabservice.entity.redis.RegisteredVehicle;
import com.company.cabservice.constants.CabType;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegisteredVehiclesRepository extends CrudRepository<RegisteredVehicle, String> {

  List<RegisteredVehicle> findAllByIdAndCabType(Iterable<String> ids, CabType cabType);
}
