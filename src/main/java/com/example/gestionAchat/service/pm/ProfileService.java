
package com.example.gestionAchat.service.pm;

import com.example.gestionAchat.entities.UserAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.gestionAchat.entities.Profile;
import com.example.gestionAchat.entities.Role;
import com.example.gestionAchat.repository.pm.ProfileRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.example.gestionAchat.entities.Application;

@Transactional
@Service
public class ProfileService {


    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @PersistenceContext(unitName = "externDSEmFactory")
    @Autowired
    private EntityManager em;

    public Profile getProfilesById(String profilename, String application) {
        Query q = this.em.createNativeQuery("SELECT * FROM pm_profile WHERE profilename =? and application =(select id from pm_application where alias =?)", Profile.class).setParameter(1, profilename).setParameter(2, application);
        return (Profile) q.getResultList().get(0);
    }

    public  List<BigInteger> getProfilesByNameProfile(Long profileid) {
        //Query q = this.em.createNativeQuery("SELECT roles FROM mpm2021.pm_profile_role where profile="+profileid);
        Query q = this.em.createNativeQuery("SELECT roles FROM pm_profile_role where profile="+profileid);
        return (List<BigInteger>) q.getResultList();
    }
    public List<Profile> getProfilesByApplication(String application) {
        Query q = this.em.createNativeQuery("SELECT * FROM pm_profile WHERE application =(select id from pm_application where alias =?)", Profile.class).setParameter(1, application);
        return (List<Profile>) q.getResultList();
    }

    public List<Profile> getProfilesByName(String name) {
        Query q = this.em.createNativeQuery("SELECT * FROM pm_profile WHERE profilename = ? ", Profile.class).setParameter(1, name);
        return (List<Profile>) q.getResultList();
    }

    public List<Profile> getProfilesByUser(String name, String alias) {
        Query q = this.em.createNativeQuery("SELECT * FROM pm_profile WHERE (profilename,alias) in (SELECT profilename,alias FROM pm_user_access WHERE users=?and alias =?)", Profile.class).setParameter(1, name).setParameter(2, alias);
        return (List<Profile>) q.getResultList();
    }

    public List<Profile> getProfilesByUserMM(Long iduser) {
        List<Profile> result= new ArrayList<Profile>();
        String query= "SELECT * FROM pm_user_access ";
        System.out.println("query::"+ query);
        Query q = this.em.createNativeQuery(query, UserAccess.class);
        List<UserAccess> liste= q.getResultList();

        if(liste.size()!=0) {
            for (int i = 0; i < liste.size(); i++)
            {System.out.println("userofaccess::"+liste.get(i).getUser().getId()+"iduser::"+iduser+"::");
            {  if (liste.get(i).getUser().getId()-iduser==0)
            {System.out.println("::"+liste.get(i).getProfile().getProfilename());
                    result.add(liste.get(i).getProfile());}

            }}
            return result;
        }
        else
            return null;
    }



    public Profile autosave(String profileName, Application application, List<Role> roles) {

        Optional<Profile> r = profileRepository.findByApplicationAndProfilename(application, profileName);
        if (!r.isPresent()) {
            Profile p = new Profile();
            p.setProfilename(profileName);
            p.setStatus(true);
            p.setTest(false);
            p.setAuthor("ProfileWriter,SUPLOCALADMIN");
            p.setReader("ProfileReader");
            p.setCreatedby("System");
            p.setDatecreate(LocalDate.now());
            p.setComment("auto create");
            p.setApplication(application);
            p.setRoles(roles);
            return profileRepository.save(p);
        }
        return r.get();
    }
}
