package com.bulish.melnikov.converter.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class ConvertRequestMsgDTO implements Serializable {

    private byte [] file;

    private String formatTo;

    private String formatFrom;

    private LocalDateTime conversionDate;
}
