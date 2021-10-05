package com.example.gestionAchat.config.audit;

import org.hibernate.envers.RevisionListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

// Listener spécifique appelé dans la gestion des audits des entités
public class AuditListener implements RevisionListener {
    /**
     * {@inheritDoc}
     */
    @Override
    public void newRevision(final Object revisionEntity) {
        final Revision revision = (Revision) revisionEntity;
        // Récupération de l'utilisateur connecté
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        revision.setUserName("imen");
    }
    }
