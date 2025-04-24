package com.bulish.melnikov.converter.service;

import com.bulish.melnikov.converter.model.ConvertRequest;
import com.bulish.melnikov.converter.model.ConvertResponse;

public interface CommonConverterService {

    ConvertResponse processRequest(ConvertRequest request);

    ConvertResponse getRequestStatusById(String id);

    byte[] downloadConvertedFileById(String id);
}
