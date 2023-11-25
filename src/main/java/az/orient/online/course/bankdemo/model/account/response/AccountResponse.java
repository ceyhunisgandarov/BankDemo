package az.orient.online.course.bankdemo.model.account.response;

import az.orient.online.course.bankdemo.model.customer.response.CustomerResponse;
import az.orient.online.course.bankdemo.model.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponse implements Serializable {

    private Long id;
    private String name;
    private String accountNo;
    private String iban;
    private String currency;
    private CustomerResponse customerResponse;
    private Date createdAt;

}
