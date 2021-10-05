package com.example.gestionAchat.domain;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class GroupUserPK implements Serializable {


        protected Long demande_id;
        protected Long article_id;

        public GroupUserPK() {}

        public GroupUserPK(Long demande_id, Long article_id) {
            this.demande_id = demande_id;
            this.article_id = article_id;
        }
        // equals, hashCode
    }

