
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
import com.example.gestionAchat.entities.Profile;
import com.example.gestionAchat.entities.Role;
import com.example.gestionAchat.repository.pm.AppRepository;
import com.example.gestionAchat.repository.pm.ProfileRepository;
import com.example.gestionAchat.repository.pm.RoleRepository;
import com.example.gestionAchat.service.pm.ProfileService;
import com.example.gestionAchat.service.pm.RoleService;
import com.example.gestionAchat.service.pm.dto.ProfileCriteria;
import com.example.gestionAchat.service.pm.query.ProfileQueryService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import com.example.gestionAchat.entities.Application;
//@PreAuthorize("isAuthenticated()")
@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping({"/api"})
public class ProfileController {
    private static final Logger log;
    @Autowired
    private ProfileService profileService;

    static {
        log = LoggerFactory.getLogger(ProfileController.class);
    }

    private final Device global;
    /*	@Autowired
        Config config;
        Client client;*/
    List<Profile> prof;
    @Autowired
    private ProfileRepository Repository;
    @Autowired
    private RoleRepository RoleRepository;
    //	@Autowired
//	private ProfileEsRepository profileEsRepository;
    @Autowired
    private RoleService roleService;
    @Autowired
    private AppRepository appRep;
    @Autowired
    private ProfileService Service;

    @Autowired
    private ProfileQueryService profileQueryService;

    public ProfileController() {
        this.global = new Device();

    }

    public HashMap<String, Profile> JsonFormat(Profile Profile) {
        HashMap<String, Profile> dataOut = new HashMap<String, Profile>();
        dataOut.put("data", Profile);
        return dataOut;
    }


    @GetMapping("/ListProfiles")
    public List<String>  getAllProfilesString() {

        List<Profile>  liste= Repository.findAll();

        List<String> profilestring = new ArrayList<>();

        for(int k=0; k<liste.size(); k++)
            profilestring.add(liste.get(k).getProfilename());

        if(liste.isEmpty())
            return null;
        else
            return profilestring;
    }


    @GetMapping("/profileByUser")
    public String ProfileByUser(@RequestParam(name="userid") Long userid){


    List<Profile> profiles = profileService.getProfilesByUserMM(userid);

    if(profiles==null)
        return "";
        else
      return  profiles.get(0).getProfilename();
    }
    /**
     * {@code GET  /profiles} : get all the profiles.<br><br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to get all the profiles.
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of profiles in body.
     */
    @GetMapping("/profiles")
    public ResponseEntity<List<Profile>> getAllProfiles(ProfileCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Profiles by criteria: {}", criteria);
        Page<Profile> page = profileQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);

        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /profiles/count} : count all the profiles.<br> <br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to count all the profiles.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/profiles/count")
    public ResponseEntity<Long> countProfiles(ProfileCriteria criteria) {
        log.debug("REST request to count Profiles by criteria: {}", criteria);
        return ResponseEntity.ok().body(profileQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /profile} : get all profiles.<br> <br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to get all profiles.
     * @param request request param.
     * @return dataOut.
     */
@PreAuthorize("hasAuthority('SUPLOCALADMIN') or hasAuthority('ProfileReader') or hasAuthority('ProfileWriter')")
    @GetMapping({"/Profile"})
    public HashMap<String, List<Profile>> allProfile(HttpServletRequest request) {
        List<Profile> profileList = this.Repository.findAll();
        List<Profile> profiles = new ArrayList<Profile>();
        String token = request.getHeader("Authorization");
        String body = this.global.DecodeJWT(token.substring(token.indexOf("ey")));
        String[] list = body.split("\"authority\":\"");
        List<String> l = new ArrayList<String>();
        for (int i = 1; i < list.length; ++i) {
            l.add(list[i].substring(0, body.split("\"authority\":\"")[i].length() - 4));
        }
        for (Profile prvl : profileList) {
            String[] prv = null;
            String[] prv2 = null;
            List<String> l2 = new ArrayList<String>();
            if ((prvl.getReader() != null && !prvl.getReader().isEmpty())
                    || (prvl.getAuthor() != null && !prvl.getAuthor().isEmpty())) {
                prv = prvl.getReader().split(",");
                prv2 = prvl.getAuthor().split(",");
                for (String p : prv) {
                    l2.add(p);
                }
                for (String p : prv2) {
                    l2.add(p);
                }
            }
            if (this.global.valid(l2, l)) {
                profiles.add(prvl);
            }
        }
        HashMap<String, List<Profile>> dataOut = new HashMap<String, List<Profile>>();
        dataOut.put("data", profiles);
        return dataOut;
    }

    /**
     * {@code GET  /ProfileByDBID/{id}} : get Profile By DBID.<br> <br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to get Profile By DBID.
     *
     * @param request request param.
     * @param id      id param.
     * @return dataOut.
     * @throws IOException IOException is thrown.
     */
@PreAuthorize("hasAuthority('SUPLOCALADMIN') or hasAuthority('ProfileReader') or hasAuthority('ProfileWriter')")
    @GetMapping({"/ProfileByDBID/{id}"})
    public HashMap<String, Optional<Profile>> ProfileByDBID(HttpServletRequest request, @PathVariable Long id)
            throws IOException {
        String token = request.getHeader("Authorization");
        String body = this.global.DecodeJWT(token.substring(token.indexOf("ey")));
        String[] list = body.split("\"authority\":\"");
        List<String> l = new ArrayList<String>();
        for (int i = 1; i < list.length; ++i) {
            l.add(list[i].substring(0, body.split("\"authority\":\"")[i].length() - 4));
        }
        Optional<Profile> profile = this.Repository.findById(id);
        String[] prv = null;
        String[] prv2 = null;
        List<String> l2 = new ArrayList<String>();
        if ((profile.get().getReader() != null && !profile.get().getReader().isEmpty())
                || (profile.get().getAuthor() != null && !profile.get().getAuthor().isEmpty())) {
            prv = profile.get().getReader().split(",");
            prv2 = profile.get().getAuthor().split(",");
            for (String p : prv) {
                l2.add(p);
            }
            for (String p : prv2) {
                l2.add(p);
            }
        }
        if (this.global.valid(l2, l)) {
            HashMap<String, Optional<Profile>> dataOut = new HashMap<String, Optional<Profile>>();
            dataOut.put("data", profile);
            return dataOut;
        }
        return null;
    }

    /**
     * {@code GET  /ProfileById/{profileName},{alias}} : get Profile By Id.<br> <br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to get Profile By Id.
     * @param request request param.
     * @param alias alias param.
     * @param profileName profileName param.
     * @return dataOut.
     * @throws IOException IOException is thrown.
     */

 @PreAuthorize("hasAuthority('SUPLOCALADMIN') or hasAuthority('ProfileReader') or hasAuthority('ProfileWriter') ")
    @GetMapping({"/ProfileById/{profileName},{alias}"})
    public HashMap<String, Profile> ProfileById(HttpServletRequest request, @PathVariable String profileName,
                                                @PathVariable String alias) throws IOException {
        String token = request.getHeader("Authorization");
        String body = this.global.DecodeJWT(token.substring(token.indexOf("ey")));
        String[] list = body.split("\"authority\":\"");
        List<String> l = new ArrayList<String>();
        for (int i = 1; i < list.length; ++i) {
            l.add(list[i].substring(0, body.split("\"authority\":\"")[i].length() - 4));
        }
        Profile profile = this.Service.getProfilesById(profileName, alias);
        String[] prv = null;
        String[] prv2 = null;
        List<String> l2 = new ArrayList<String>();
        if ((profile.getReader() != null && !profile.getReader().isEmpty())
                || (profile.getAuthor() != null && !profile.getAuthor().isEmpty())) {
            prv = profile.getReader().split(",");
            prv2 = profile.getAuthor().split(",");
            for (String p : prv) {
                l2.add(p);
            }
            for (String p : prv2) {
                l2.add(p);
            }
        }
        if (this.global.valid(l2, l)) {
            HashMap<String, Profile> dataOut = new HashMap<String, Profile>();
            dataOut.put("data", profile);
            return dataOut;
        }
        return null;
    }



   @PreAuthorize("hasAuthority('SUPLOCALADMIN') or hasAuthority('ProfileReader') or hasAuthority('ProfileWriter') ")
    @GetMapping({"/ProfileByName/{profileName},{alias}"})
    public List<String> ProfileByName(HttpServletRequest request, @PathVariable String profileName,
                                                @PathVariable String alias) throws IOException {
        String token = request.getHeader("Authorization");
        String body = this.global.DecodeJWT(token.substring(token.indexOf("ey")));
        String[] list = body.split("\"authority\":\"");
        List<String> l = new ArrayList<String>();
        for (int i = 1; i < list.length; ++i) {
            l.add(list[i].substring(0, body.split("\"authority\":\"")[i].length() - 4));
        }

       Profile profile= Repository.findByProfilename(profileName);
        System.out.println("prof"+profile.getProfilename());


        System.out.println(profile.getId());
       List<BigInteger> liste= this.Service.getProfilesByNameProfile(profile.getId());
        System.out.println("listelength"+liste.size());

        List<String> listeRoles= new ArrayList<>();
        for(int i=0; i<liste.size(); i++)
        {
          System.out.println("listeIds"+liste.get(i).toString());
          Optional<Role> role=  RoleRepository.findById(Long.valueOf(liste.get(i).toString()));
          if(role.isPresent())
              if(listeRoles.indexOf(role.get().getRolename())==-1)
              { System.out.println("rolename::"+role.get().getRolename());
                  listeRoles.add(role.get().getRolename());}

        }
        return listeRoles;

    }


    /**
     * {@code GET  /getProfilesByApplication/{alias}} : get Profiles By Application.<br> <br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to get Profiles By Application.
     * @param request request param.
     * @param alias alias param.
     * @return dataOut.
     */
   // @PreAuthorize("hasAuthority('SUPLOCALADMIN') or hasAuthority('ProfileReader') or hasAuthority('ProfileWriter')")
    @GetMapping({"/getProfilesByApplication/{alias}"})
    public HashMap<String, List<Profile>> getProfilesByApplication(HttpServletRequest request,
                                                                   @PathVariable String alias) {
        List<Profile> profileList = this.Service.getProfilesByApplication(alias);
        HashMap<String, List<Profile>> dataOut = new HashMap<String, List<Profile>>();
        dataOut.put("data", profileList);
        return dataOut;
    }

    /**
     * {@code POST  /Profile} : add Profile.<br> <br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to add Profile.
     * @param request request param.
     * @param profile profile param.
     * @return dataOut.
     * @throws IOException IOException is thrown.
     */
 //@PreAuthorize("hasAuthority('SUPLOCALADMIN') or hasAuthority('ProfileWriter')")
    @PostMapping({"/Profile"})
    public ResponseEntity<HashMap<String, Profile>> addProfile(HttpServletRequest request, @Valid @RequestBody Profile profile)
            throws IOException {
        String token = request.getHeader("Authorization");
        String body = this.global.DecodeJWT(token.substring(token.indexOf("ey")));
        String[] list = body.split("\"authority\":\"");
        String username = body.substring(body.indexOf("\":\"") + 3, body.indexOf("\","));
        List<String> l = new ArrayList<String>();
        for (int i = 1; i < list.length; ++i) {
            l.add(list[i].substring(0, body.split("\"authority\":\"")[i].length() - 4));
        }
        String profileName = profile.getProfilename();


       String alias = profile.getApplication().getAlias();
      // String alias= appRep.findAll().get(0).getAlias();
        List<Role> role = profile.getRoles();
        List<Role> roles = new ArrayList<>();
        for (Role r : role) {
            roles.add(roleService.getRoleById(r.getRolename(), alias));
        }
        List<Profile> prof1 = this.Repository.findAll();
        int j = 0;
        while ((j < prof1.size()) && ((!prof1.get(j).getProfilename().equals(profileName))
                || (!prof1.get(j).getApplication().getAlias().equals(alias)))) {
            j++;
        }
        if (j < prof1.size()) {
            return updateProfile(request, profileName, alias, profile);
        }
        Application app = this.appRep.findByAlias(alias).get();

        String A = profile.getAuthor();
        String R = profile.getReader();
        if (A == null) {
            A = "ProfileWriter,SUPLOCALADMIN";
        } else {
            String[] aa = A.split(",");
            Boolean z = false;
            Boolean e = false;
            for (String a : aa) {
                if (a.equals("ProfileWriter")) {
                    z = true;
                }
                if (a.equals("SUPLOCALADMIN")) {
                    e = true;
                }
            }
            if (!z) {
                A += ",ProfileWriter";
            }
            if (!e) {
                A += ",SUPLOCALADMIN";
            }
        }
        if (R == null) {
            R = "ProfileReader";
        } else {
            String[] aa = R.split(",");
            Boolean z = false;
            for (String a2 : aa) {
                if (a2.equals("ProfileReader")) {
                    z = true;
                }
            }
            if (!z) {
                R += ",ProfileReader";
            }
        }
        LocalDate date = LocalDate.now();
        profile.setRoles(roles);
        profile.setAuthor(A);
        profile.setReader(R);
        profile.setApplication(app);
        profile.setDatecreate(date);
        profile.setDateupdate(date);
        profile.setCreatedby(username);
        HashMap<String, Profile> result = this.JsonFormat(this.Repository.save(profile));
        //String res = this.newcreate(profile);
        //profileEsRepository.save(profile);
        return ResponseEntity.ok(result);
    }

    /**
     * {@code PUT  /Profile/{profileName},{alias}} : update Profile.<br> <br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to update Profile.
     * @param request request param.
     * @param profile profile param.
     * @param alias alias param.
     * @param profileName profileName param.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of expands in body.
     * @throws IOException IOException is thrown.
     */
   //@PreAuthorize("hasAuthority('SUPLOCALADMIN') or hasAuthority('ProfileWriter') ")
    @PutMapping({"/Profile/{profileName},{alias}"})
    public ResponseEntity<HashMap<String, Profile>> updateProfile(HttpServletRequest request,
                                                                  @PathVariable String profileName, @PathVariable String alias, @Valid @RequestBody Profile profile)
            throws IOException {
        String token = request.getHeader("Authorization");
        String body = this.global.DecodeJWT(token.substring(token.indexOf("ey")));
        String[] list = body.split("\"authority\":\"");
        String username = body.substring(body.indexOf("\":\"") + 3, body.indexOf("\","));
        List<String> l = new ArrayList<String>();

        Profile p2 = this.Service.getProfilesById(profileName, alias);

        for (int i = 1; i < list.length; ++i) {
            l.add(list[i].substring(0, body.split("\"authority\":\"")[i].length() - 4));
        }
        String[] prv = null;
        List<String> l2 = new ArrayList<String>();
        if (p2.getAuthor() != null && !p2.getAuthor().isEmpty()) {
            prv = p2.getAuthor().split(",");
        }
        for (String p : prv) {
            l2.add(p);
        }
        if (this.global.valid(l2, l)) {
            LocalDate date = LocalDate.now();
            Application app = this.appRep.findByAlias(alias).get();
            List<Role> role = profile.getRoles();
            List<Role> roles = new ArrayList<>();
            for (Role r : role) {
                roles.add(roleService.getRoleById(r.getRolename(), alias));
            }

            String A = profile.getAuthor();
            String R = profile.getReader();
            if (A == null) {
                A = "ProfileWriter,SUPLOCALADMIN";
            } else {
                String[] aa = A.split(",");
                Boolean z = false;
                Boolean e = false;
                for (String a : aa) {
                    if (a.equals("ProfileWriter")) {
                        z = true;
                    }
                    if (a.equals("SUPLOCALADMIN")) {
                        e = true;
                    }
                }
                if (!z) {
                    A += ",ProfileWriter";
                }
                if (!e) {
                    A += ",SUPLOCALADMIN";
                }
            }
            if (R == null) {
                R = "ProfileReader";
            } else {
                String[] aa = R.split(",");
                Boolean z = false;
                for (String a2 : aa) {
                    if (a2.equals("ProfileReader")) {
                        z = true;
                    }
                }
                if (!z) {
                    R += ",ProfileReader";
                }
            }
            p2.setApplication(app);
            p2.setComment(profile.getComment());
            p2.setDateupdate(date);
            p2.setProfildescription(profile.getProfildescription());
            p2.setStatus(profile.isStatus());
            p2.setTest(profile.isTest());
            p2.setAuthor(A);
            p2.setReader(R);
            p2.setRoles(roles);
            p2.setModifiedby(username);
            HashMap<String, Profile> result = this.JsonFormat(this.Repository.save(p2));
            //String res = this.update(p2.getId(), p2);
            //profileEsRepository.save(p2);

            return ResponseEntity.ok(result);
        }
        return null;
    }

    /**
     * {@code GET  /getProfileActive} : get Profile Active.<br> <br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to get Profile Active.
     * @param status status param.
     * @return size.
     */
    @GetMapping({"/getProfileActive"})
    public int getProfileActive(@RequestParam Boolean status) {
        return this.Repository.findAllByStatus(status).size();
    }

    /**
     * {@code GET  /getProfileTest} : get Profile Test.<br> <br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to get Profile Test.
     * @param test test param.
     * @return size.
     */
    @GetMapping({"/getProfileTest"})
    public int getProfileTest(@RequestParam Boolean test) {
        return this.Repository.findAllByTest(test).size();
    }

    /**
     * {@code GET  /getProfileTotal} : get Profile Total.<br> <br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to get Profile Total.
     * @return size.
     */
    @GetMapping({"/getProfileTotal"})
    public int getProfileTotal() {
        return this.Repository.findAll().size();
    }

    /**
     * {@code DELETE  /Profile/{profileName},{alias}} : delete Profile.<br> <br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to delete Profile.
     * @param request request param.
     * @param alias alias param.
     * @param profileName profileName param.
     * @return msg.
     * @throws UnknownHostException UnknownHostException is thrown.
     * @throws MalformedURLException MalformedURLException is thrown.
     */
  // @PreAuthorize("hasAuthority('SUPLOCALADMIN') or hasAuthority('ProfileWriter') ")
    @DeleteMapping({"/Profile/{profileName},{alias}"})
    public String deleteProfile(HttpServletRequest request, @PathVariable String profileName,
                                @PathVariable String alias) throws UnknownHostException, MalformedURLException {
        String token = request.getHeader("Authorization");
        String body = this.global.DecodeJWT(token.substring(token.indexOf("ey")));
        String[] list = body.split("\"authority\":\"");
        String username = body.substring(body.indexOf("\":\"") + 3, body.indexOf("\","));
        List<String> l = new ArrayList<String>();
        for (int i = 1; i < list.length; ++i) {
            l.add(list[i].substring(0, body.split("\"authority\":\"")[i].length() - 4));
        }
        Profile std = this.Service.getProfilesById(profileName, alias);
        String[] prv = null;
        List<String> l2 = new ArrayList<String>();
        if (std.getAuthor() != null && !std.getAuthor().isEmpty()) {
            prv = std.getAuthor().split(",");
        }
        for (String p : prv) {
            l2.add(p);
        }
        if (this.global.valid(l2, l)) {
            this.Repository.delete(std);
            //profileEsRepository.delete(std);
            return "Document Deleted";
        }
        return null;
    }
}
