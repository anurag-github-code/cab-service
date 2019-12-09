package com.company.cabservice.repository.redis;

import com.company.cabservice.entity.redis.ServiceType;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceTypeRepository extends CrudRepository<ServiceType, String> {

}
