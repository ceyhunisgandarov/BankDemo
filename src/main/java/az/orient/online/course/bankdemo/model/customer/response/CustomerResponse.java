package az.orient.online.course.bankdemo.model.customer.response;

import az.orient.online.course.bankdemo.model.account.response.AccountResponse;
import az.orient.online.course.bankdemo.model.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponse implements Serializable {

    private Long id;
    private String name;
    private String surname;
    private Date dob;
    private String address;
    private String phone;
//    private List<AccountResponse> accounts;
    private String pin;
    private String serialNumber;
    private String cif;
    private Date createdAt;

}
