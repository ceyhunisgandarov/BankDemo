package az.orient.online.course.bankdemo.model.customer.response;

import lombok.*;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenResponse {

    private Long id;
    private Long userId;
    private String token;
    private Date updatedAt;

}
