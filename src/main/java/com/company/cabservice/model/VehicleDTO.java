package com.company.cabservice.model;

import com.company.cabservice.constants.CabType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehicleDTO {

  @JsonProperty("id")
  String id;
  @JsonProperty("cabType")
  CabType cabType;
  @JsonProperty("registrationNumber")
  String registrationNumber;
  @JsonProperty("seatCapacity")
  String seatCapacity;
  @JsonProperty("driverId")
  String driverId;
  @JsonProperty("latitude")
  Double latitude;
  @JsonProperty("longitude")
  Double longitude;

}
