package com.bulish.melnikov.converter.service;

import com.bulish.melnikov.converter.entity.ConverterEntity;
import com.bulish.melnikov.converter.enums.FileType;
import com.bulish.melnikov.converter.enums.State;
import com.bulish.melnikov.converter.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommonConverterServiceImpl implements CommonConverterService {

    private final ConverterRouteService routeService;
    private final ConverterRequestService requestService;
    private final FileService fileService;

    @Override
    public ConvertResponse processRequest(ConvertRequest request) {
        if (routeService.isValidExtension(request)) {
            String fileName = fileService.getFileNameWithoutExtension(request.getFile().getOriginalFilename());

            var converterEntity = ConverterEntity.builder()
                    .id(UUID.randomUUID().toString())
                    .state(State.INIT)
                    .conversionDate(LocalDateTime.now())
                    .formatFrom(request.getFormatFrom())
                    .formatTo(request.getFormatTo())
                    .initialName(fileName)
                    .build();

            converterEntity = requestService.save(converterEntity);

            byte[] file = null;

            try {
                file = request.getFile().getBytes();
            } catch (IOException e) {
                log.debug("Error while get bytes from multipart");
            }

            FileType type = fileService.getFileTypeByContent(request.getFile());

            var msg = ConvertRequestMsgDTO.builder()
                    .file(file)
                    .formatTo(request.getFormatTo())
                    .formatFrom(request.getFormatFrom())
                    .id(converterEntity.getId())
                    .type(type)
                    .build();

                routeService.sendRequest(msg);

            return ConvertResponse.builder()
                    .id(converterEntity.getId())
                    .state(converterEntity.getState())
                    .build();
        }

        return null;
    }

    @Override
    public void processResponse(ConvertResponseMsgDTO responseMsgDTO) {
        ConverterEntity converterEntity = requestService.get(responseMsgDTO.getId());
        String convertedPath = null;
        try {
            convertedPath = fileService.saveFile(responseMsgDTO.getFile(), responseMsgDTO.getFormatTo(), converterEntity.getInitialName());
        } catch (IOException e) {
            log.error("Error while save file " + e.getMessage());
        }
        converterEntity.setConvertedFilePath(convertedPath);
        requestService.save(converterEntity);
    }

    @Override
    public ConvertResponse status(String id) {
        ConverterEntity entity = requestService.get(id);

        return ConvertResponse.builder()
                .id(entity.getId())
                .state(entity.getState())
                .build();
    }

    @Override
    public byte[] downloadConvertedFileById(String id) {
        ConverterEntity entity = requestService.get(id);

        if (entity.getState() != State.CONVERTED) throw new RuntimeException("File status " + entity.getState());
        try {
            return Files.readAllBytes(Paths.get(entity.getConvertedFilePath()));
            } catch (IOException e) {
                throw new RuntimeException("Error downloading file", e);
            }
        }

    @Override
    public void changeStatus(StatusMessageDTO statusMessageDTO) {
        ConverterEntity entity = requestService.get(statusMessageDTO.getId());
        entity.setState(statusMessageDTO.getState());
        requestService.update(entity);
    }
}
