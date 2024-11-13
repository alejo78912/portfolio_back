package com.portfolio.alejandro.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.portfolio.alejandro.Models.Contact;

@Repository
public interface IContactRepository extends JpaRepository <Contact, Long>{
    
}
