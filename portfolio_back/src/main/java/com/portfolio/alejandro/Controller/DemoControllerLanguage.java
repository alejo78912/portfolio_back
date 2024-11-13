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

import com.portfolio.alejandro.Models.Language;
import com.portfolio.alejandro.Service.LanguageService;


@RestController
@RequestMapping("/api/languages")
@CrossOrigin(origins = "https://portfolio.alejo78912.com")
public class DemoControllerLanguage {
 
     @Autowired
	 private LanguageService languageService;

	@GetMapping
	    public ResponseEntity<List<Language>> laguages() {
	        return ResponseEntity.ok(languageService.languages());
	    }

       
    private static final String SQL_INJECTION_PATTERN = ".*(\\b(SELECT|DROP|DELETE|INSERT|UPDATE|CREATE|ALTER|TRUNCATE|REVOKE|GRANT|EXEC|UNION|--|;|\\b(?:OR|AND)\\b).+).*";

    @PostMapping
    public ResponseEntity<Language> create(@RequestBody Language language) {
        
        
        if (isSQLInjection(language.getName()) || isSQLInjection(language.getLevel()) || isSQLInjection(language.getProficiency())){
            return ResponseEntity.badRequest().body(null);  
        }

		if(language.getProficiency().contains("%")!= true){
			 return ResponseEntity.badRequest().body(null);  
		}

        return ResponseEntity.ok(languageService.saveLanguage(language));
    }

    
  

    private boolean isSQLInjection(String input) {
        if (input == null) return false;
        return Pattern.compile(SQL_INJECTION_PATTERN, Pattern.CASE_INSENSITIVE).matcher(input).matches();
    }


	@DeleteMapping("/{idLanguage}/delete")
	public ResponseEntity<Void> delete(@PathVariable Long idLanguage) {
	    languageService.deleteLanguage(idLanguage);
	    return ResponseEntity.noContent().build();
	    
	}
    
    @PutMapping("/{idLanguage}/update")
	    public ResponseEntity<Language> update(@PathVariable Long idLanguage, @RequestBody Language language) {
	    	Language existingLanguage = languageService.getlanguageById(idLanguage);
	        
	        if (existingLanguage != null) {
	        	existingLanguage.setName(language.getName());
	        	existingLanguage.setLevel(language.getLevel());
                existingLanguage.setProficiency(language.getProficiency());

                Language updateLanguage = languageService.saveLanguage(language);
	            
	            return ResponseEntity.ok(updateLanguage);
	        } else {

	            return ResponseEntity.notFound().build();
	        }
	    }
}
