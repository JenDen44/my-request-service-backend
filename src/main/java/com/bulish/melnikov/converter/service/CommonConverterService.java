package com.bulish.melnikov.converter.service;

import com.bulish.melnikov.converter.model.ConvertRequest;
import com.bulish.melnikov.converter.model.ConvertResponse;
import com.bulish.melnikov.converter.model.ConvertResponseMsgDTO;

public interface CommonConverterService {

    ConvertResponse processRequest(ConvertRequest request);

    void processResponse(ConvertResponseMsgDTO responseMsgDTO);

    ConvertResponse getRequestStatusById(String id);

    byte[] downloadConvertedFileById(String id);
}
