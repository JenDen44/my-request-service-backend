package com.bulish.melnikov.converter.service;

import com.bulish.melnikov.converter.model.ConvertRequest;

import java.io.IOException;

public interface ConverterRouteService {

    boolean isValidExtension(ConvertRequest request);

    void sendRequest(ConvertRequest request) throws IOException;


}
