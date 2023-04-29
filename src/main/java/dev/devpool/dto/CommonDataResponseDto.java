package dev.devpool.dto;

import lombok.*;

import java.util.List;

@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommonDataResponseDto<T> {
    int status;

    String message;

    T data;
}
