package com.portfolio.alejandro.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name ="Language")
public class Language {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idLanguage")
    private long idLanguage;

    @Column(name = "name")
    private String name;

    
    @Column(name = "level")
    private String level;


    @Column(name = "proficiency")
    private String proficiency;

    public Language() {
    }

    public long getIdLanguage() {
        return idLanguage;
    }

    public void setIdLanguage(long idLanguage) {
        this.idLanguage = idLanguage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getProficiency() {
        return proficiency;
    }

    public void setProficiency(String proficiency) {
        this.proficiency = proficiency;
    }
}
