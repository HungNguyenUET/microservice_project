<<<<<<<< HEAD:auth-service/src/main/java/com/vti/auth_service/dto/response/AuthenticationResponse.java
package com.vti.auth_service.dto.response;
========
package com.auth_service.auth.dto.response;
>>>>>>>> 47f37699d2ea9828b383ce775658bc07a27d7ddb:auth-service/src/main/java/com/auth_service/auth/dto/response/AuthenticationResponseDTO.java

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class AuthenticationResponse {
    private int status;
    private String message;

    @JsonProperty("user_id")
    private int userId;

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("refresh_token")
    private String refreshToken;
}
