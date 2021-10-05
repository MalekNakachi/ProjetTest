package com.example.gestionAchat.service;

import com.example.gestionAchat.domain.AclSid;
import com.example.gestionAchat.repository.AclClassRepository;
import com.example.gestionAchat.repository.AclEntryRepository;
import com.example.gestionAchat.repository.AclObjectIdentityRepository;
import com.example.gestionAchat.repository.AclSidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.ObjectIdentityRetrievalStrategyImpl;
import org.springframework.security.acls.model.ObjectIdentityRetrievalStrategy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class MyAclService {


    @Autowired
    AclEntryRepository aclEntryRepository;


    @Autowired
    AclSidRepository aclSidRepository;

    @Autowired
    AclClassRepository aclClassRepository;
    @Autowired
    AclObjectIdentityRepository aclObjectIdentityRepository;

    private ObjectIdentityRetrievalStrategy objectIdentityRetrievalStrategy = new ObjectIdentityRetrievalStrategyImpl();


    public List<AclSid> AllSid() {
        List<AclSid> aclSids = aclSidRepository.findAll();
        return aclSids;
    }


    public void SaveSid(AclSid aclSid) {

        aclSidRepository.save(aclSid);
    }
}
