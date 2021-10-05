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
import com.example.gestionAchat.entities.AuthAffectation;
import com.example.gestionAchat.entities.User;
import com.example.gestionAchat.entities.UserAccess;
import com.example.gestionAchat.repository.pm.AppRepository;
import com.example.gestionAchat.repository.pm.AuthAffectationRepository;
import com.example.gestionAchat.repository.pm.UserRepository;
import com.example.gestionAchat.service.pm.UserAccessService;
import com.example.gestionAchat.service.pm.dto.AuthAffectationCriteria;
import com.example.gestionAchat.service.pm.query.AuthAffectationQueryService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;
import com.example.gestionAchat.entities.Application;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping({"/api"})
public class AuthAffectationController {
    private static Logger log;

    static {
        log = LoggerFactory.getLogger(AuthAffectationController.class);
    }

    private final Device global;
    @Autowired
    private AuthAffectationRepository affectationRepository;
    @Autowired
    private UserAccessService access;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AppRepository appRepository;
    @Autowired
    private AuthAffectationQueryService authAffectationQueryService;

    public AuthAffectationController() {
        this.global = new Device();

    }

    /**
     * {@code GET  /auth-affectations} : get all the authAffectations.<br> <br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to get all the authAffectations.
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of authAffectations in body.
     */
@PreAuthorize("hasAuthority('SUPLOCALADMIN') or hasAuthority('SysReader') or hasAuthority('SysWriter')")

    @GetMapping("/auth-affectations")
    public ResponseEntity<List<AuthAffectation>> getAllAuthAffectations(AuthAffectationCriteria criteria, Pageable pageable) {
        log.debug("REST request to get AuthAffectations by criteria: {}", criteria);
        Page<AuthAffectation> page = authAffectationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /getUserBygroupe} : get User By groupe.<br> <br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to get User By groupe.
     * @param groupe groupe param.
     * @return List of User.
     * @throws UnsupportedEncodingException UnsupportedEncodingException is thrown.
     */
  @PreAuthorize("hasAuthority('SUPLOCALADMIN') or hasAuthority('SysReader') or hasAuthority('SysWriter')")

    @GetMapping({"/getUserBygroupe"})
    public List<User> getUserBygroupe(@RequestParam("groupe") String groupe) throws UnsupportedEncodingException {
        return userRepository.findAllByMemberof(groupe);
    }

    /**
     * {@code GET  /auth-affectation/{id}} : get Affectation.<br> <br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to get Affectation.
     * @param id id param.
     * @return AuthAffectation.
     */
 @PreAuthorize("hasAuthority('SUPLOCALADMIN') or hasAuthority('SysReader') or hasAuthority('SysWriter')")

    @GetMapping({"/auth-affectation/{id}"})
    public Optional<AuthAffectation> getAffectation(@PathVariable Long id) {
        return affectationRepository.findById(id);
    }

    /**
     * {@code GET  /auth-affectations/count} : count all the authAffectations.<br> <br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to count all the authAffectations.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/auth-affectations/count")
    public ResponseEntity<Long> countAuthAffectations(AuthAffectationCriteria criteria) {
        log.debug("REST request to count AuthAffectations by criteria: {}", criteria);
        return ResponseEntity.ok().body(authAffectationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code POST  /auth-affectations} : add Affectation.<br> <br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to add Affectation.
     * @param auth auth is thrown.
     * @param request request is thrown.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
   @PreAuthorize("hasAuthority('SUPLOCALADMIN') or hasAuthority('SysWriter')")

    @PostMapping({"/auth-affectation"})
    public AuthAffectation addAffectation(HttpServletRequest request, @RequestBody AuthAffectation auth) {
        String token = request.getHeader("Authorization");
        String body = this.global.DecodeJWT(token.substring(token.indexOf("ey")));
        String username = body.substring(body.indexOf("\":\"") + 3, body.indexOf("\","));
        auth.setAuthor("SysWriter,SUPLOCALADMIN");
        auth.setReader("SysReader");
        auth.setCreatedby(username);
        return affectationRepository.save(auth);
    }

    /**
     * {@code PUT  /auth-affectation/{id}} : update Affectation.<br> <br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to update Affectation.
     * @param auth auth is thrown.
     * @param request request is thrown.
     * @param id id is thrown.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
   @PreAuthorize("hasAuthority('SUPLOCALADMIN') or hasAuthority('SysWriter')")
    @PutMapping({"/auth-affectation/{id}"})
    public AuthAffectation apdateAffectation(HttpServletRequest request, @RequestBody AuthAffectation auth,
                                             @PathVariable Long id) {
        String token = request.getHeader("Authorization");
        String body = this.global.DecodeJWT(token.substring(token.indexOf("ey")));
        String username = body.substring(body.indexOf("\":\"") + 3, body.indexOf("\","));
        AuthAffectation a = affectationRepository.findById(id).get();
        a.setAuthor("SysWriter,SUPLOCALADMIN");
        a.setReader("SysReader");
        a.setModifiedby(username);
        a.setComment(auth.getComment());
        a.setTest(auth.isTest());
        a.setStatus(auth.isStatus());
        a.setApplication(auth.getApplication());
        a.setGroupe(auth.getGroupe());
        a.setProfile(auth.getProfile());
        a.setOrders(auth.getOrders());
        return affectationRepository.save(a);
    }

    /**
     * {@code DELETE  /auth-affectation/{id}} : delete Affectation.<br> <br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to delete Affectation.
     * @param id id is thrown.
     * @return msg.
     */
  @PreAuthorize("hasAuthority('SUPLOCALADMIN') or hasAuthority('SysWriter')")
    @DeleteMapping({"/auth-affectation/{id}"})
    public String deleteAffectation(@PathVariable Long id) {
        affectationRepository.deleteById(id);
        return "Deleted";
    }

    /**
     * {@code DELETE  /auth-affectation/{id}} : delete Affectation.<br> <br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to delete Affectation.
     * @param alias alias is thrown.
     * @param request request is thrown.
     * @return String.
     * @throws IOException IOException is thrown.
     */
   @PreAuthorize("hasAuthority('SUPLOCALADMIN') or hasAuthority('SysWriter')")
    @PostMapping({"/affectProfileGroups"})
    public String authrun(HttpServletRequest request, @RequestParam String alias) throws IOException {
        String token = request.getHeader("Authorization");
        String body = this.global.DecodeJWT(token.substring(token.indexOf("ey")));
        String username = body.substring(body.indexOf("\":\"") + 3, body.indexOf("\","));
        return run(username, alias);
    }


    //@PostMapping({ "/run" })
    public String run(String username, String alias) throws IOException {
        try {
            Application app = appRepository.findByAlias(alias).get();
            List<AuthAffectation> list = affectationRepository.findAllByOrder(app);
            for (AuthAffectation a : list) {
                run(username, a);
            }
            return "Affect";
        } catch (Exception e) {
            return "Erreur  " + e;
        }
    }

    public void run(String username, AuthAffectation auth) throws IOException {
        List<User> users = getUserBygroupe(auth.getGroupe().trim());
        for (User u : users) {
            UserAccess aces = new UserAccess();
            aces.setApplication(auth.getApplication());
            aces.setProfile(auth.getProfile());
            aces.setUser(u);
            aces.setComment("Affectation automatique");
            aces.setStatus(true);
            aces.setTest(false);
            access.addAccess(username, aces);
        }
    }

}
