package dev.devpool.dto.common;

import lombok.*;

@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommonResponseDto<T> {
    int status;

    String message;

    Long id;
}
