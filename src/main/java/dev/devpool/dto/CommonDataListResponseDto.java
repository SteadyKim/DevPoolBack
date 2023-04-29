package dev.devpool.dto;

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

    ArrayList<T> dataList;
}
