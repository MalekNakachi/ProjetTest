
package com.example.gestionAchat.web.rest.pm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.example.gestionAchat.config.Device;
import com.example.gestionAchat.entities.*;
import com.example.gestionAchat.repository.pm.DepRepository;
import com.example.gestionAchat.repository.pm.ExpandRepository;
import com.example.gestionAchat.repository.pm.LdapConfigRepository;
import com.example.gestionAchat.repository.pm.UserRepository;
import com.example.gestionAchat.service.pm.RoleService;
import com.example.gestionAchat.service.pm.UserAccessService;

import javax.script.ScriptException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping({"/api"})
public class OrgaController {
    private static final Logger log;

    static {
        log = LoggerFactory.getLogger(OrgaController.class);
    }

    private final Device global;

    @Autowired
    ExpandController ex;
    @Autowired
    private DepRepository DepRepository;
    @Autowired
    private UserRepository UserRepository;
    @Autowired
    private LdapConfigRepository repository;
    @Autowired
    private ExpandRepository expandRepository;
    @Autowired
    private UserAccessService AccessService;
    @Autowired
    private RoleService RoleService;
    private List<ConfigLdap> urlbases;
    @Value("${userlocal.name}")
    private String localusername;
    @Value("${userlocal.role}")
    private String localrole;


    public OrgaController() {
        this.global = new Device();

    }

    /**
     * {@code GET  /getManagerOf/{name}} : get ManagerOf By id.<br> <br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to get ManagerOf By id.
     * @param name name param.
     * @return String.
     */
   //@PreAuthorize("hasAuthority('SUPLOCALADMIN') or hasAuthority('SysReader') or hasAuthority('SysWriter')")
    @GetMapping({"/getManagerOf/{name}"})
    public String getManagerOf(@PathVariable String name) {
        Optional<User> user = this.UserRepository.findBySamaccountname(name);
        if (user.isPresent()) {
            System.out.println("user"+user.get().getDisplayname());
            return getManagerOfPersonMM(name);
        } else {
            Optional<Department> dep = this.DepRepository.findByName(name);
            if (dep.isPresent()) {
                System.out.println("dept"+dep.get().getName());
                return ManagerOfEntity(dep.get().getDistinguishedname());
            }
        }
        return "";
    }

    @GetMapping("/sous-couvert-users/{samaaccountname}")
    public List<String> getsouscouvertusers(@PathVariable String samaaccountname) {
        log.debug("REST request to get SousCouvert : {}", samaaccountname);
        List<String> souscouverts= new ArrayList<>();
       User usermanger = this.UserRepository.findBySamaccountname(samaaccountname).get();
        String manager="";
        if (usermanger!=null)
        {  souscouverts= new ArrayList<>();
           while (manager!=null) {
               System.out.println("managerA::"+usermanger.getSamaccountname());
               manager= getALlManagerOfPersonMM(usermanger.getSamaccountname());
               System.out.println("managerB::"+manager);
               if(!manager.equals(""))
               {
               usermanger = this.UserRepository.findByDisplayname(manager);

               souscouverts.add(manager);}
               else
                   manager=null;

          }

        }

        return souscouverts;
    }

    @GetMapping("/sous-couvert-depts/{samaaccountname}")
    public List<String> getsouscouvertdepts(@PathVariable String samaaccountname) {
        log.debug("REST request to get SousCouvert : {}", samaaccountname);
        return getALlManagerOfDeptsMM(samaaccountname);


    }




    /**
     * {@code GET  /getManagerOfPerson/{name}} : get ManagerOfPerson By name.<br> <br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to get ManagerOfPerson By name.
     * @param name name param.
     * @return String.
     */
 @PreAuthorize("hasAuthority('SUPLOCALADMIN') or hasAuthority('SysReader') or hasAuthority('SysWriter')")
    @GetMapping({"/getManagerOfPerson/{name}"})
    public String getManagerOfPerson(@PathVariable String name) {
        String distinguishedname = "";
        this.urlbases = this.repository.findAll();
        Optional<User> user = this.UserRepository.findBySamaccountname(name);
        if (user.isPresent()) {
            String manager = user.get().getManager();
            if (manager == "null" || manager == null || !manager.startsWith("CN=")) {
                distinguishedname = user.get().getDistinguishedname();
                if(distinguishedname != null) {
                    String[] des = distinguishedname.split(",OU=");
                    if(des.length>0) {
                        String result = des[1];
                        Optional<Department> dep = this.DepRepository.findByName(result);
                        if (dep.isPresent()) {
                            if (!dep.get().getManagedby().equals(distinguishedname)) return dep.get().getName();
                            else return this.ManagerOfEntity(dep.get().getDistinguishedname());
                        }
                    }
                }
                return "";
            }
            String result2 = manager.substring(3, manager.indexOf(",OU="));
            return result2;
        }
        return "";
    }


    public String getManagerOfPersonMM(@PathVariable String name) {
        String distinguishedname = "";
        this.urlbases = this.repository.findAll();
        Optional<User> user = this.UserRepository.findBySamaccountname(name);
        if (user.isPresent()) {
            String displayname = user.get().getDisplayname();
            if (displayname == "null" || displayname == null || !displayname.startsWith("CN=")) {
                distinguishedname = user.get().getDistinguishedname();
                System.out.println("dis::"+distinguishedname);
                if (distinguishedname != null) {
                    String[] des = distinguishedname.split(",OU=");
                    if (des.length > 0) {
                        String result = des[1];
                        Optional<Department> dep = this.DepRepository.findByName(result);
                        if (dep.isPresent()) {
                            if (!dep.get().getManagedby().equals(distinguishedname))

                              //  return dep.get().getName();
                                return dep.get().getManagedby().substring(dep.get().getManagedby().indexOf("CN=")+3,dep.get().getManagedby().indexOf(",OU=") );
                            else
                                return this.ManagerOfEntity(dep.get().getDistinguishedname());
                        }
                    }
                }
                return "";
            }
            String result2 = displayname.substring(3, displayname.indexOf(",OU="));
            return result2;
        }


    return "";
    }
    public String getALlManagerOfPersonMM(@PathVariable String name) {
        String distinguishedname = "";
        String ss = "";
        this.urlbases = this.repository.findAll();
        Optional<User> user = this.UserRepository.findBySamaccountname(name);
        if (user.isPresent()) {
            String displayname = user.get().getDisplayname();
            if (displayname == "null" || displayname == null || !displayname.startsWith("CN=")) {
                distinguishedname = user.get().getDistinguishedname();
                System.out.println("dis::" + distinguishedname);
                if (distinguishedname != null) {
                    String[] des = distinguishedname.split(",OU=");
                    if (des.length > 0) {
                        String result = des[1];
                        Optional<Department> dep = this.DepRepository.findByName(result);
                        System.out.println("manager::"+dep.get().getManagedby());
                        System.out.println("disting::"+distinguishedname);


                        if (dep.isPresent()) {
                            if (!dep.get().getManagedby().equals(distinguishedname)) {
                                System.out.println("here");

                                ss = dep.get().getManagedby().substring(dep.get().getManagedby().indexOf("CN=") + 3, dep.get().getManagedby().indexOf(",OU="));
                                System.out.println("ss::" + ss);

                                return ss;
                            } else {
                                //  CN= IH, OU=DG, OU=Orga, DC=pico, DC=biz
                                if (dep.get().getManagedby().indexOf("OU") != -1) {
                                    String intermediaire1 = dep.get().getManagedby().substring(dep.get().getManagedby().indexOf(",OU=") +4);
                                    if (intermediaire1.indexOf("OU=") != -1) {
                                        String intermediaire2 = intermediaire1.substring(intermediaire1.indexOf(",OU=") + 4);
                                        System.out.println("int2::"+intermediaire2);
                                        if (intermediaire2.indexOf("OU=") != -1) {
                                            ss = intermediaire2.substring(0, (intermediaire2.indexOf(",OU=")));
                                            System.out.println("ss::" + ss);
                                            String urlbase = dep.get().getConfigLdap().getBase();
                                            System.out.println("urlbase"+ urlbase);
                                            if (urlbase.startsWith("OU=" + ss)) {
                                                ss = "";
                                                return ss;
                                            } else {
                                                System.out.println(":::test:::");
                                                Optional<Department> dept = this.DepRepository.findByName(ss);
                                                System.out.println("Name:::"+dept.get().getName());
                                                ss = dept.get().getManagedby().substring(dept.get().getManagedby().indexOf("CN=") + 3, dept.get().getManagedby().indexOf(",OU="));
                                                System.out.println("ssfinal"+ss);
                                                return ss;
                                            }
                                        }

                                        return ss;
                                    }
                                    return ss;
                                }
                                return ss;

                            }

                        }
                        return ss;
                    }
                    return ss;
                }
                String result2 = displayname.substring(3, displayname.indexOf(",OU="));
                return result2;
            }


            return ss;
        }
        return ss;
    }


    public List<String> getALlManagerOfDeptsMM(@PathVariable String name) {
        String user="";String chaine="", dept="";
        List<String> getsouscouvertusers=getsouscouvertusers(name);
        List<String> souscouvertsDept= new ArrayList<>();
        for(int i=0; i<getsouscouvertusers.size(); i++)
        {
            chaine = this.UserRepository.findByDisplayname(getsouscouvertusers.get(i)).getDistinguishedname().substring(
                    this.UserRepository.findByDisplayname(getsouscouvertusers.get(i)).getDistinguishedname().indexOf(",OU=")+4);
            System.out.println("chaine::"+ chaine);
            dept= chaine.substring(0, chaine.indexOf(",OU="));
            System.out.println("dept::"+ dept);
            souscouvertsDept.add(dept);
        }



        return souscouvertsDept;
    }
    /**
     * {@code GET  /getManagerOfEntity/{dn}} : get ManagerOfEntity.<br> <br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to get ManagerOfEntity.
     * @param dn dn param.
     * @return String.
     */
 @PreAuthorize("hasAuthority('SUPLOCALADMIN') or hasAuthority('SysReader') or hasAuthority('SysWriter')")
    @GetMapping({"/getManagerOfEntity/{dn}"})
    public String ManagerOfEntity(@PathVariable String dn) {
        this.urlbases = this.repository.findAll();
        Optional<Department> dep = this.DepRepository.findByDistinguishedname(dn);
        if (dep.isPresent()) {
            for (ConfigLdap ldap : urlbases) {
                if (dn.equals(ldap.getBase())) {
                    return "";
                }
            }
            String dnmanager = dep.get().getManagedby();
            String supdep = dn.split(",OU=")[1];
            Optional<Department> depmanager = this.DepRepository.findByName(supdep);
            if (depmanager.isPresent()) {
                if (!dnmanager.equals(null) && !depmanager.get().getManagedby().equals(null) && !dnmanager.equals(depmanager.get().getManagedby()))
                    return depmanager.get().getName();
                else return ManagerOfEntity(depmanager.get().getDistinguishedname());
            }
        }
        return "";
    }

    /**
     * {@code GET  /getEffectMyUserNameList/{alias}} : get EffectUserNameList.<br> <br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to get EffectUserNameList.
     * @param alias alias param.
     * @param request request param.
     * @return listePrivillegeUser.
     */
    //@Cacheable(value = "getEffectMyUserNameList")
    @GetMapping({"/getEffectMyUserNameList/{alias}"})
    public listePrivillegeUser getEffectMyUserNameList(HttpServletRequest request, @PathVariable String alias) throws IOException, ScriptException {

        System.out.println("in getEffectMyUserNameList");

        String token = request.getHeader("Authorization");
        String body = this.global.DecodeJWT(token.substring(token.indexOf("ey")));
        String name = body.substring(body.indexOf("\":\"") + 3, body.indexOf("\","));
        listePrivillegeUser liste = new listePrivillegeUser();
        if (name.equals(this.localusername)) {
            liste.setApplication(alias);
            liste.setUser(name);
            liste.setOrgaPrivillege(new ArrayList());
            liste.setRoles(new ArrayList(Arrays.asList(this.localrole)));
            return liste;
        }
        List<String> profileList = this.AccessService.getProfileByUserApp(name, alias);
        liste.setApplication(alias);
        liste.setUser(name);
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
            liste.setRoles(new ArrayList());
        }
        List<String> OrgaPrivillege = new ArrayList<String>();
        Long id = this.UserRepository.findBySamaccountname(name).get().getId();
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

    /**
     * {@code GET  /getEffectUserNameList/{name},{alias}} : get EffectUserNameList By name and alias.<br> <br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to get EffectUserNameList By name and alias.
     * @param alias alias param.
     * @param name name param.
     * @return listePrivillegeUser.
     */
   @PreAuthorize("hasAuthority('SUPLOCALADMIN') or hasAuthority('SysReader') or hasAuthority('SysWriter')")
    @GetMapping({"/getEffectUserNameList/{name},{alias}"})
    public listePrivillegeUser getEffectUserNameList(@PathVariable String name, @PathVariable String alias) {
        listePrivillegeUser liste = new listePrivillegeUser();
        if (name.equals(this.localusername)) {
            liste.setApplication(alias);
            liste.setUser(name);
            liste.setProfiles("");
            liste.setOrgaPrivillege(new ArrayList());
            liste.setRoles(new ArrayList(Arrays.asList(this.localrole)));
            return liste;
        }
        List<String> profileList = this.AccessService.getProfileByUserApp(name, alias);
        liste.setApplication(alias);
        liste.setUser(name);
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
            liste.setRoles(new ArrayList());
        }
        List<String> OrgaPrivillege = new ArrayList<String>();
        Long id = this.UserRepository.findBySamaccountname(name).get().getId();
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

    /**
     * {@code GET  /GroupDepByUser} : Group Department By User.<br> <br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to Group Department By User.
     * @param samaccountname samaccountname param.
     * @return List of String.
     */
   @PreAuthorize("hasAuthority('SUPLOCALADMIN') or hasAuthority('SysReader')")
    @GetMapping({"/GroupDepByUser"})
    public List<String> GroupDepByUser(@RequestParam String samaccountname) {
        Long id = this.UserRepository.findBySamaccountname(samaccountname).get().getId();
        List<String> liste = new ArrayList<String>();
        List<Expand> priv = this.expandRepository.findAllByUserId(id.intValue());
        for (Expand ex : priv) {
            if (ex.getDepartement() != null) {
                liste.add(ex.getDepartement());
            }
            if (ex.getGroupe() != null) {
                liste.add(ex.getGroupe());
            }
        }
        return liste;
    }

    /**
     * {@code GET  /getManagerOfDeps} : get Manager Of Deps.<br> <br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to get Manager Of Deps.
     * @param param  param.
     * @return List od String.
     */
  @PreAuthorize("hasAuthority('SUPLOCALADMIN') or hasAuthority('SysReader')")
    @GetMapping({"/getManagerOfDeps"})
    public List<String> getManagerOfDeps(@RequestParam String[] param) {
        List<String> res = new ArrayList<String>();
        for (int i = 0; i < param.length; ++i) {
            List<Expand> list = this.expandRepository.findAllByDepartement(param[i]);
            if (!list.isEmpty()) {
                for (int j = 0; j < list.size(); ++j) {
                    String displayname = UserRepository.findById((long) list.get(j).getUserId()).get().getDisplayname();
                    res.add(displayname);
                }
            } else {
                res.add(this.ex.getDX(param[i]));
            }
        }
        return res;
    }
}
