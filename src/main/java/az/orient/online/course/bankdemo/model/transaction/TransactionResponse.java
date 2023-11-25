package az.orient.online.course.bankdemo.model.transaction;

import az.orient.online.course.bankdemo.model.account.response.AccountResponse;
import az.orient.online.course.bankdemo.model.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {

    private Long id;
    private AccountResponse fromAccount;
    private String toAccount;
    private String iban;
    private String note;
    private Double amount;
    private String currency;
    private Date createdAt;


}
