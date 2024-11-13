package com.portfolio.alejandro.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.portfolio.alejandro.Models.Tech;

@Repository
public interface ITechRepository extends JpaRepository <Tech, Long>{
    
}
