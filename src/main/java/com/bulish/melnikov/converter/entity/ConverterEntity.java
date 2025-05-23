package com.bulish.melnikov.converter.entity;

import com.bulish.melnikov.converter.enums.State;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@RedisHash("ConverterEntity")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ConverterEntity implements Serializable {

    @Id
    private String id;

    private State state;

    private String formatTo;

    private String formatFrom;

    private LocalDateTime startConversionDate;

    private LocalDateTime endConversionDate;

    private String convertedFilePath;

    private String initialName;
}
