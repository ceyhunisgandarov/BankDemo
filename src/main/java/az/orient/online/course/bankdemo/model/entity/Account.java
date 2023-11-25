package az.orient.online.course.bankdemo.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@DynamicInsert
@Builder
@Table(name = "tbl_account")
@AllArgsConstructor
@NoArgsConstructor
public class Account implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String accountNo;
    private String iban;
    private String currency;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Date createdAt;

    @ColumnDefault(value = "1")
    private Integer active;

}
