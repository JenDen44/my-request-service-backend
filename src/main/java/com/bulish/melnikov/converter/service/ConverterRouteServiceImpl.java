package com.bulish.melnikov.converter.service;

import com.bulish.melnikov.converter.enums.FileType;
import com.bulish.melnikov.converter.exception.IncorrectFormatExtensionException;
import com.bulish.melnikov.converter.model.ConvertRequest;
import com.bulish.melnikov.converter.model.ConvertRequestMsgDTO;
import com.bulish.melnikov.converter.model.ExtensionDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
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

            throw new IncorrectFormatExtensionException("Check available formats formatTo "
                    + request.getFormatTo() + " or formatFrom " +  formatFrom + " are not supported");
        }

        return true;
    }

    private String getPathToServiceByTypeFile(ConvertRequest request) {
        String contentType = request.getFile().getContentType();

        if (contentType.startsWith("audio/")) {
            return FileType.AUDIO.getBinding();
        } else if (contentType.startsWith("video/")) {
            return FileType.VIDEO.getBinding();
        } else {
            return FileType.FILE.getBinding();
        }
    }

    @Override
    public void sendRequest(ConvertRequest request) throws IOException {
        String destination = getPathToServiceByTypeFile(request);

        var msg = ConvertRequestMsgDTO.builder()
                .file(request.getFile().getBytes())
                .conversionDate(LocalDateTime.now())
                .formatTo(request.getFormatTo())
                .formatFrom(request.getFormatFrom())
                .build();

        var result = streamBridge.send(destination, msg);
        log.debug("result send request " + result);
    }
}
