package com.bulish.melnikov.converter.service;

import com.bulish.melnikov.converter.model.ConvertRequest;
import com.bulish.melnikov.converter.repository.ConverterRequestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConvertRequestServiceImpl implements ConvertRequestService {

    private final ConverterRequestRepository requestRepo;

    @Override
    public ConvertRequest save(ConvertRequest convertRequest) {
        return requestRepo.save(convertRequest);
    }

    @Override
    public ConvertRequest get(String convertRequestId) {
        return requestRepo.findById(convertRequestId).orElseThrow(()
                -> new RuntimeException("File not found"));
    }

    @Override
    public ConvertRequest update(ConvertRequest convertRequest) {
        ConvertRequest convertRequestFromRedis = get(convertRequest.getId());

        convertRequestFromRedis.setState(convertRequest.getState());
        convertRequestFromRedis.setConvertedFilePath(convertRequest.getConvertedFilePath());

        requestRepo.save(convertRequestFromRedis);

        return convertRequestFromRedis;
    }

    @Override
    public void delete(String convertRequestId) {
        requestRepo.deleteById(convertRequestId);
    }
}
