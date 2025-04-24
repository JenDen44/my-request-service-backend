package com.bulish.melnikov.converter.service;

import com.bulish.melnikov.converter.entity.ConverterEntity;
import com.bulish.melnikov.converter.model.ConvertRequest;
import com.bulish.melnikov.converter.model.ConvertResponse;
import com.bulish.melnikov.converter.model.State;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommonConverterServiceImpl implements CommonConverterService {

    private final ConverterRouteService routeService;
    private final ConverterRequestService requestService;

    @Override
    public ConvertResponse processRequest(ConvertRequest request) {

        if (routeService.isValidExtension(request)) {

            var converterEntity = ConverterEntity.builder()
                    .id(UUID.randomUUID().toString())
                    .state(State.INIT)
                    .conversionDate(LocalDateTime.now())
                    .formatFrom(request.getFormatFrom())
                    .formatTo(request.getFormatTo())
                    .build();

            requestService.save(converterEntity);

            try {
                routeService.sendRequest(request);
            } catch (IOException e) {
               log.debug("Error while send request");
            }

            return new ConvertResponse(converterEntity.getId(), converterEntity.getState());
        }

        return null;
    }

    @Override
    public ConvertResponse getRequestStatusById(String id) {
        return null;
    }

    @Override
    public byte[] downloadConvertedFileById(String id) {
        return new byte[0];
    }
}
