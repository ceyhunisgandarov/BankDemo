package az.orient.online.course.bankdemo.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import java.util.Date;

@Entity
@Getter
@Setter
@DynamicInsert
@Builder
@Table(name = "tbl_user")
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String fullName;
    private String password;
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Date createdAt;
    @ColumnDefault(value = "1")
    private Integer active;


}
