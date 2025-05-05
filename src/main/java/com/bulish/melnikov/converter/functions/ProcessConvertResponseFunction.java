package com.bulish.melnikov.converter.functions;

import com.bulish.melnikov.converter.model.ConvertResponseMsgDTO;
import com.bulish.melnikov.converter.model.StatusMessageDTO;
import com.bulish.melnikov.converter.service.CommonConverterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class ProcessConvertResponseFunction {

    private final CommonConverterService commonConverterService;

    @Bean
    public Consumer<ConvertResponseMsgDTO> process() {
        return convertResponse -> {
            log.info("convert response with converted data from format {} to {}", convertResponse.getFormatFrom(), convertResponse.getFormatTo());
            commonConverterService.processResponse(convertResponse);
        };
    }

    @Bean
    public Consumer<StatusMessageDTO> status() {
        return statusMessageDTO -> {
            log.info("Convert request {} status changed to {}", statusMessageDTO.getId(), statusMessageDTO.getState());
            commonConverterService.changeStatus(statusMessageDTO);
        };
    }
}
