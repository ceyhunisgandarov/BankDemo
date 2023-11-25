package az.orient.online.course.bankdemo.model.transaction;

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
public class TransactionRequest {

    private Long id;
    private Long fromAccountId;
    private String toAccount;
    private String note;
    private Double amount;
    private String iban;
    private String currency;

}
