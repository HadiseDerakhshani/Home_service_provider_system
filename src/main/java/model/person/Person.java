package model.person;

import lombok.Data;
import model.enums.UserState;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
@Data
@Entity
@MappedSuperclass
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int  id;
    private String firstName;
    private String lastName;
    @Column(unique = true )
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserState userState;
    @CreationTimestamp
    private Date dateRegister;
    private double Credit;
}
