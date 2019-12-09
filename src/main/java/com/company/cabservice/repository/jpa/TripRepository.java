package com.company.cabservice.repository.jpa;

import com.company.cabservice.jpa.Trip;
import javax.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface TripRepository extends CrudRepository<Trip, String> {

}
