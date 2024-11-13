package com.portfolio.alejandro.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.portfolio.alejandro.Models.Language;

@Repository
public interface ILanguageRepository extends JpaRepository <Language, Long>{
    
}
