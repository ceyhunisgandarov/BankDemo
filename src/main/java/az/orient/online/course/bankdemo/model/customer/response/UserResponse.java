package az.orient.online.course.bankdemo.model.customer.response;

import lombok.Data;

@Data
public class UserResponse {

    private String username;
    private String fullName;
    private TokenResponse tokenResponse;

}
