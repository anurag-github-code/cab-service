package com.company.cabservice.jpa;

import com.company.cabservice.constants.CabType;
import com.company.cabservice.constants.TripStatus;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Table(name = "trips")
@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Trip {

  @Id
  String id;
  String customerId;
  String vehicleId;

  CabType cabType;

  Double startPointLatitude;
  Double startPointLongitude;

  Double endPointLongitude;
  Double endPointLatitude;

  TripStatus tripStatus;

  LocalDateTime bookedAt;
  LocalDateTime cancelledAt;
  LocalDateTime startedAt;
  LocalDateTime endedAt;

}
