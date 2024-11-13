package com.portfolio.alejandro.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.portfolio.alejandro.Models.Contact;
import com.portfolio.alejandro.Repository.IContactRepository;

@Service
public class ContactService {
    
    @Autowired
    private IContactRepository contactRepository;


    public List<Contact> contacts(){
        return contactRepository.findAll();
    }

    public Contact saveContact(Contact contact){
        return  contactRepository.save(contact);
    }

    public void deleteContact(Long idContact){
        contactRepository.deleteById(idContact);    
    }

    public Contact geContactById (Long idContact){
        return contactRepository.findById(idContact).orElse(null);
    }
}
