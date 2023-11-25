package az.orient.online.course.bankdemo.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.boot.model.naming.ImplicitBasicColumnNameSource;

import java.util.Date;


@Entity
@Getter
@Setter
@DynamicInsert
@Builder
@Table(name = "tbl_transaction")
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "from_account_id")
    private Account fromAccount;

    private String toAccount;
    private String note;
    private Double amount;
    private String iban;
    private String currency;

    @CreationTimestamp
    private Date createdAt;

    @ColumnDefault(value = "1")
    private Integer active;

}
