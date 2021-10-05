
package com.example.gestionAchat.web.rest.pm;

import io.github.jhipster.web.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.example.gestionAchat.config.Device;
import com.example.gestionAchat.entities.Role;
import com.example.gestionAchat.repository.pm.AppRepository;
import com.example.gestionAchat.repository.pm.RoleRepository;
import com.example.gestionAchat.service.pm.RoleService;
import com.example.gestionAchat.service.pm.dto.RoleCriteria;
import com.example.gestionAchat.service.pm.query.RoleQueryService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import com.example.gestionAchat.entities.Application;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping({"/api"})
public class RoleController {
    private static final Logger log;

    static {
        log = LoggerFactory.getLogger(RoleController.class);
    }

    private final Device global;
    /*	@Autowired
        Config config;
        Client client*/
    List<Role> roles;
    @Autowired
    private RoleRepository Repository;
    //	@Autowired
//	private RoleEsRepository ESRepository;
    @Autowired
    private AppRepository appRep;
    @Autowired
    private RoleService Service;
    @Autowired
    private RoleQueryService roleQueryService;

    public RoleController() {
        this.global = new Device();

    }

    public HashMap<String, Role> JsonFormat(Role Role) {
        HashMap<String, Role> dataOut = new HashMap<String, Role>();
        dataOut.put("data", Role);
        return dataOut;
    }

    /**
     * {@code GET  /roles} : get all the roles.<br><br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to get all the roles.
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of roles in body.
     */
    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getAllRoles(RoleCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Roles by criteria: {}", criteria);
        Page<Role> page = roleQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /roles/count} : count all the roles.<br><br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to count all the roles.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/roles/count")
    public ResponseEntity<Long> countRoles(RoleCriteria criteria) {
        log.debug("REST request to count Roles by criteria: {}", criteria);
        return ResponseEntity.ok().body(roleQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /Role} : get all roles.<br><br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to get all roles.
     * @param request request param.
     * @return dataOut.
     */
  @PreAuthorize("hasAuthority('SUPLOCALADMIN') or hasAuthority('RoleReader')  or hasAuthority('RoleWriter')")
    @GetMapping({"/Role"})
    public HashMap<String, List<Role>> allRoles(HttpServletRequest request) {
        return Service.allRoles(request, this.global);

    }


    @GetMapping("/ListRoles")
    public List<String>  getAllRolesString() {

        List<Role>  liste= Repository.findAll();

        List<String> usersstring = new ArrayList<>();

        for(int k=0; k<liste.size(); k++)
            usersstring.add(liste.get(k).getRolename());

        if(liste.isEmpty())
            return null;
        else
            return usersstring;
    }



    /**
     * {@code GET  /getRoleByProfile/{profileName}} : get Role By Profile.<br><br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to get Role By Profile.
     * @param request request param.
     * @param profileName profileName param.
     * @return dataOut.
     */
 @PreAuthorize("hasAuthority('SUPLOCALADMIN') or hasAuthority('RoleReader')  or hasAuthority('RoleWriter')")
    @GetMapping({"/getRoleByProfile/{profileName}"})
    public HashMap<String, List<Role>> getRoleByProfile(HttpServletRequest request, @PathVariable String profileName) {
        List<Role> roleList = this.Service.getRolesByProfile(profileName);
        HashMap<String, List<Role>> dataOut = new HashMap<String, List<Role>>();
        dataOut.put("data", roleList);
        return dataOut;
    }

    /**
     * {@code GET  /RoleByDBID/{id}} : get role By id.<br><br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to get role By id.
     *
     * @param request request param.
     * @param id      id param.
     * @return dataOut.
     * @throws IOException IOException is thrown.
     */
   @PreAuthorize("hasAuthority('SUPLOCALADMIN') or hasAuthority('RoleReader')  or hasAuthority('RoleWriter') ")
    @GetMapping({"/RoleByDBID/{id}"})
    public HashMap<String, Optional<Role>> RoleById(HttpServletRequest request, @PathVariable Long id)
            throws IOException {
        Optional<Role> role = this.Repository.findById(id);
        String token = request.getHeader("Authorization");
        String body = this.global.DecodeJWT(token.substring(token.indexOf("ey")));
        String[] list = body.split("\"authority\":\"");
        List<String> l = new ArrayList<String>();
        for (int i = 1; i < list.length; ++i) {
            l.add(list[i].substring(0, body.split("\"authority\":\"")[i].length() - 4));
        }
        String[] prv = null;
        String[] prv2 = null;
        List<String> l2 = new ArrayList<String>();
        if ((role.get().getReader() != null && !role.get().getReader().isEmpty())
                || (role.get().getAuthor() != null && !role.get().getAuthor().isEmpty())) {
            prv = role.get().getReader().split(",");
            prv2 = role.get().getAuthor().split(",");
            for (String p : prv) {
                l2.add(p);
            }
            for (String p : prv2) {
                l2.add(p);
            }
        }
        if (this.global.valid(l2, l)) {
            HashMap<String, Optional<Role>> dataOut = new HashMap<String, Optional<Role>>();
            dataOut.put("data", role);
            return dataOut;
        }
        return null;
    }

    /**
     * {@code GET  /RoleById/{roleName},{alias}} : get role By id.<br><br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to get role By id.
     * @param request request param.
     * @param roleName roleName param.
     * @param alias alias param.
     * @return dataOut.
     * @throws IOException IOException is thrown.
     */
 @PreAuthorize("hasAuthority('SUPLOCALADMIN') or hasAuthority('RoleReader')  or hasAuthority('RoleWriter')")
    @GetMapping({"/RoleById/{roleName},{alias}"})
    public HashMap<String, Role> RoleById(HttpServletRequest request, @PathVariable String roleName,
                                          @PathVariable String alias) throws IOException {
        Role role = this.Service.getRoleById(roleName, alias);
        String token = request.getHeader("Authorization");
        String body = this.global.DecodeJWT(token.substring(token.indexOf("ey")));
        String[] list = body.split("\"authority\":\"");
        List<String> l = new ArrayList<String>();
        for (int i = 1; i < list.length; ++i) {
            l.add(list[i].substring(0, body.split("\"authority\":\"")[i].length() - 4));
        }
        String[] prv = null;
        String[] prv2 = null;
        List<String> l2 = new ArrayList<String>();
        if ((role.getReader() != null && !role.getReader().isEmpty())
                || (role.getAuthor() != null && !role.getAuthor().isEmpty())) {
            prv = role.getReader().split(",");
            prv2 = role.getAuthor().split(",");
            for (String p : prv) {
                l2.add(p);
            }
            for (String p : prv2) {
                l2.add(p);
            }
        }
        if (this.global.valid(l2, l)) {
            HashMap<String, Role> dataOut = new HashMap<String, Role>();
            dataOut.put("data", role);
            return dataOut;
        }
        return null;
    }

    /**
     * {@code GET  /getRolesByApplication/{alias}} : get Roles By Application.<br><br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to get Roles By Application.
     * @param request request param.
     * @param alias alias param.
     * @return dataOut.
     */
  @PreAuthorize("hasAuthority('SUPLOCALADMIN') or hasAuthority('RoleReader')  or hasAuthority('RoleWriter')")
    @GetMapping({"/getRolesByApplication/{alias}"})
    public HashMap<String, List<Role>> getRolesByApplication(HttpServletRequest request, @PathVariable String alias) {
        List<Role> roleList = this.Service.getRoleByApplication(alias);
        HashMap<String, List<Role>> dataOut = new HashMap<String, List<Role>>();
        dataOut.put("data", roleList);
        return dataOut;
    }

    /**
     * {@code GET  /Role} : add Role.<br><br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to add Role.
     * @param request request param.
     * @param role role param.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of expands in body.
     * @throws IOException IOException is thrown.
     */
   @PreAuthorize("hasAuthority('SUPLOCALADMIN') or hasAuthority('RoleWriter') ")
    @PostMapping({"/Role"})
    public ResponseEntity<HashMap<String, Role>> addRole(HttpServletRequest request, @Valid @RequestBody Role role)
            throws IOException {
        String token = request.getHeader("Authorization");
        String body = this.global.DecodeJWT(token.substring(token.indexOf("ey")));
        String[] list = body.split("\"authority\":\"");
        String username = body.substring(body.indexOf("\":\"") + 3, body.indexOf("\","));
        List<String> l = new ArrayList<String>();
        for (int i = 1; i < list.length; ++i) {
            l.add(list[i].substring(0, body.split("\"authority\":\"")[i].length() - 4));
        }
        String roleName = role.getRolename();
        System.out.println("roleName"+roleName);
        System.out.println("app"+role.getApplication().toString());
        String alias = appRep.findByAlias("mm8").get().getAlias();
        this.roles = this.Repository.findAll();
        System.out.println("size"+this.roles.size());
        int j;

        for (j = 0; j < this.roles.size() &&
                (!this.roles.get(j).getRolename().equals(roleName)
                || !this.roles.get(j).getApplication().getAlias().equals(alias)); ++j) {
        }
        if (j < this.roles.size()) {
            return updateRole(request, roleName, alias, role);

        }
        String A = role.getAuthor();
        String R = role.getReader();
        if (A == null) {
            A = "RoleWriter,SUPLOCALADMIN";
        } else {
            String[] aa = A.split(",");
            Boolean z = false;
            Boolean e = false;
            for (String a : aa) {
                if (a.equals("RoleWriter")) {
                    z = true;
                }
                if (a.equals("SUPLOCALADMIN")) {
                    e = true;
                }
            }
            if (!z) {
                A += ",RoleWriter";
            }
            if (!e) {
                A += ",SUPLOCALADMIN";
            }
        }
        if (R == null) {
            R = "RoleReader";
        } else {
            String[] aa = R.split(",");
            Boolean z = false;
            for (String a2 : aa) {
                if (a2.equals("RoleReader")) {
                    z = true;
                }
            }
            if (!z) {
                R += ",RoleReader";
            }
        }
        LocalDate date = LocalDate.now();
        role.setAuthor(A);
        role.setReader(R);
        role.setApplication(this.appRep.findByAlias(alias).get());
        role.setDatecreate(date);
        role.setDateupdate(date);
        role.setCreatedby(username);
        HashMap<String, Role> result = this.JsonFormat(this.Repository.save(role));
        // String res = this.newcreate(role);
        //ESRepository.save(role);
        return ResponseEntity.ok(result);
    }

    /**
     * {@code PUT  /Role/{rolename},{alias}} : update Role.<br><br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to update Role.
     * @param request request param.
     * @param alias alias param.
     * @param rolename rolename param.
     * @param roles roles param.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of expands in body.
     * @throws IOException IOException is thrown.
     */
  @PreAuthorize("hasAuthority('SUPLOCALADMIN') or hasAuthority('RoleWriter') ")
    @PutMapping({"/Role/{rolename},{alias}"})
    public ResponseEntity<HashMap<String, Role>> updateRole(HttpServletRequest request, @PathVariable String rolename,
                                                            @PathVariable String alias, @Valid @RequestBody Role roles) throws IOException {

        System.out.println(roles.getId());
        String token = request.getHeader("Authorization");
        String body = this.global.DecodeJWT(token.substring(token.indexOf("ey")));
        String[] list = body.split("\"authority\":\"");
        String username = body.substring(body.indexOf("\":\"") + 3, body.indexOf("\","));
        List<String> l = new ArrayList<String>();
        for (int i = 1; i < list.length; ++i) {
            l.add(list[i].substring(0, body.split("\"authority\":\"")[i].length() - 4));
        }

        Role role = this.Service.getRoleByIdMM(rolename);


        Application app = this.appRep.findByAlias(alias).get();
        String[] prv = null;
        List<String> l2 = new ArrayList<String>();
        if (role.getAuthor() != null && !role.getAuthor().isEmpty()) {
            prv = role.getAuthor().split(",");
        }
        for (String p : prv) {
            l2.add(p);
        }
        if (this.global.valid(l2, l)) {
            String A = roles.getAuthor();
            String R = roles.getReader();
            if (A == null) {
                A = "RoleWriter,SUPLOCALADMIN";
            } else {
                String[] aa = A.split(",");
                Boolean z = false;
                Boolean e = false;
                for (String a : aa) {
                    if (a.equals("RoleWriter")) {
                        z = true;
                    }
                    if (a.equals("SUPLOCALADMIN")) {
                        e = true;
                    }
                }
                if (!z) {
                    A += ",RoleWriter";
                }
                if (!e) {
                    A += ",SUPLOCALADMIN";
                }
            }
            if (R == null) {
                R = "RoleReader";
            } else {
                String[] aa = R.split(",");
                Boolean z = false;
                for (String a2 : aa) {
                    if (a2.equals("RoleReader")) {
                        z = true;
                    }
                }
                if (!z) {
                    R += ",RoleReader";
                }
            }
            Role r = role;
            role.setRolename(roles.getRolename());
            role.setApplication(app);
            role.setComment(roles.getComment());
            role.setDateupdate(LocalDate.now());
            role.setRoledescription(roles.getRoledescription());
            role.setStatus(roles.isStatus());
            role.setTest(roles.isTest());
            role.setAuthor(A);
            role.setReader(R);
            role.setModifiedby(username);
            HashMap<String, Role> result = this.JsonFormat(this.Repository.save(role));
            //String res = this.update(role.getId(), role);
            //ESRepository.save(role);
            return ResponseEntity.ok(result);
        }
        return null;
    }

    /**
     * {@code GET  /getRoleActive} : get Role Active.<br><br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to get Role Active.
     * @param status status param.
     * @return size.
     */
    @GetMapping({"/getRoleActive"})
    public int getRoleActive(@RequestParam Boolean status) {
        return this.Repository.findAllByStatus(status).size();
    }

    /**
     * {@code GET  /getRoleTest} : get Role Test.<br><br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to get Role Test.
     * @param test test param.
     * @return size.
     */
    @GetMapping({"/getRoleTest"})
    public int getRoleTest(@RequestParam Boolean test) {
        return this.Repository.findAllByTest(test).size();
    }

    /**
     * {@code GET  /getRoleTotal} : get Role Total.<br><br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to get Role Active.
     * @return size.
     */
    @GetMapping({"/getRoleTotal"})
    public int getRoleTotal() {
        return this.Repository.findAll().size();
    }

    /**
     * {@code DELETE  /Role/{rolename},{alias}} : delete Role.<br><br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to delete Role.
     * @param request request param.
     * @param alias alias param.
     * @param rolename rolename param.
     * @throws UnknownHostException UnknownHostException is thrown.
     * @throws MalformedURLException MalformedURLException is thrown.
     */
  //  @PreAuthorize("hasAuthority('SUPLOCALADMIN') or hasAuthority('RoleWriter') ")
    @DeleteMapping({"/Role/{rolename},{alias}"})
    public void deleteRole(HttpServletRequest request, @PathVariable String rolename, @PathVariable String alias)
            throws UnknownHostException, MalformedURLException {
        String token = request.getHeader("Authorization");
        String body = this.global.DecodeJWT(token.substring(token.indexOf("ey")));
        String[] list = body.split("\"authority\":\"");
        String username = body.substring(body.indexOf("\":\"") + 3, body.indexOf("\","));
        List<String> l = new ArrayList<String>();
        for (int i = 1; i < list.length; ++i) {
            l.add(list[i].substring(0, body.split("\"authority\":\"")[i].length() - 4));
        }
        Role std = this.Service.getRoleById(rolename, alias);
        String[] prv = null;
        List<String> l2 = new ArrayList<String>();
        if (std.getAuthor() != null && !std.getAuthor().isEmpty()) {
            prv = std.getAuthor().split(",");
        }
        for (String p : prv) {
            l2.add(p);
        }
        if (this.global.valid(l2, l)) {
            //String res = this.delete(std.getId());
            //ESRepository.delete(std);
            this.Repository.delete(std);
        }
    }
}
