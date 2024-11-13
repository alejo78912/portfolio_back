package com.portfolio.alejandro.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.portfolio.alejandro.Models.Language;
import com.portfolio.alejandro.Repository.ILanguageRepository;

@Service
public class LanguageService {

    @Autowired
    private ILanguageRepository languageRepository;

     public List<Language> languages(){
        return languageRepository.findAll();
    }

    public Language saveLanguage(Language language){
        return  languageRepository.save(language);
    }

    public void deleteLanguage(Long idLanguage){
        languageRepository.deleteById(idLanguage);    
    }

    public Language getlanguageById (Long idLanguage){
        return languageRepository.findById(idLanguage).orElse(null);
    }
}
