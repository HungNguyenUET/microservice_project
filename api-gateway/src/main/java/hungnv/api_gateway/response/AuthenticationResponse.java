package hungnv.api_gateway.response;

import lombok.Getter;

@Getter
public class AuthenticationResponse {
    private int status;
    private String message;

    public AuthenticationResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
