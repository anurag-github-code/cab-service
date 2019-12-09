package com.company.cabservice.constants;

import java.util.Arrays;

public enum CabType {

  AUTO,
  CAR_MINI,
  CAR_MICRO,
  CAR_LUXURY,
  VAN;

  public static CabType getCabTypeFromTypeString(String typeString) {

    if (typeString != null) {
      return Arrays.stream(CabType.values()).filter(ct -> typeString.equals(ct.name())).findFirst()
          .orElse(null);
    }
    return null;
  }
}
