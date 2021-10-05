package com.example.gestionAchat.service;

import com.example.gestionAchat.entities.Group;
import com.example.gestionAchat.entities.GroupUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class GroupService {


    @PersistenceContext(unitName = "externDSEmFactory")
    @Autowired
    private EntityManager em;


    public List<Group> getGroupByUser(Long iduser) {
        List<Group> result= new ArrayList<Group>();
        String query= "SELECT * FROM pm_group_users where user_id="+iduser ;
        //  System.out.println("query::"+ query);
        Query q = this.em.createNativeQuery(query, GroupUser.class);
        List<GroupUser> liste= q.getResultList();

        if(liste.size()!=0) {
            System.out.println(liste.size());
            for (int i = 0; i < liste.size(); i++)
            {  if (liste.get(i).getUser().getId() == iduser)
                 System.out.println("::"+liste.get(i).getUser().getId());
                result.add(liste.get(i).getGroup());

            }
            return result;
        }
        else
            return null;
    }



}
