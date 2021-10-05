package com.example.gestionAchat.repository.pm;

import com.example.gestionAchat.entities.HistoriqueDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface HistoriqueDetailsRepository extends JpaRepository<HistoriqueDetails, Integer>, JpaSpecificationExecutor<HistoriqueDetails> {
	
}
