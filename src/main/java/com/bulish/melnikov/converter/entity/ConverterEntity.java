package com.bulish.melnikov.converter.entity;

import com.bulish.melnikov.converter.model.State;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@RedisHash("ConvertRequest")
@ToString
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ConverterEntity implements Serializable {

    @Id
    private String id;

    private State state;

    private String formatTo;

    private String formatFrom;

    private LocalDateTime conversionDate;

    public ConverterEntity(String formatTo, String formatFrom) {
        this.formatTo = formatTo;
        this.formatFrom = formatFrom;
        this.state = State.INIT;
        this.id = UUID.randomUUID().toString();
        this.conversionDate = LocalDateTime.now();
    }
}
