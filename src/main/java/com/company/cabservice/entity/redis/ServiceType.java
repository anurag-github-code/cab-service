package com.company.cabservice.entity.redis;


import com.company.cabservice.constants.CabType;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash("serviceTypes-for-geohash")
public class ServiceType {

  @Id
  String geoHashId;
  CabType[] serviceTypes;

}
