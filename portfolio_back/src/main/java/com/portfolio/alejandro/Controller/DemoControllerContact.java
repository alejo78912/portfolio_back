package com.portfolio.alejandro.Controller;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.alejandro.Models.Contact;
import com.portfolio.alejandro.Service.ContactService;
import com.portfolio.alejandro.Service.EmailService;



@RestController
@RequestMapping("/api/contacts")
@CrossOrigin(origins = "*")
public class DemoControllerContact {
    
     @Autowired
	 private ContactService contactService;

	 @Autowired
	 private EmailService emailService;

	@GetMapping
	    public ResponseEntity<List<Contact>> contacts() {
	        return ResponseEntity.ok(contactService.contacts());
	    }

       
    private static final String SQL_INJECTION_PATTERN = ".*(\\b(SELECT|DROP|DELETE|INSERT|UPDATE|CREATE|ALTER|TRUNCATE|REVOKE|GRANT|EXEC|UNION|--|;|\\b(?:OR|AND)\\b).+).*";

    @PostMapping("/contact")
    public ResponseEntity<Contact> create(@RequestBody Contact contact) {
        
        if (contact.getEmail() == null || !isValidEmail(contact.getEmail())) {
            return ResponseEntity.badRequest().body(null);  
        }

        if (isSQLInjection(contact.getEmail()) || isSQLInjection(contact.getMessage()) || isSQLInjection(contact.getName())) {
            return ResponseEntity.badRequest().body(null);  
        }

       
        Contact savedContact = contactService.saveContact(contact);

        String subject = "Nuevo contacto desde el formulario";
        String messageBody = String.format("Se ha recibido un nuevo contacto:\n\nNombre: %s\nCorreo: %s\nMensaje: %s", 
                                           contact.getName(), contact.getEmail(), contact.getMessage());

        emailService.sendContactEmail("av739006@gmail.com", subject, messageBody); 


        return ResponseEntity.ok(savedContact);
    }

    
    private boolean isValidEmail(String email) {

        return email != null && email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    }

    private boolean isSQLInjection(String input) {
        if (input == null) return false;
        return Pattern.compile(SQL_INJECTION_PATTERN, Pattern.CASE_INSENSITIVE).matcher(input).matches();
    }


	@DeleteMapping("/{idContact}/delete")
	public ResponseEntity<Void> delete(@PathVariable Long idContact) {
	    contactService.deleteContact(idContact);
	    return ResponseEntity.noContent().build();
	    
	}
    
    @PutMapping("/{idContact}/update")
	    public ResponseEntity<Contact> update(@PathVariable Long idContact, @RequestBody Contact contact) {
	    	Contact existingContact = contactService.geContactById(idContact);
	        
	        if (existingContact != null) {
	        	existingContact.setName(contact.getName());
	        	existingContact.setEmail(contact.getEmail());
	        	existingContact.setMessage(contact.getMessage());

	           

	            Contact updateContact = contactService.saveContact(existingContact);
	            
	            return ResponseEntity.ok(updateContact);
	        } else {

	            return ResponseEntity.notFound().build();
	        }
	    }
	    

}
