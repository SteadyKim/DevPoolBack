package dev.devpool.dto.common;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommonDataListResponseDto<T> {

    int status;

    String message;

    List<T> dataList;
}
