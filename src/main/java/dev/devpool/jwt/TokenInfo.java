package dev.devpool.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class TokenInfo {

    private String grantType; // grantType은 JWT에 대한 인증 타입으로 Bearer를 사용한다. 헤더의 prefix에 붙여줌
    private String accessToken;
    private String refreshToken;

}
