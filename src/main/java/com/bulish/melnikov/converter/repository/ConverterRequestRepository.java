package com.bulish.melnikov.converter.repository;

import com.bulish.melnikov.converter.entity.ConverterEntity;
import com.bulish.melnikov.converter.enums.State;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ConverterRequestRepository extends CrudRepository<ConverterEntity, String> {

    List<ConverterEntity> findByState(State state);
}
