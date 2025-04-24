package com.bulish.melnikov.converter.service;

import com.bulish.melnikov.converter.entity.ConverterEntity;
import com.bulish.melnikov.converter.repository.ConverterRequestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConverterRequestServiceImpl implements ConverterRequestService {

    private final ConverterRequestRepository requestRepo;

    @Override
    public ConverterEntity save(ConverterEntity converterEntity) {
        return requestRepo.save(converterEntity);
    }

    @Override
    public ConverterEntity get(String convertRequestId) {
        return requestRepo.findById(convertRequestId).orElseThrow(()
                -> new RuntimeException("File not found"));
    }

    @Override
    public ConverterEntity update(ConverterEntity converterEntity) {
        ConverterEntity converterEntityFromRedis = get(converterEntity.getId());

        converterEntityFromRedis.setState(converterEntity.getState());

        requestRepo.save(converterEntityFromRedis);

        return converterEntityFromRedis;
    }

    @Override
    public void delete(String convertRequestId) {
        requestRepo.deleteById(convertRequestId);
    }
}
