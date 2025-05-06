package com.bulish.melnikov.converter.job;

import com.bulish.melnikov.converter.service.ConverterRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Slf4j
public class ConverterEntityCleaner {

    private final ConverterRequestService converterService;

   @Scheduled(fixedRate = 5, timeUnit = TimeUnit.MINUTES)
    public void cleanConverterEntities() {
        log.info("cleanConverterEntities started");
        converterService.deleteOldConverterEntities();
    }
}
