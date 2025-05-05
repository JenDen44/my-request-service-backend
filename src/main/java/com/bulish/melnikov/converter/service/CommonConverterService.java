package com.bulish.melnikov.converter.service;

import com.bulish.melnikov.converter.model.ConvertRequest;
import com.bulish.melnikov.converter.model.ConvertResponse;
import com.bulish.melnikov.converter.model.ConvertResponseMsgDTO;
import com.bulish.melnikov.converter.model.StatusMessageDTO;


public interface CommonConverterService {

    ConvertResponse processRequest(ConvertRequest request);

    void processResponse(ConvertResponseMsgDTO responseMsgDTO);

    ConvertResponse status(String id);

    byte[] downloadConvertedFileById(String id);

    void changeStatus(StatusMessageDTO statusMessageDTO);
}
