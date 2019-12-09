package com.company.cabservice.entity.redis;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.GeoIndexed;

@Getter
@Setter
@Builder
@RedisHash("unoccupied-vehicles")
public class UnOccupiedVehicle {

  @Id
  String vehicleId;

  @GeoIndexed
  Point location;

  @TimeToLive
  private Long expiration;


}
