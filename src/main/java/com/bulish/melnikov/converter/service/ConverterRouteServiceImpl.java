package com.bulish.melnikov.converter.service;

import com.bulish.melnikov.converter.exception.IncorrectFormatExtensionException;
import com.bulish.melnikov.converter.model.ConvertRequest;
import com.bulish.melnikov.converter.model.ConvertRequestMsgDTO;
import com.bulish.melnikov.converter.model.ExtensionDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConverterRouteServiceImpl implements ConverterRouteService {

    private final ExtensionService extensionService;
    private final FileService fileService;
    private final StreamBridge streamBridge;

    @Override
    public boolean isValidExtension(ConvertRequest request) {
        String formatFrom = fileService.getFileExtension(request.getFile().getOriginalFilename());
        List<ExtensionDto> extensions = extensionService.getAllowedExtensions();

        if (extensions.stream().noneMatch(e -> e.getFormatFrom().equals(formatFrom)
                && e.getFormatsTo().contains(request.getFormatTo()))) {
            throw new IncorrectFormatExtensionException("Check available formats format from "
                    + request.getFormatFrom() + " to " +  request.getFormatTo() + " are not supported");
        }

        return true;
    }

    @Override
    public void sendRequest(ConvertRequestMsgDTO convertRequestMsgDTO) {
        String destination = convertRequestMsgDTO.getType().getBinding();

        var result = streamBridge.send(destination, convertRequestMsgDTO);
        log.debug("result send request " + result);
    }
}
