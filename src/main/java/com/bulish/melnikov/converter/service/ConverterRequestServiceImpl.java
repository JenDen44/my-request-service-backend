package com.bulish.melnikov.converter.service;

import com.bulish.melnikov.converter.entity.ConverterEntity;
import com.bulish.melnikov.converter.enums.State;
import com.bulish.melnikov.converter.repository.ConverterRequestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConverterRequestServiceImpl implements ConverterRequestService {

    private final ConverterRequestRepository requestRepo;
    private final FileService fileService;

    @Override
    public ConverterEntity save(ConverterEntity converterEntity) {
        return requestRepo.save(converterEntity);
    }

    @Override
    public ConverterEntity get(String converterEntityId) {
        return requestRepo.findById(converterEntityId).orElseThrow(()
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
    public void deleteOldConverterEntities() {
       List<ConverterEntity> converterEntities =
               findByStateAndEndConversionDateLessThanEqual(State.CONVERTED, LocalDateTime.now().minusMinutes(5));
        for (ConverterEntity converterEntity : converterEntities) {
            log.info("converterEntity id {}, end conversion date {} was removed ", converterEntity.getId(), converterEntity.getEndConversionDate());
            requestRepo.delete(converterEntity);
            try {
                fileService.deleteFile(converterEntity.getConvertedFilePath());
            } catch (IOException e) {
                log.error("Error while delete file {}", e.getMessage());
            }
        }
    }

    public List<ConverterEntity> findByStateAndEndConversionDateLessThanEqual(State state, LocalDateTime localDateTime) {
        return requestRepo.findByState(state).stream().filter(entity -> entity.getEndConversionDate() != null &&
                entity.getEndConversionDate().isBefore(localDateTime)).collect(Collectors.toList());
    }
}
