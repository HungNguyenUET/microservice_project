<<<<<<<< HEAD:auth-service/src/main/java/com/vti/auth_service/dto/request/ErrorResponse.java
package com.vti.auth_service.dto.request;
========
package com.auth_service.auth.dto.request;
>>>>>>>> 47f37699d2ea9828b383ce775658bc07a27d7ddb:auth-service/src/main/java/com/auth_service/auth/dto/request/ErrorResponseDTO.java

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
@Getter

@RequiredArgsConstructor
public class ErrorResponse implements Serializable {

    @NonNull
    private int status;

    @NonNull
    private String message;

}
