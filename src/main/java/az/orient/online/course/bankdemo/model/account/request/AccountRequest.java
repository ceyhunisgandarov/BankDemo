package az.orient.online.course.bankdemo.model.account.request;


import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class AccountRequest implements Serializable {

    private Long id;
    private String name;
    private String accountNo;
    private String currency;
    private String iban;
    private Long customerId;

}
