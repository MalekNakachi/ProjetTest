
package com.example.gestionAchat.web.rest.pm;

import com.example.gestionAchat.config.Device;
import com.example.gestionAchat.service.pm.query.ApplicationQueryService;
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


import com.example.gestionAchat.repository.pm.AppRepository;
import com.example.gestionAchat.service.pm.dto.ApplicationCriteria;


import javax.script.ScriptException;
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
//@Slf4j
public class AppController {
    private final Logger log = LoggerFactory.getLogger(AppController.class);
    private final ApplicationQueryService applicationQueryService;
    private final Device global;
    //Client client;
    List<Application> appl;
    /*	@Autowired
        Config config;*/
    boolean estem;
    @Autowired
    private AppRepository Repository;
    //	@Autowired
//	private AppEsRepository appEsRepository;


    public AppController(ApplicationQueryService applicationQueryService) {
        this.applicationQueryService = applicationQueryService;
        this.global = new Device();

    }

    public HashMap<String, Application> JsonFormat(Application application) {
        HashMap<String, Application> dataOut = new HashMap<String, Application>();
        dataOut.put("data", application);
        return dataOut;
    }

    /**
     * {@code GET  /applications} : get all the applications.<br><br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to get all the applications.
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of applications in body.
     */
    @GetMapping("/applications")
    public ResponseEntity<List<Application>> getAllApplications(ApplicationCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Applications by criteria: {}", criteria);
        Page<Application> page = applicationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /applications/count} : count all the applications.<br><br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to count all the applications.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/applications/count")
    public ResponseEntity<Long> countApplications(ApplicationCriteria criteria) {
        log.debug("REST request to count Applications by criteria: {}", criteria);
        return ResponseEntity.ok().body(applicationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /applications} : get applications.<br><br>
     * <b>AUTHORIZATIONS : </b> <br> <br> SUPLOCALADMIN OR AppReader OR AppWriter  : is authorized to get applications.
     * @param request request param.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
 @PreAuthorize("hasAuthority('SUPLOCALADMIN') or hasAuthority('AppReader') or hasAuthority('AppWriter') ")

    @GetMapping({"/Application"})
    public HashMap<String, List<Application>> allApplication(HttpServletRequest request) {
        List<Application> applicationList = this.Repository.findAll();
        List<Application> application = new ArrayList<Application>();
        String token = request.getHeader("Authorization");
        String body = this.global.DecodeJWT(token.substring(token.indexOf("ey")));
        String[] list = body.split("\"authority\":\"");
        List<String> l = new ArrayList<String>();
        for (int i = 1; i < list.length; ++i) {
            l.add(list[i].substring(0, body.split("\"authority\":\"")[i].length() - 4));
        }
        for (Application app : applicationList) {
            String[] prv = null;
            String[] prv2 = null;
            List<String> l2 = new ArrayList<String>();
            if ((app.getReader() != null && !app.getReader().isEmpty())
                    || (app.getAuthor() != null && !app.getAuthor().isEmpty())) {
                prv = app.getReader().split(",");
                prv2 = app.getAuthor().split(",");
                for (String p : prv) {
                    l2.add(p);
                }
                for (String p : prv2) {
                    l2.add(p);
                }
            }
            if (this.global.valid(l2, l)) {
                application.add(app);
            }
        }
        HashMap<String, List<Application>> dataOut = new HashMap<String, List<Application>>();
        dataOut.put("data", application);
        return dataOut;
    }

    /**
     * {@code GET  /ApplicationByDBID/{id}} : get Application By DBID.<br><br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to get Application By DBID.
     *
     * @param request request param.
     * @param id      id param.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     * @throws IOException IOException is thrown.
     */
 @PreAuthorize("hasAuthority('SUPLOCALADMIN') or hasAuthority('AppReader') or hasAuthority('AppWriter') ")

    @GetMapping({"/ApplicationByDBID/{id}"})
    public HashMap<String, Optional<Application>> ApplicationByDBID(HttpServletRequest request, @PathVariable Long id)
            throws IOException {
        Optional<Application> app = Repository.findById(id);
        String token = request.getHeader("Authorization");
        String body = this.global.DecodeJWT(token.substring(token.indexOf("ey")));
        String username = body.substring(body.indexOf("\":\"") + 3, body.indexOf("\","));

        String[] list = body.split("\"authority\":\"");
        List<String> l = new ArrayList<String>();
        for (int i = 1; i < list.length; ++i) {
            l.add(list[i].substring(0, body.split("\"authority\":\"")[i].length() - 4));
        }
        String[] prv = null;
        String[] prv2 = null;
        List<String> l2 = new ArrayList<String>();
        if ((app.get().getReader() != null && !app.get().getReader().isEmpty())
                || (app.get().getAuthor() != null && !app.get().getAuthor().isEmpty())) {
            prv = app.get().getReader().split(",");
            prv2 = app.get().getAuthor().split(",");
            for (String p : prv) {
                l2.add(p);
            }
            for (String p : prv2) {
                l2.add(p);
            }
        }
        if (this.global.valid(l2, l)) {
            HashMap<String, Optional<Application>> dataOut = new HashMap<String, Optional<Application>>();
            dataOut.put("data", app);

            return dataOut;
        }
        return null;
    }

    /**
     * {@code GET  /ApplicationById/{alias}} : get Application By id.<br><br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to get Application By id.
     * @param request request param.
     * @param alias alias param.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     * @throws IOException IOException is thrown.
     */
  @PreAuthorize("hasAuthority('SUPLOCALADMIN') or hasAuthority('AppReader') or hasAuthority('AppWriter') ")

    @GetMapping({"/ApplicationById/{alias}"})
    public HashMap<String, Optional<Application>> ApplicationById(HttpServletRequest request, @PathVariable String alias) throws IOException {
        Optional<Application> app = this.Repository.findByAlias(alias);
        String token = request.getHeader("Authorization");
        String body = this.global.DecodeJWT(token.substring(token.indexOf("ey")));
        String username = body.substring(body.indexOf("\":\"") + 3, body.indexOf("\","));
        String[] list = body.split("\"authority\":\"");
        List<String> l = new ArrayList<String>();
        for (int i = 1; i < list.length; ++i) {
            l.add(list[i].substring(0, body.split("\"authority\":\"")[i].length() - 4));
        }
        String[] prv = null;
        String[] prv2 = null;
        List<String> l2 = new ArrayList<String>();
        if ((app.get().getReader() != null && !app.get().getReader().isEmpty())
                || (app.get().getAuthor() != null && !app.get().getAuthor().isEmpty())) {
            prv = app.get().getReader().split(",");
            prv2 = app.get().getAuthor().split(",");
            for (String p : prv) {
                l2.add(p);
            }
            for (String p : prv2) {
                l2.add(p);
            }
        }
        if (this.global.valid(l2, l)) {
            LocalDate date = LocalDate.now();
            HashMap<String, Optional<Application>> dataOut = new HashMap<String, Optional<Application>>();
            dataOut.put("data", app);
            return dataOut;
        }
        return null;
    }

    /**
     * {@code POST  /Application} : add Application.<br><br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to add Application.
     * @param request request param.
     * @param application application param.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     * @throws IOException IOException is thrown.
     * @throws ScriptException ScriptException is thrown.
     */
 @PreAuthorize("hasAuthority('SUPLOCALADMIN') or hasAuthority('AppWriter') ")
    @PostMapping({"/Application"})
    public ResponseEntity<HashMap<String, Application>> addApplication(HttpServletRequest request,
                                                                       @Valid @RequestBody Application application) throws IOException, ScriptException {
        String token = request.getHeader("Authorization");
        String body = this.global.DecodeJWT(token.substring(token.indexOf("ey")));
        String username = body.substring(body.indexOf("\":\"") + 3, body.indexOf("\","));
        String alias = application.getAlias();
        this.appl = this.Repository.findAll();
        int i;
        for (i = 0; i < this.appl.size() && !this.appl.get(i).getAlias().equals(alias); ++i) {
        }
        if (i < this.appl.size()) {
            return updateApplication(request, alias, application);

        }
        String A = application.getAuthor();
        String R = application.getReader();
        if (A == null) {
            A = "AppWriter,SUPLOCALADMIN";
        } else {
            String[] aa = A.split(",");
            Boolean z = false;
            Boolean e = false;
            for (String a : aa) {
                if (a.equals("AppWriter")) {
                    z = true;
                }
                if (a.equals("SUPLOCALADMIN")) {
                    e = true;
                }
            }
            if (!z) {
                A += ",AppWriter";
            }
            if (!e) {
                A += ",SUPLOCALADMIN";
            }
        }
        if (R == null) {
            R = "AppReader";
        } else {
            String[] aa = R.split(",");
            Boolean z = false;
            for (String a2 : aa) {
                if (a2.equals("AppReader")) {
                    z = true;
                }
            }
            if (!z) {
                R += ",AppReader";
            }
        }
        LocalDate date = LocalDate.now();
        application.setCreatedby(username);
        application.setAuthor(A);
        application.setReader(R);
        application.setDatecreate(date);
        application.setDateupdate(date);
        HashMap<String, Application> result = this.JsonFormat(this.Repository.save(application));
        //String res = this.newcreate(application);
        //appEsRepository.save(application);
        return ResponseEntity.ok(result);
    }

    /**
     * {@code PUT  /Application/{alias}} : update Application.<br><br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to update Application.
     * @param request request param.
     * @param application application param.
     * @param alias alias param.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     * @throws IOException IOException is thrown.
     * @throws ScriptException ScriptException is thrown.
     */
    @PreAuthorize("hasAuthority('SUPLOCALADMIN') or hasAuthority('AppWriter') ")

    @PutMapping({"/Application/{alias}"})
    public ResponseEntity<HashMap<String, Application>> updateApplication(HttpServletRequest request,
                                                                          @PathVariable String alias, @Valid @RequestBody Application application) throws IOException, ScriptException {
        String token = request.getHeader("Authorization");
        String body = this.global.DecodeJWT(token.substring(token.indexOf("ey")));
        String[] list = body.split("\"authority\":\"");
        String username = body.substring(body.indexOf("\":\"") + 3, body.indexOf("\","));
        List<String> l = new ArrayList<String>();
        for (int i = 1; i < list.length; ++i) {
            l.add(list[i].substring(0, body.split("\"authority\":\"")[i].length() - 4));
        }
        Optional<Application> std = this.Repository.findByAlias(alias);
        String[] prv = null;
        List<String> l2 = new ArrayList<String>();
        if (std.get().getAuthor() != null && !std.get().getAuthor().isEmpty()) {
            prv = std.get().getAuthor().split(",");
        }
        for (String p : prv) {
            l2.add(p);
        }
        if (this.global.valid(l2, l)) {
            LocalDate date = LocalDate.now();

            if (std.isPresent()) {
                Application app = std.get();
                String A = application.getAuthor();
                String R = application.getReader();
                if (A == null) {
                    A = "AppWriter,SUPLOCALADMIN";
                } else {
                    String[] aa = A.split(",");
                    Boolean z = false;
                    Boolean e = false;
                    for (String a : aa) {
                        if (a.equals("AppWriter")) {
                            z = true;
                        }
                        if (a.equals("SUPLOCALADMIN")) {
                            e = true;
                        }
                    }
                    if (!z) {
                        A += ",AppWriter";
                    }
                    if (!e) {
                        A += ",SUPLOCALADMIN";
                    }
                }
                if (R == null) {
                    R = "AppReader";
                } else {
                    String[] aa = R.split(",");
                    Boolean z = false;
                    for (String a2 : aa) {
                        if (a2.equals("AppReader")) {
                            z = true;
                        }
                    }
                    if (!z) {
                        R += ",AppReader";
                    }
                }
                app.setModifiedby(username);
                app.setAppdescription(application.getAppdescription());
                app.setAppname(application.getAppname());
                app.setComment(application.getComment());
                app.setDateupdate(date);
                app.setStatus(application.isStatus());
                app.setTest(application.isTest());
                app.setReader(R);
                app.setAuthor(A);
                HashMap<String, Application> result = this.JsonFormat(this.Repository.save(app));
                // res = this.update(app.getId(), app);
                //appEsRepository.save(app);
                return ResponseEntity.ok(result);
            }
        }
        return (ResponseEntity<HashMap<String, Application>>) ResponseEntity.badRequest();
    }

    /**
     * {@code DELETE  /Application/{alias}} : update Application.<br><br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to update Application.
     * @param request request param.
     * @param alias alias param.
     * @throws UnknownHostException UnknownHostException is thrown.
     * @throws MalformedURLException MalformedURLException is thrown.
     */
 @PreAuthorize("hasAuthority('SUPLOCALADMIN') or hasAuthority('AppWriter') ")
    @DeleteMapping({"/Application/{alias}"})
    public void deleteApplication(HttpServletRequest request, @PathVariable String alias)
            throws UnknownHostException, MalformedURLException {
        String token = request.getHeader("Authorization");
        String body = this.global.DecodeJWT(token.substring(token.indexOf("ey")));
        String[] list = body.split("\"authority\":\"");
        String username = body.substring(body.indexOf("\":\"") + 3, body.indexOf("\","));
        List<String> l = new ArrayList<String>();
        for (int i = 1; i < list.length; ++i) {
            l.add(list[i].substring(0, body.split("\"authority\":\"")[i].length() - 4));
        }
        Optional<Application> std = this.Repository.findByAlias(alias);
        String[] prv = null;
        List<String> l2 = new ArrayList<String>();
        if (std.get().getAuthor() != null && !std.get().getAuthor().isEmpty()) {
            prv = std.get().getAuthor().split(",");
        }
        for (String p : prv) {
            l2.add(p);
        }
        if (this.global.valid(l2, l)) {
            LocalDate date = LocalDate.now();
            //String res = this.delete(std.get().getId());
            //appEsRepository.delete(std.get());
            this.Repository.delete(std.get());
        }
    }

    /**
     * {@code GET  /getAppActive} : get AppActive.<br><br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to get AppActive.
     * @param status status param.
     * @return size
     */
    @GetMapping({"/getAppActive"})
    public int getAppActive(@RequestParam Boolean status) {
        return this.Repository.findAllByStatus(status).size();
    }

    /**
     * {@code GET  /getAppTest} : get AppTest.<br><br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to get AppActive.
     * @param test test param.
     * @return size
     */
    @GetMapping({"/getAppTest"})
    public int getAppTest(@RequestParam Boolean test) {
        return this.Repository.findAllByTest(test).size();
    }

    /**
     * {@code GET  /getAppTotal} : get AppTotal.<br><br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to get get AppTotal.
     * @return size
     */
    @GetMapping({"/getAppTotal"})
    public int getAppTotal() {
        return this.Repository.findAll().size();
    }

}
