package com.bartmont.VetClinicREST.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    @Column(unique = true)
    private String customerID;
    private String customerPIN;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "customerId")
    private Collection<Appointment> appointments;

}
