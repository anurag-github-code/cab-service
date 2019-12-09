package com.company.cabservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ServiceTypesDTO {

  @JsonProperty("serviceType")
  String serviceType;

  public ServiceTypesDTO(String name) {
  }
}
