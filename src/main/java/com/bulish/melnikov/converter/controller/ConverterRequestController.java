package com.bulish.melnikov.converter.controller;

import com.bulish.melnikov.converter.model.ConvertRequest;
import com.bulish.melnikov.converter.model.ConvertResponse;
import com.bulish.melnikov.converter.model.ExtensionDto;
import com.bulish.melnikov.converter.service.CommonConverterService;
import com.bulish.melnikov.converter.service.ExtensionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ConverterRequestController {

    private final CommonConverterService commonConverterService;
    private final ExtensionService extensionService;

    @PostMapping(value = "/convert", consumes = {"multipart/form-data"})
    @ResponseStatus(HttpStatus.OK)
    public ConvertResponse convert(@ModelAttribute ConvertRequest request) {
        return commonConverterService.processRequest(request);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ConvertResponse requestStatus(@PathVariable("id") String id) {
        return commonConverterService.getRequestStatusById(id);
    }

    @GetMapping("/{id}/download")
    @ResponseStatus(HttpStatus.OK)
    public byte[] download(@PathVariable("id") String id) {
        return commonConverterService.downloadConvertedFileById(id);
    }

    @GetMapping("/extensions")
    @ResponseStatus(HttpStatus.OK)
    public List<ExtensionDto> allowedExtensions() {
        return extensionService.getAllowedExtensions();
    }
}
