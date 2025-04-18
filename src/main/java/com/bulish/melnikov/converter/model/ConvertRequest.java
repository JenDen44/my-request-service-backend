package com.bulish.melnikov.converter.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
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
public class ConvertRequest implements Serializable {

    @Id
    private String id;

    private State state;

    private String convertedFilePath;

    private String formatTo;

    private String formatFrom;

    private LocalDateTime conversionDate;

    public ConvertRequest(String formatTo, String formatFrom) {
        this.formatTo = formatTo;
        this.formatFrom = formatFrom;
        this.state = State.INIT;
        this.id = UUID.randomUUID().toString();
        this.conversionDate = LocalDateTime.now();
    }
}
