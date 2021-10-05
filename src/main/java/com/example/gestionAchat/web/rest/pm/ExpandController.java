
package com.example.gestionAchat.web.rest.pm;

import io.github.jhipster.web.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.example.gestionAchat.web.rest.errors.BadRequestAlertException;
import com.example.gestionAchat.entities.Department;
import com.example.gestionAchat.entities.Expand;
import com.example.gestionAchat.entities.User;
import com.example.gestionAchat.repository.pm.DepRepository;
import com.example.gestionAchat.repository.pm.ExpandRepository;
import com.example.gestionAchat.repository.pm.UserRepository;
import com.example.gestionAchat.service.pm.dto.ExpandCriteria;
import com.example.gestionAchat.service.pm.query.ExpandQueryService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = {"*"})
//@PreAuthorize("isAuthenticated()")    
@RestController
@RequestMapping({"/api"})
public class ExpandController {
    private static Logger log;

    static {
        log = LoggerFactory.getLogger(ExpandController.class);
    }

    Long id;
    User u;
    @Autowired
    private ExpandRepository ExRepository;
    @Autowired
    private DepRepository depRepository;
    @Autowired
    private UserRepository UserRepository;
    @Autowired
    private ExpandQueryService expandQueryService;
    private String urlbase;

    /**
     * {@code GET  /expands} : get all the expands.<br> <br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to get all the expands.
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of expands in body.
     */
    @GetMapping("/expands")
    public ResponseEntity<List<Expand>> getAllExpands(ExpandCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Expands by criteria: {}", criteria);
        Page<Expand> page = expandQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    public Boolean recherche(String ch, List l) {
        if (ch != null && l != null) {
            int i;
            for (i = 0; i < l.size() && l.get(i) != null && !l.get(i).equals(ch); ++i) {
            }
            return i < l.size();
        }
        return false;
    }

    /**
     * {@code GET  /expands/count} : count all the expands.<br> <br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to count all the expands.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/expands/count")
    public ResponseEntity<Long> countExpands(ExpandCriteria criteria) {
        log.debug("REST request to count Expands by criteria: {}", criteria);
        return ResponseEntity.ok().body(expandQueryService.countByCriteria(criteria));
    }

    /**
     * {@code POST  /CreerExpand} : Create Expand.<br> <br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to Create Expand.
     * @return msg.
     */
    @PostMapping({"/CreerExpand"})
    public String CreerExpand() {
        if (!this.ExRepository.findAll().isEmpty()) {
            this.ExRepository.deleteAll();
        }
        List<Department> dep = this.depRepository.findAll();
        List<User> user = this.UserRepository.findAll();
        for (Department d : dep) {
            if (d.getConfigLdap() != null) {
                Expand ex = new Expand();
                ex.setDepartement(d.getName());
                if (this.getDX(d.getName()) != null) {
                    u = UserRepository.findByDisplayname(this.getDX(d.getName()));
                    if (u != null) {
                        id = u.getId();
                        ex.setUserId(id.intValue());
                        this.ExRepository.save(ex);
                    }
                }
                List<String> l2 = this.getDXSup(d.getName());
                for (String i : l2) {
                    Expand ex2 = new Expand();
                    ex2.setDepartement(d.getName() + "/SUP");
                    u = UserRepository.findByDisplayname(i);
                    if (u != null) {
                        id = u.getId();
                        ex2.setUserId(id.intValue());
                        this.ExRepository.save(ex2);
                    }
                }
                List<User> l3 = this.getDXAll(d.getName());
                for (User u : l3) {
                    String j = u.getDisplayname();
                    if (j != null) {
                        Expand ex3 = new Expand();
                        ex3.setDepartement(d.getName() + "/ALL");
                        u = UserRepository.findByDisplayname(j);
                        if (u != null) {
                            id = u.getId();
                            ex3.setUserId(id.intValue());
                            this.ExRepository.save(ex3);
                        }
                    }
                }
            }
        }
        for (User u : user) {
            if (u.getConfigLdap() != null) {
                String delimiter = ",";
                if (u.getMemberof() != null && !u.getMemberof().isEmpty()) {
                    String[] tempArray = u.getMemberof().split(delimiter);
                    for (int k = 0; k < tempArray.length; ++k) {
                        Expand ex4 = new Expand();
                        ex4.setGroupe(tempArray[k]);
                        ex4.setUserId(u.getId().intValue());
                        this.ExRepository.save(ex4);
                    }
                }
            }
        }
        return "Created";
    }

    /**
     * {@code GET  /getTeamMembers} : get TeamMembers.<br> <br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to get TeamMembers.
     * @param username username param.
     * @return List of User.
     */
    @GetMapping({"/getTeamMembers"})
    public List<User> getTeamMembers(@RequestParam String username) {
        Optional<User> user = this.UserRepository.findBySamaccountname(username);
        if (user.isPresent()) {
            String distinguishedname = user.get().getDistinguishedname();
            String[] result = distinguishedname.split(",OU=");
            return getDXMembreUser(result[1]);
        }
        throw new BadRequestAlertException("", "", " ");
    }

    /**
     * {@code GET  /getAllTeamMembers} : get All MemebreSup.<br> <br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to get All MemebreSup .
     * @param username username param.
     * @return List of User.
     */
    @GetMapping({"/getAllTeamMembers"})
    public List<User> getAllMemebreSup(@RequestParam String username) {
        Optional<User> user = this.UserRepository.findBySamaccountname(username);
        if (user.isPresent()) {
            List<User> s = new ArrayList<>();
            String distinguishedname = user.get().getDistinguishedname();
            List<Department> deps = depRepository.findAllByManagedby(distinguishedname);
            if (!deps.isEmpty()) {
                String[] result = distinguishedname.split(",OU=");
                return getDXAllUser(result[1]);
            } else {
                s.add(user.get());
                return s;
            }
        }
        throw new BadRequestAlertException("", "", " ");
    }

    public List<User> getDXMembreUser(String name) {
        List<User> membreDX = new ArrayList<User>();
        String displayName = this.getDX(name);
        User u = this.UserRepository.findByDisplayname(displayName);
        membreDX.add(u);
        for (User m : getMembreDepUser(name)) {
            if (!rechercheUser(m, membreDX)) {
                membreDX.add(m);
            }
        }
        return membreDX;
    }

    public List<User> getMembreDepUser(String dep) {
        List<User> allDX = new ArrayList<User>();
        List<User> userList = this.UserRepository.findAll();
        for (User a : userList) {
            String des = this.UserRepository.getManagerOfUser(a.getSamaccountname());
            if (dep.equals(des)) {
                allDX.add(a);
            }
        }
        return allDX;
    }

    public Boolean rechercheUser(User ch, List l) {
        if (ch != null && l != null) {
            int i;
            for (i = 0; i < l.size() && l.get(i) != null && !l.get(i).equals(ch); ++i) {
            }
            return i < l.size();
        }
        return false;
    }

    public List<User> getDXAllUser(String name) {
        List<User> membredep = new ArrayList<User>();
        List<String> sousdep = new ArrayList<String>();
        sousdep.add(name);
        membredep.addAll(this.getDXMembreUser(name));
        while (!sousdep.isEmpty()) {
            List<String> allDX = new ArrayList<String>();
            List<Department> dep = this.depRepository.findAll();
            for (Department d : dep) {
                if (d.getConfigLdap() != null) {
                    String distinguishednameDep = d.getDistinguishedname();
                    if (distinguishednameDep.startsWith("OU=")) {
                        String[] resultDep = distinguishednameDep.split(",OU=");
                        if (resultDep.length > 1) {
                            for (String n : sousdep) {
                                if (resultDep[1].equals(n)) {
                                    allDX.add(resultDep[0].substring(3));
                                    List<User> mm = getDXMembreUser(resultDep[0].substring(3));
                                    // membredep.addAll(this.getMembreDep(resultDep[0].substring(3)));

                                    for (User m : mm) {
                                        if (!rechercheUser(m, membredep)) {
                                            membredep.add(m);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            sousdep.clear();
            sousdep.addAll(allDX);
        }
        return membredep;
    }

    /**
     * {@code GET  /getMembersOfDepartment} : get Members Of Department .<br> <br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to get Members Of Department .
     * @param name name param.
     * @return List of User.
     */
    @GetMapping({"/getMembersOfDepartment"})
    public List<User> getDXMembre(String name) {
        List<User> membreDX = new ArrayList<>();
        String d = this.getDX(name);
        User u = UserRepository.findByDisplayname(d);
        if (u != null) membreDX.add(u);
        for (User user : getMembreDep(name)) {
            String m = user.getDisplayname();
            if (!recherche(m, membreDX)) {
                membreDX.add(user);
            }
        }
        return membreDX;
    }

    /**
     * {@code GET  /getDX} : get DX.<br> <br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to get DX.
     * @param name name param.
     * @return String.
     */
    @GetMapping({"/getDX"})
    public String getDX(String name) {
        Optional<Department> dep = this.depRepository.findByName(name);
        if (dep.isPresent()) {
            String manager = dep.get().getManagedby();
            if (manager.startsWith("CN=")) {
                return manager.substring(3, manager.indexOf(",OU="));
            }
            String distinguishednameDep = dep.get().getDistinguishedname();
            if (distinguishednameDep.startsWith("OU=")) {
                String[] ds = distinguishednameDep.split(",OU=");
                if (ds.length > 1) {
                    name = ds[1];
                    return this.getDX(name);
                }
            }
        }
        return null;
    }

    public List<User> getDXAll(String name) {
        List<User> membredep = new ArrayList<User>();
        List<String> sousdep = new ArrayList<String>();
        sousdep.add(name);
        membredep.addAll(this.getDXMembre(name));
        while (!sousdep.isEmpty()) {
            List<String> allDX = new ArrayList<String>();
            List<Department> dep = this.depRepository.findAll();
            for (Department d : dep) {
                if (d.getConfigLdap() != null) {
                    String distinguishednameDep = d.getDistinguishedname();
                    if (distinguishednameDep.startsWith("OU=")) {
                        String[] resultDep = distinguishednameDep.split(",OU=");
                        if (resultDep.length > 1) {
                            for (String n : sousdep) {
                                if (resultDep[1].equals(n)) {
                                    allDX.add(resultDep[0].substring(3));
                                    List<User> mm = getDXMembre(resultDep[0].substring(3));
                                    // membredep.addAll(this.getMembreDep(resultDep[0].substring(3)));

                                    for (User u : mm) {
                                        String m = u.getDisplayname();
                                        if (!recherche(m, membredep)) {
                                            membredep.add(u);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            sousdep.clear();
            sousdep.addAll(allDX);
        }
        return membredep;
    }

    public List<User> getMembreDep(String dep) {
        List<User> allDX = new ArrayList<>();
        List<User> userList = this.UserRepository.findAll();
        for (User a : userList) {
            String des = this.UserRepository.getManagerOfUser(a.getSamaccountname());
            if (dep.equals(des)) {
                allDX.add(a);
            }
        }
        return allDX;
    }

    /**
     * {@code GET  /getDX/{name}} : get DXSup.<br> <br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to get DXSup.
     * @param name name param.
     * @return SupDX.
     */
    @GetMapping({"/getDXSup/{name}"})
    public List<String> getDXSup(@PathVariable String name) {
        Optional<Department> dep = this.depRepository.findByName(name);
        this.urlbase = dep.get().getConfigLdap().getBase();
        List<String> SupDX = new ArrayList<String>();
        if (this.urlbase != null) {
            String distinguishednameDep = null;
            do {
                if (dep.isPresent()) {
                    distinguishednameDep = dep.get().getDistinguishedname();
                    if (!distinguishednameDep.startsWith("OU=")) {
                        continue;
                    }
                    String[] resultDep = distinguishednameDep.split(",OU=");
                    if (resultDep.length > 1) {
                        String manager = dep.get().getManagedby();
                        if (manager.startsWith("CN=")) {
                            String m = manager.substring(3, manager.indexOf(",OU="));
                            if (!recherche(m, SupDX))
                                SupDX.add(m);

                        }
                        dep = this.depRepository.findByName(resultDep[1]);
                    }
                }
            } while (!distinguishednameDep.equals(this.urlbase) && distinguishednameDep.startsWith("OU="));
        }
        return SupDX;
    }
}
