package com.bulish.melnikov.converter.repository;

import com.bulish.melnikov.converter.entity.ConverterEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConverterRequestRepository extends CrudRepository<ConverterEntity, String> {
}
