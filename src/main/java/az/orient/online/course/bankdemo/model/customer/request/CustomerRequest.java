package az.orient.online.course.bankdemo.model.customer.request;

import az.orient.online.course.bankdemo.model.account.request.TokenRequest;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
public class CustomerRequest implements Serializable {

    private Long id;
    private String name;
    private String surname;
    private Date dob;
    private String address;
    private String phone;
    private String pin;
    private String serialNumber;
    private String cif;
    private TokenRequest tokenRequest;

}
