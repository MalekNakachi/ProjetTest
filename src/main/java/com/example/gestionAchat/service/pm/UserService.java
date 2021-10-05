package com.example.gestionAchat.service.pm;

import com.example.gestionAchat.config.Device;


import com.example.gestionAchat.domain.UsersActifs;
import com.example.gestionAchat.domain.UsersNonActifs;
import com.example.gestionAchat.web.rest.errors.BadRequestAlertException;
import com.example.gestionAchat.web.rest.pm.OrgaController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ldap.NamingException;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//import com.example.MaiManager.web.rest.errors.BadRequestAlertException;
import com.example.gestionAchat.entities.*;

import com.example.gestionAchat.repository.pm.LdapConfigRepository;
import com.example.gestionAchat.repository.pm.PasswordTokenRepository;
import com.example.gestionAchat.repository.pm.UserRepository;

import com.example.gestionAchat.repository.pm.ExpandRepository;


import javax.naming.directory.Attributes;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.*;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

@Service
@Transactional
public class UserService {

    @Autowired
    OrgaController orgaController;

    @PersistenceContext(unitName = "externDSEmFactory")
    @Autowired
    private EntityManager em;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRepository UserRepository;
    @Autowired
    private ExpandRepository expandRepository;
    @Autowired
    private UserAccessService AccessService;
    @Autowired
    private RoleService RoleService;
    @Autowired
    private LdapConfigRepository ldapRepository;
    @Autowired
    private PasswordTokenRepository passwordTokenRepository;


    @Value("${userlocal.role}")
    private String localrole;
    @Value("${userlocal.name}")
    private String localusername;
    @Value("${userlocal.password}")
    private String localpassword;

    private String app;
    private PasswordEncoder passwordEncoder;
    PasswordEncoder encoder;
    private final String RoleLocalAdmin = System.getenv("SYS_ADMIN_ROLE");

    public void setapp(String Napp) {
        this.app = Napp;
    }

    public String getapp() {
        return this.app;
    }

    public BCryptPasswordEncoder PasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public LdapTemplate LDAP(ConfigLdap configLdap) throws Exception {
        LdapContextSource contextSource = new LdapContextSource();

        contextSource.setUrl(configLdap.getUrl());
        contextSource.setBase(configLdap.getBase());
        contextSource.setUserDn(configLdap.getUserDn());
        contextSource.setPassword(configLdap.getPassword());
        contextSource.afterPropertiesSet();
        contextSource.setReferral("follow");
        LdapTemplate ldap = new LdapTemplate(contextSource);
        ldap.afterPropertiesSet();
        return ldap;
    }


    public User save(String mail, User user) {

        this.encoder = this.PasswordEncoder();
        LocalDate date = LocalDate.now();
        user.setAuthor("UserWriter,SUPLOCALADMIN");
        user.setReader("UserReader");
        if ((user.getId() != null) || user.getSamaccountname() != null) {
            Optional<User> users = this.userRepository.findBySamaccountname(user.getSamaccountname());
            if (users.isPresent()) {
                // if(user.getId() == null )  throw new BadRequestAlertException("User exist deja", "User exist deja", "User exist deja");
                user.setDateupdate(date);
                user.setModifiedby(mail);
                return userRepository.save(user);
            } else {
                user.setCreatedby(mail);
                user.setDatecreate(date);
                User u = userRepository.save(user);
                PasswordResetToken token = new PasswordResetToken();
                token.setToken(UUID.randomUUID().toString());
                token.setUseriD(u);
                token.setExpiryDate(2);
                passwordTokenRepository.save(token);
                return u;
            }
        }
        throw new BadRequestAlertException("User is not present", "User is not present", "User is not present");
    }

    public boolean authentify(String username, String password) throws Exception {
        LocalDate date = LocalDate.now();
        if (username.equals(this.localusername) && password.equals(this.localpassword)) {
            return true;
        }
        Optional<User> user = this.userRepository.findBySamaccountname(username);
        if (user.isPresent()) {
         //   System.out.println("user Present :  " + username);
            User u = user.get();
            if (u.getConfigLdap() != null) {
                System.out.println("user ldap :  ");
                LdapTemplate ldap = this.LDAP(u.getConfigLdap());
                User userldap = getAllPersons(username, ldap);
                String distinguishedName = userldap.getDistinguishedname();
              //  System.out.println("user distinguishedName :  " + distinguishedName);
                String employeeType = userldap.getEmployeetype();
                if (employeeType != null && !distinguishedName.equals(null)) {
                  //  System.out.println("not null:  ");
                    if (!distinguishedName.equals(u.getDistinguishedname()) || !employeeType.equals(u.getEmployeetype())) {
                        System.out.println("user update :  ");
                        userldap.setId(u.getId());
                        userldap.setConfigLdap(u.getConfigLdap());
                        userldap.setComment("update lors d'authentification");
                        userldap.setModifiedby("root");
                        userldap.setDateupdate(date);
                        UserRepository.save(userldap);
                    }
                }
              //  System.out.println("user etape 2 :  ");
                if (ldap.authenticate("", (new EqualsFilter("sAMAccountName", username)).encode(), password)) {
                 //   System.out.println("sAMAccountName :  ");
                    return ldap.authenticate("", (new EqualsFilter("sAMAccountName", username)).encode(), password);
                } else if (ldap.authenticate("", (new EqualsFilter("mail", username)).encode(), password)) {
                 //   System.out.println("mail :  ");
                    return ldap.authenticate("", (new EqualsFilter("mail", username)).encode(), password);
                }
             //   System.out.println("password errone");
            } else {
                if (u.isStatus()) {
//                    this.passwordEncoder = this.PasswordEncoder();
             //       System.out.println("this.passwordEncoder.matches(password, u.getPassword() =====>    " + password +"#"+ u.getPassword());

               //     System.out.println(u.getPassword());
               //     System.out.println(u.getPassword().isEmpty());
              //      System.out.println(this.passwordEncoder.matches(password, u.getPassword()));

                   Boolean b = u.getPassword() != null && !u.getPassword().isEmpty()
                           && (password.equals(u.getPassword()));
                    System.out.println("b==>"+b);

                    return b;

                } else throw new BadRequestAlertException("Inactive User", "Authentification", "");
            }
        } else {
            List<ConfigLdap> listLdap = ldapRepository.findAll();
            for (ConfigLdap c : listLdap) {
                LdapTemplate ldap = this.LDAP(c);
                if (ldap.authenticate("", (new EqualsFilter("sAMAccountName", username)).encode(), password)) {
                    User userldap = getAllPersons(username, ldap);
                    userldap.setConfigLdap(c);
                    userldap.setDatecreate(date);
                    userldap.setCreatedby("root");
                    userldap.setComment("created lors d'authentification");
                    UserRepository.save(userldap);
                    return ldap.authenticate("", (new EqualsFilter("sAMAccountName", username)).encode(), password);
                } else if (ldap.authenticate("", (new EqualsFilter("mail", username)).encode(), password)) {
                    User userldap = getAllPersons(username, ldap);
                    userldap.setConfigLdap(c);
                    userldap.setDatecreate(date);
                    userldap.setCreatedby("root");
                    userldap.setComment("created lors d'authentification");
                    UserRepository.save(userldap);
                    return ldap.authenticate("", (new EqualsFilter("mail", username)).encode(), password);
                }
            }
        }
        return false;
    }

    public listePrivillegeUser getUser(String username) {

        listePrivillegeUser liste = new listePrivillegeUser();
        if (username.equals(this.localusername)) {
            liste.setApplication(this.getapp());
            liste.setProfiles("");
            liste.setOrgaPrivillege(new ArrayList<String>());
            liste.setRoles(new ArrayList<String>(Arrays.asList(this.localrole)));
            return liste;
        }
        List<String> profileList = this.AccessService.getProfileByUserApp(username, this.getapp());
        liste.setApplication(this.getapp());
        liste.setUser(username);
        if (!profileList.isEmpty()) {
            liste.setProfiles(profileList.get(0));
            List<Role> roles = this.RoleService.getRolesByProfile(profileList.get(0));
            List<String> roleName = new ArrayList<String>();
            for (Role role : roles) {
                roleName.add(role.getRolename());
            }
            liste.setRoles(roleName);
        } else {
            liste.setProfiles("");
            liste.setRoles(new ArrayList<String>());
        }
        List<String> OrgaPrivillege = new ArrayList<String>();
        Long id = this.UserRepository.findBySamaccountname(username).get().getId();
        List<Expand> priv = this.expandRepository.findAllByUserId(id.intValue());
        for (Expand ex : priv) {
            if (ex.getDepartement() != null) {
                OrgaPrivillege.add(ex.getDepartement());
            }
            if (ex.getGroupe() != null) {
                OrgaPrivillege.add(ex.getGroupe());
            }

            liste.setOrgaPrivillege(OrgaPrivillege);
        }

        return liste;
    }

    public String distinguishedNames(String username, LdapTemplate ldapTemplate) {

        try {
            List<String> distinguishedNames = ldapTemplate.search(
                    query().where("objectclass").is("person")
                            .and(query().where("sAMAccountName").is(username).or(query().where("mail").is(username))),
                    (AttributesMapper<String>) attrs -> attrs.get("distinguishedName").get().toString());

            if (distinguishedNames.isEmpty()) {
                throw new UsernameNotFoundException("User not recognized in LDAP");
            }

            if (distinguishedNames.get(0) != null) {
                return distinguishedNames.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updatePassword(String password, Long userId) {

        userRepository.updatePassword(password, userId);
    }

    public User getAllPersons(String username, LdapTemplate ldapTemplate) {
        return ldapTemplate.search(
                query().where("objectclass").is("person")
                        .and(query().where("sAMAccountName").is(username).or(query().where("mail").is(username))),
                new PersonAttributesMapper()).get(0);
    }

    public List<String> userProfilesByApplication(String username, String application) {
        List<String> resultVal = new ArrayList<String>();
        List<Profile> profiles = profileService.getProfilesByUser(username, application);
        List<String> list = new ArrayList<>();

        profiles.forEach(pro -> {
            String name = pro.getProfilename();
            list.add(name);
            return;
        });
        return resultVal;
    }

    public void createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken myToken = new PasswordResetToken(token, user);
        passwordTokenRepository.save(myToken);
    }

    public List<User> getMembreHasNoAccess() {
        Query q = this.em
                .createNativeQuery("SELECT count(*) FROM pm_users WHERE id not in (SELECT users FROM pm_user_access)");
        return q.getResultList();
    }

    public List<User> getUserByApplication(String app) {
        Query q = this.em.createNativeQuery(
                "SELECT * FROM pm_users WHERE id in (SELECT users FROM pm_user_access WHERE application =(SELECT id FROM pm_application WHERE alias=?) )",
                User.class).setParameter(1, app);
        return (List<User>) q.getResultList();
    }

    public List<User> getUserByProfile(String profile) {
        Query q = this.em.createNativeQuery(
                "SELECT * FROM pm_users WHERE id in (SELECT users FROM pm_user_access WHERE profile =(SELECT id FROM pm_profile WHERE profilename=?) )",
                User.class).setParameter(1, profile);
        return (List<User>) q.getResultList();
    }

    private class PersonAttributesMapper implements AttributesMapper<User> {
        public User mapFromAttributes(Attributes attrs) throws NamingException, javax.naming.NamingException {
            User person = new User();
            person.setDistinguishedname((String) attrs.get("distinguishedName").get());
            person.setTest(false);
            person.setStatus(true);
            person.setAuthor("UserWriter," + RoleLocalAdmin);
            person.setReader("UserReader");

            if (attrs.get("description") != null)
                person.setDescription((String) attrs.get("description").get());
            if (attrs.get("displayName") != null)
                person.setDisplayname((String) attrs.get("displayName").get());
            if (attrs.get("employeeType") != null)
                person.setEmployeetype((String) attrs.get("employeeType").get());
            if (attrs.get("mail") != null)
                person.setMail((String) attrs.get("mail").get());
            if (attrs.get("manager") != null)
                person.setManager((String) attrs.get("manager").get());
            if (attrs.get("memberOf") != null) {
                String membre = "";
                String[] tempArray;
                String delimiter = ";";
                tempArray = ((String) attrs.get("memberOf").get()).split(delimiter);
                for (int i = 0; i < tempArray.length; i++)
                    membre = membre + tempArray[i].substring(3, tempArray[i].indexOf(",")) + ",";
                person.setMemberof(membre);
            }
            if (attrs.get("sAMAccountName") != null)
                person.setSamaccountname((String) attrs.get("sAMAccountName").get());
            if (attrs.get("thumbnailPhoto") != null) {
                byte[] photo = (byte[]) attrs.get("thumbnailPhoto").get();
                FileOutputStream os;
                try {
                    os = new FileOutputStream(System.getenv("var_name_PROFILE_PHOTO_DIRECTORY")
                            + attrs.get("sAMAccountName").get() + ".png");
                    os.write(photo);
                    os.flush();
                    os.close();
                    person.setThumbnailphoto(attrs.get("sAMAccountName").get() + ".png");
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            return person;
        }
    }


    public User autosave(String userName) {

        Optional<User> r = userRepository.findBySamaccountname(userName);
        if (!r.isPresent()) {
            this.encoder = this.PasswordEncoder();
            User p = new User();
            p.setSamaccountname(userName);
            p.setMail(userName + "@epost.tn");
            p.setDisplayname(userName);
            p.setStatus(true);
            p.setTest(false);
            p.setAuthor("UserWriter," + RoleLocalAdmin);
            p.setReader("UserReader");
            p.setPassword(this.encoder.encode("PicoSoft2019"));
            p.setCreatedby("System");
            p.setDatecreate(LocalDate.now());
            p.setComment("auto create");
            return userRepository.save(p);
        }
        return r.get();
    }

    public HashMap<String, List<User>> allUsers(HttpServletRequest request, Device global) {
    List<User> userList = this.UserRepository.findAll();
    List<User> users = new ArrayList<User>();
    String token = request.getHeader("Authorization");
    String body = global.DecodeJWT(token.substring(token.indexOf("ey")));
    String[] list = body.split("\"authority\":\"");
    List<String> l = new ArrayList<String>();
        for (int i = 1; i < list.length; ++i) {
        l.add(list[i].substring(0, body.split("\"authority\":\"")[i].length() - 4));
    }
    String[] prv = null;
    String[] prv2 = null;
    List<String> l2 = new ArrayList<String>();
        for (User u : userList) {
        if ((u.getReader() != null && !u.getReader().isEmpty())
                || (u.getAuthor() != null && !u.getAuthor().isEmpty())) {
            prv = u.getReader().split(",");
            prv2 = u.getAuthor().split(",");
        }
        for (String p : prv) {
            l2.add(p);
        }
        for (String p : prv2) {
            l2.add(p);
        }
        if (global.valid(l2, l)) {
            users.add(u);
        }
    }
    HashMap<String, List<User>> dataOut = new HashMap<String, List<User>>();
        dataOut.put("data", users);
        return dataOut;
}


    public BigInteger countallUsersActifsLazy () {

        String allquery = "SELECT count(*) from `usersview` ";

        Query q = (Query) em.createNativeQuery(allquery);

        List<BigInteger> count = (List<BigInteger>) q.getResultList();

        return count.get(0);
    }

    public BigInteger countallUsersNonActifsLazy () {

        String allquery = "SELECT count(*) from `usersviewnonactif` ";

        Query q = (Query) em.createNativeQuery(allquery);

        List<BigInteger> count = (List<BigInteger>) q.getResultList();

        return count.get(0);
    }

    public List<UsersActifs> allUsersActifsLazy (int skip, int take, String filter, String orderBy) {

       String allquery = "SELECT * from `usersview` ";

        List<UsersActifs> retour = new ArrayList<UsersActifs>();
       String part0 = " where 1 ";


                allquery += part0;
                String ch = "";
                String w = "";
                //   System.out.println("filter " + filter);

                if (!filter.equals("undefined")) {
                    String x = filter;
//            String[] T = x.split(",and,");
                    String[] T = x.split(",XR002,");

                    for (int i = 0; i < T.length; i++) {
                        if (T[i].contains(",or,")) {
                            w = T[i];
                            String[] R = w.split(",or,");
                            String v0 = R[0];
                            String[] R00 = v0.split(",");
                            // System.out.println("R00   " + R00);
                            if (R00[1].contains("XR001")) {
                                ch += " AND " + "(" + R00[0] + " like '%" + R00[2].trim() + "%'";
                            } else {
                                ch += " AND " + "(" + R00[0] + " " + R00[1] + " '" + R00[2] + "'";
                            }

                            for (int y = 1; y < R.length; y++) {
//                    for (int y = 0; y < R.length; y++) {
                                String v = R[y];
                                String[] R0 = v.split(",");
                                if (y != R.length - 1) {

                                    if (R0[1].contains("XR001")) {
                                        ch += " OR " + R0[0] + " like '%" + R0[2].trim() + "%'";
                                    } else {

                                        ch += " OR " + R0[0] + " " + R0[1] + " '" + R0[2] + "'";
                                    }

                                } else {
                                    if (R0[1].contains("XR001")) {
                                        ch += " OR " + R0[0] + " like '%" + R0[2].trim() + "%'" + ")";
                                    } else {

                                        ch += " OR " + R0[0] + " " + R0[1] + " '" + R0[2] + "'" + ")";
                                    }
                                }

                            }
                        } else {
                            w = T[i];

                            String[] R = w.split(",");

                            if (R[1].contains("XR001")) {
                                ch += " AND " + R[0] + " like '%" + R[2].trim() + "%'";
                            } else {
                                ch += " AND " + R[0] + " " + R[1] + " '" + R[2] + "'";

                            }
                        }
                    }
                }

                allquery += ch;
                if (orderBy.equals("undefined")) {
                    allquery += " ORDER BY userId asc ";
                }
                if (!orderBy.equals("undefined")) {
                    allquery += " ORDER BY " + orderBy;
                }
                allquery += " LIMIT " + take + " OFFSET " + skip;

                System.out.println("allquery::"+allquery);
               Query q = (Query) em.createNativeQuery(allquery, UsersActifs.class);



                retour = ( List<UsersActifs>) q.getResultList();

                return retour;
            }


    public List<UsersNonActifs> allUsersNonActifsLazy (int skip, int take, String filter, String orderBy) {





        String allquery = "SELECT * from `usersviewnonactif` ";

        List<UsersNonActifs> retour = new ArrayList<UsersNonActifs>();
        String part0 = " where 1 ";


        allquery += part0;
        String ch = "";
        String w = "";
        //   System.out.println("filter " + filter);

        if (!filter.equals("undefined")) {
            String x = filter;
//            String[] T = x.split(",and,");
            String[] T = x.split(",XR002,");

            for (int i = 0; i < T.length; i++) {
                if (T[i].contains(",or,")) {
                    w = T[i];
                    String[] R = w.split(",or,");
                    String v0 = R[0];
                    String[] R00 = v0.split(",");
                    // System.out.println("R00   " + R00);
                    if (R00[1].contains("XR001")) {
                        ch += " AND " + "(" + R00[0] + " like '%" + R00[2].trim() + "%'";
                    } else {
                        ch += " AND " + "(" + R00[0] + " " + R00[1] + " '" + R00[2] + "'";
                    }

                    for (int y = 1; y < R.length; y++) {
//                    for (int y = 0; y < R.length; y++) {
                        String v = R[y];
                        String[] R0 = v.split(",");
                        if (y != R.length - 1) {

                            if (R0[1].contains("XR001")) {
                                ch += " OR " + R0[0] + " like '%" + R0[2].trim() + "%'";
                            } else {

                                ch += " OR " + R0[0] + " " + R0[1] + " '" + R0[2] + "'";
                            }

                        } else {
                            if (R0[1].contains("XR001")) {
                                ch += " OR " + R0[0] + " like '%" + R0[2].trim() + "%'" + ")";
                            } else {

                                ch += " OR " + R0[0] + " " + R0[1] + " '" + R0[2] + "'" + ")";
                            }
                        }

                    }
                } else {
                    w = T[i];

                    String[] R = w.split(",");

                    if (R[1].contains("XR001")) {
                        ch += " AND " + R[0] + " like '%" + R[2].trim() + "%'";
                    } else {
                        ch += " AND " + R[0] + " " + R[1] + " '" + R[2] + "'";

                    }
                }
            }
        }

        allquery += ch;
        if (orderBy.equals("undefined")) {
            allquery += " ORDER BY userId asc ";
        }
        if (!orderBy.equals("undefined")) {
            allquery += " ORDER BY " + orderBy;
        }
        allquery += " LIMIT " + take + " OFFSET " + skip;

        System.out.println("allquery::"+allquery);
        Query q = (Query) em.createNativeQuery(allquery, UsersNonActifs.class);



        retour = ( List<UsersNonActifs>) q.getResultList();

        return retour;
    }


}
