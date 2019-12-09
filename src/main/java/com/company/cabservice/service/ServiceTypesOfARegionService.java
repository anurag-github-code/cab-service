package com.company.cabservice.service;

import com.company.cabservice.entity.redis.ServiceType;
import com.company.cabservice.model.ServiceTypesDTO;
import com.company.cabservice.repository.redis.ServiceTypeRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceTypesOfARegionService {

  @Autowired
  ServiceTypeRepository serviceTypeRepository;

  public List<ServiceTypesDTO> getSupportedServiceOfRegion(double latitude,
      double longitude) {

    String geoHashForThePoint = calculateGeoHashForThePoint(latitude, longitude);

    Optional<ServiceType> serviceTypesData = serviceTypeRepository
        .findById(geoHashForThePoint);

    if (serviceTypesData.isPresent()) {

      return createServiceDTO(serviceTypesData.get());
    }

    return null;
  }

  private List<ServiceTypesDTO> createServiceDTO(ServiceType serviceType) {

    return Arrays.stream(serviceType.getServiceTypes()).map(x -> new ServiceTypesDTO(x.name()))
        .collect(
            Collectors.toList());
  }

  private String calculateGeoHashForThePoint(double latitude, double longitude) {
    // use Google S2 geometry algo to get a geoHash for a lat-long

    /* pseudo code
    latlng = s2.S2LatLng.FromDegrees(-30.043800, -51.140220)
    /cell = s2.S2CellId.FromLatLng(latlng);
    cell.ToToken(); //eg 951977d377e723ab
    */

    return "951977d377e723ab";
  }


}
