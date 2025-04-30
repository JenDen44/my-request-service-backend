package com.bulish.melnikov.converter.service;

import com.bulish.melnikov.converter.model.ConvertRequest;
import com.bulish.melnikov.converter.model.ConvertRequestMsgDTO;

public interface ConverterRouteService {

    boolean isValidExtension(ConvertRequest request);

    void sendRequest(ConvertRequestMsgDTO convertRequestMsgDTO);
}
