package com.bulish.melnikov.converter.service;

import com.bulish.melnikov.converter.entity.ConverterEntity;

public interface ConverterRequestService {

    ConverterEntity save(ConverterEntity converterEntity);

    ConverterEntity get(String converterEntityId);

    ConverterEntity update(ConverterEntity converterEntity);

    void delete(String converterEntityId);
}
