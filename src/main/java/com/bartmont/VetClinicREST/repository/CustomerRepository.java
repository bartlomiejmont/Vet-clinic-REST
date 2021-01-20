package com.bartmont.VetClinicREST.repository;

import com.bartmont.VetClinicREST.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByCustomerID(String customerID);

}
