package com.company.cabservice.entity.redis;

import com.company.cabservice.constants.CabType;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@RedisHash("registered-vehicles")
public class RegisteredVehicle {

  @Id
  String id;
  @Indexed
  CabType cabType;
  String registrationNumber;
  String seatCapacity;
  String driverId;

}
