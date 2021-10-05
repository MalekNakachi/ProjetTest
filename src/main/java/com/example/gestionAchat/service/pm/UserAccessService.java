
package com.example.gestionAchat.service.pm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.gestionAchat.entities.Profile;
import com.example.gestionAchat.entities.User;
import com.example.gestionAchat.entities.UserAccess;
import com.example.gestionAchat.repository.pm.AppRepository;
import com.example.gestionAchat.repository.pm.UserAccessRepository;
import com.example.gestionAchat.entities.Application;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class UserAccessService {
    @PersistenceContext(unitName = "externDSEmFactory")
    @Autowired
    private EntityManager em;
    @Autowired
    private UserAccessRepository userAccessRepository;
    @Autowired
    private com.example.gestionAchat.repository.pm.UserRepository UserRepository;
    @Autowired
    private AppRepository appRep;
    @Autowired
    private com.example.gestionAchat.service.pm.ProfileService profileService;

    public UserAccess getAccessById1(final String profilename, final String application, final String user) {
        final Query q = this.em.createNativeQuery("SELECT ua.id,ua.author,ua.comment,ua.createdby,ua.datecreate,ua.dateupdate,ua.modifiedby,ua.reader,ua.status,ua.test,ua.profile,ua.application,ua.users FROM pm_user_access ua inner join pm_profile p on ua.profile=p.id  inner join pm_application app on ua.application=app.id inner join pm_users us on ua.users=us.id where p.profilename=? and app.alias=? and  us.samaccountname=? ", UserAccess.class).setParameter(1, profilename).setParameter(2, application).setParameter(3, user);
        return (UserAccess) q.getResultList().get(0);
    }

    public UserAccess getAccessById(final String application, final String user) {
        final Query q = this.em.createNativeQuery("SELECT * FROM pm_user_access WHERE application=(select id from pm_application where alias =?) and users =(select id from pm_users where samaccountname = ?)", UserAccess.class).setParameter(1, application).setParameter(2, user);
        return (UserAccess) q.getResultList().get(0);
    }

    public List<UserAccess> getUserAccessById(final String application, final String user) {
        final Query q = this.em.createNativeQuery("SELECT * FROM pm_user_access WHERE application=(select id from pm_application where alias =?) and users =(select id from pm_users where samaccountname = ?)", UserAccess.class).setParameter(1, application).setParameter(2, user);
        return (List<UserAccess>) q.getResultList();
    }
    public List<UserAccess> getAccessByApplication(final String application) {
        final Query q = this.em.createNativeQuery("SELECT * FROM pm_user_access WHERE application =(select id from pm_application where alias =?)", UserAccess.class).setParameter(1, application);
        return (List<UserAccess>) q.getResultList();
    }

    public List<UserAccess> getAccessByUser(final String user) {
        final Query q = this.em.createNativeQuery("SELECT * FROM pm_user_access WHERE users=(select id from pm_users where samaccountname = ?)", UserAccess.class).setParameter(1, user);
        return (List<UserAccess>) q.getResultList();
    }

    public List<String> getProfileByUserApp(final String user, final String application) {
        final Query q = this.em.createNativeQuery("SELECT p.profilename FROM pm_user_access a , pm_profile p WHERE a.profile=p.id and a.users=(select id from pm_users where samaccountname = ?) and a.application =(select id from pm_application where alias =?)").setParameter(1, user).setParameter(2, application);
        return (List<String>) q.getResultList();
    }

  public List<UserAccess> getAllUsersByProfile(Profile profile) {
         return userAccessRepository.findAllByProfile(profile);
  }


//
//    public Object getInfoAccess(String user, String application) {
//        final Query q = this.em.createNativeQuery("SELECT p.profilename,u.samaccountname,u.mail  FROM pm_user_access a , pm_profile p , pm_users u WHERE a.profile=p.id and a.users=u.id and u.samaccountname = ? and a.application =(select id from pm_application where alias =?) for json auto").setParameter(1, user).setParameter(2, application);
//        return q.getResultList().get(0);
//    }

    public Optional<UserAccess> getAccess(String alias, String samaccountname) {
        Optional<UserAccess> acces = userAccessRepository.findUserAccessById(alias, samaccountname);
        return acces;
    }

    public UserAccess addAccess(String username, UserAccess access) {
        String profileName = access.getProfile().getProfilename();
        String alias = access.getApplication().getAlias();
        String samaccountname = access.getUser().getSamaccountname();

        Optional<UserAccess> acces = userAccessRepository.findUserAccessById(alias, samaccountname);
        if (acces.isPresent()) {
            access.setId(acces.get().getId());
        }

        String A = access.getAuthor();
        String R = access.getReader();
        if (A == null) {
            A = "AccessWriter,SUPLOCALADMIN";
        } else {
            String[] aa = A.split(",");
            Boolean z = false;
            Boolean e = false;
            for (String a : aa) {
                if (a.equals("AccessWriter")) {
                    z = true;
                }
                if (a.equals("SUPLOCALADMIN")) {
                    e = true;
                }
            }
            if (!z) {
                A += ",AccessWriter";
            }
            if (!e) {
                A += ",SUPLOCALADMIN";
            }
        }
        if (R == null) {
            R = "AccessReader";
        } else {
            String[] aa = R.split(",");
            Boolean z = false;
            for (String a2 : aa) {
                if (a2.equals("AccessReader")) {
                    z = true;
                }
            }
            if (!z) {
                R += ",AccessReader";
            }
        }
        LocalDate date = LocalDate.now();
        Profile p = this.profileService.getProfilesById(profileName, alias);
        access.setAuthor(A);
        access.setReader(R);
        access.setApplication(this.appRep.findByAlias(alias).get());
        access.setProfile(p);
        access.setUser(this.UserRepository.findBySamaccountname(samaccountname).get());
        access.setCreatedby(username);
        access.setDatecreate(date);
        access.setDateupdate(date);
        return userAccessRepository.save(access);
    }

    public void deleteAccess(String profileName, String alias, String samaccountname) {
        UserAccess std = getAccessById1(profileName, alias, samaccountname);
        userAccessRepository.delete(std);
    }


    public UserAccess autosave(Profile profile, Application application, User user) {

        Optional<UserAccess> r = userAccessRepository.findUserAccessByApplicationAndUserAndProfile(application, user, profile);
        if (!r.isPresent()) {
            UserAccess p = new UserAccess();
            p.setStatus(true);
            p.setTest(false);
            p.setAuthor("AccessWriter,SUPLOCALADMIN");
            p.setReader("AccessReader");
            p.setCreatedby("System");
            p.setDatecreate(LocalDate.now());
            p.setComment("auto create");
            p.setApplication(application);
            p.setProfile(profile);
            p.setUser(user);
            return userAccessRepository.save(p);
        }
        return r.get();
    }
}
