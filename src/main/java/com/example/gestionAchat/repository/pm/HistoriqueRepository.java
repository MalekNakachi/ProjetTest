package com.example.gestionAchat.repository.pm;

import com.example.gestionAchat.entities.Historique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface HistoriqueRepository extends JpaRepository<Historique, Integer>, JpaSpecificationExecutor<Historique> {
	
}
