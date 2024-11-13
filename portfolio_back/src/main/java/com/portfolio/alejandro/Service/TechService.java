package com.portfolio.alejandro.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.portfolio.alejandro.Models.Tech;
import com.portfolio.alejandro.Repository.ITechRepository;


@Service
public class TechService {

    @Autowired
    private ITechRepository techRepository;

     public List<Tech> techs(){
        return techRepository.findAll();
    }

    public Tech saveTech(Tech tech){
        return  techRepository.save(tech);
    }

    public void deleteTech(Long idTech){
        techRepository.deleteById(idTech);    
    }

    public Tech getTechById (Long idTech){
        return techRepository.findById(idTech).orElse(null);
    }
}
