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

import com.portfolio.alejandro.Models.Tech;
import com.portfolio.alejandro.Service.TechService;


@RestController
@RequestMapping("/api/techs")
@CrossOrigin(origins = "https://portfolio.alejo78912.com")
public class DemoControllerTech {

    @Autowired
	 private TechService techService;

	@GetMapping
	    public ResponseEntity<List<Tech>> techs() {
	        return ResponseEntity.ok(techService.techs());
	    }

       
    private static final String SQL_INJECTION_PATTERN = ".*(\\b(SELECT|DROP|DELETE|INSERT|UPDATE|CREATE|ALTER|TRUNCATE|REVOKE|GRANT|EXEC|UNION|--|;|\\b(?:OR|AND)\\b).+).*";

    @PostMapping
    public ResponseEntity<Tech> create(@RequestBody Tech tech) {
        
        
        if (isSQLInjection(tech.getName()) || isSQLInjection(tech.getDescription()) || isSQLInjection(tech.getImage())){
            return ResponseEntity.badRequest().body(null);  
        }

        return ResponseEntity.ok(techService.saveTech(tech));
    }

    
  

    private boolean isSQLInjection(String input) {
        if (input == null) return false;
        return Pattern.compile(SQL_INJECTION_PATTERN, Pattern.CASE_INSENSITIVE).matcher(input).matches();
    }


	@DeleteMapping("/{idTech}/delete")
	public ResponseEntity<Void> delete(@PathVariable Long idTech) {
	    techService.deleteTech(idTech);
	    return ResponseEntity.noContent().build();
	    
	}
    
    @PutMapping("/{idTech}/update")
	    public ResponseEntity<Tech> update(@PathVariable Long idTech, @RequestBody Tech tech) {
	    	Tech existingTech = techService.getTechById(idTech);
	        
	        if (existingTech != null) {
	        	existingTech.setName(tech.getName());
	        	existingTech.setDescription(tech.getDescription());
                existingTech.setImage(tech.getImage());

                Tech updateTech = techService.saveTech(existingTech);
	            
	            return ResponseEntity.ok(updateTech);

	        } else {

	            return ResponseEntity.notFound().build();
	        }
	    }
}
