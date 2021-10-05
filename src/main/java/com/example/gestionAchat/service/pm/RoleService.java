
package com.example.gestionAchat.service.pm;

import com.example.gestionAchat.config.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.gestionAchat.entities.Role;
import  com.example.gestionAchat.repository.pm.RoleRepository;
import com.example.gestionAchat.entities.Application;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RoleService {
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    ProfileService profileService;



    @PersistenceContext(unitName = "externDSEmFactory")

    private EntityManager em;

    public Role getRoleById(final String rolename, final String application) {
        Query q = this.em.createNativeQuery("SELECT * FROM pm_role WHERE rolename =? and application =(select id from pm_application where alias =?)", Role.class).setParameter(1, rolename).setParameter(2, application);
        return (Role) q.getResultList().get(0);
    }


    public Role getRoleByIdMM(String rolename) {
        Query q = this.em.createNativeQuery("SELECT * FROM pm_role WHERE rolename ='"+rolename+"'", Role.class);
        return (Role) q.getResultList().get(0);
    }

    public List<Role> getRoleByApplication(final String application) {
        Query q = this.em.createNativeQuery("SELECT DISTINCT *  FROM pm_role WHERE application =(select id from pm_application where alias =?)", Role.class).setParameter(1, application);
        return (List<Role>) q.getResultList();
    }

    public List<Role> getRolesByProfile(final String name) {
        Query q = this.em.createNativeQuery("SELECT * FROM pm_role WHERE id in (SELECT pr.roles FROM pm_profile_role pr , pm_profile p WHERE pr.profile=p.id and p.profilename=?)", Role.class).setParameter(1, name);
        return (List<Role>) q.getResultList();
    }


    public Role autosave(String roleName, Application application) {

        Optional<Role> r = roleRepository.findByApplicationAndRolename(application, roleName);
        if (!r.isPresent()) {
            Role p = new Role();
            p.setRolename(roleName);
            p.setStatus(true);
            p.setTest(false);
            p.setAuthor("RoleWriter,SUPLOCALADMIN");
            p.setReader("RoleReader");
            p.setCreatedby("System");
            p.setDatecreate(LocalDate.now());
            p.setComment("auto create");
            p.setApplication(application);
            return roleRepository.save(p);
        }
        return r.get();
    }


    public HashMap<String, List<Role>> allRoles(HttpServletRequest request, Device global) {
        List<Role> roleList = this.roleRepository.findAll();
        List<Role> roles = new ArrayList<Role>();
        String token = request.getHeader("Authorization");
        String body = global.DecodeJWT(token.substring(token.indexOf("ey")));
        String[] list = body.split("\"authority\":\"");
        List<String> l = new ArrayList<String>();
        for (int i = 1; i < list.length; ++i) {
            l.add(list[i].substring(0, body.split("\"authority\":\"")[i].length() - 4));
        }
        for (Role r : roleList) {
            String[] prv = null;
            String[] prv2 = null;
            List<String> l2 = new ArrayList<String>();
            if ((r.getReader() != null && !r.getReader().isEmpty())
                    || (r.getAuthor() != null && !r.getAuthor().isEmpty())) {
                prv = r.getReader().split(",");
                prv2 = r.getAuthor().split(",");
                for (String p : prv) {
                    l2.add(p);
                }
                for (String p : prv2) {
                    l2.add(p);
                }
            }
            if (global.valid(l2, l)) {
                roles.add(r);
            }
        }
        HashMap<String, List<Role>> dataOut = new HashMap<String, List<Role>>();
        dataOut.put("data", roles);
        return dataOut;
    }



}
