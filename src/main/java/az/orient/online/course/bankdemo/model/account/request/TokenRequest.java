package az.orient.online.course.bankdemo.model.account.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenRequest {

    private Long userId;
    private String token;

}
