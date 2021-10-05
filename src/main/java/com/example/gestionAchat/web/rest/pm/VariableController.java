
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
import com.example.gestionAchat.entities.Variable;
import com.example.gestionAchat.repository.pm.AppRepository;
import com.example.gestionAchat.repository.pm.VarRepository;
import com.example.gestionAchat.service.pm.VariableServices;
import com.example.gestionAchat.service.pm.dto.VariableCriteria;
import com.example.gestionAchat.service.pm.query.VariableQueryService;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
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
import java.util.concurrent.ExecutionException;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping({"/api"})
public class VariableController {
    private static final Logger log;

    static {
        log = LoggerFactory.getLogger(VariableController.class);
    }

    private final Device global;
    @Autowired
    VariableServices Service;
    /*	@Autowired
        Config config;
        Client client;*/
    List<Variable> var;
    @Autowired
    private VarRepository Repository;
    //	@Autowired
//	private VarEsRepository EsRepository;
    @Autowired
    private AppRepository appRep;

    @Autowired
    private VariableQueryService variableQueryService;

    public VariableController() {
        this.global = new Device();

    }

    public HashMap<String, Variable> JsonFormat(Variable variable) {
        HashMap<String, Variable> dataOut = new HashMap<String, Variable>();
        dataOut.put("data", variable);
        return dataOut;
    }

    /**
     * {@code GET  /variables} : get all the variables.<br> <br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to get all the variables.
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of variables in body.
     */
    @GetMapping("/variables")
    public ResponseEntity<List<Variable>> getAllVariables(VariableCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Variables by criteria: {}", criteria);
        Page<Variable> page = variableQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /variables/count} : count all the variables.<br> <br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to count all the variables.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/variables/count")
    public ResponseEntity<Long> countVariables(VariableCriteria criteria) {
        log.debug("REST request to count Variables by criteria: {}", criteria);
        return ResponseEntity.ok().body(variableQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /variable} : get all variables.<br> <br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to get all variables.
     * @param request request param.
     * @return dataOut.
     */
    @PreAuthorize("hasAuthority('SUPLOCALADMIN') or hasAuthority('VarReader')  or hasAuthority('VarWriter')")
    @GetMapping({"/variable"})
    public HashMap<String, List<Variable>> allVariable(HttpServletRequest request) {
        List<Variable> variableList = this.Repository.findAll();
        List<Variable> variables = new ArrayList<Variable>();
        String token = request.getHeader("Authorization");
        String body = this.global.DecodeJWT(token.substring(token.indexOf("ey")));
        String[] list = body.split("\"authority\":\"");
        List<String> l = new ArrayList<String>();
        for (int i = 1; i < list.length; ++i) {
            l.add(list[i].substring(0, body.split("\"authority\":\"")[i].length() - 4));
        }
        for (Variable var : variableList) {
            String[] prv = null;
            String[] prv2 = null;
            List<String> l2 = new ArrayList<String>();
            if ((var.getReader() != null && !var.getReader().isEmpty())
                    || (var.getAuthor() != null && !var.getAuthor().isEmpty())) {
                prv = var.getReader().split(",");
                prv2 = var.getAuthor().split(",");
                for (String p : prv) {
                    l2.add(p);
                }
                for (String p : prv2) {
                    l2.add(p);
                }
            }
            if (this.global.valid(l2, l)) {
                variables.add(var);
            }
        }
        HashMap<String, List<Variable>> dataOut = new HashMap<String, List<Variable>>();
        dataOut.put("data", variables);
        return dataOut;
    }

    /**
     * {@code GET  /VariableByDBID/{id}} : get Variable By DBID.<br> <br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to get Variable By DBID.
     *
     * @param request request param.
     * @param id      id param.
     * @return dataOut.
     * @throws IOException IOException is thrown.
     */
   @PreAuthorize("hasAuthority('SUPLOCALADMIN') or hasAuthority('VarReader')  or hasAuthority('VarWriter')")
    @GetMapping({"/VariableByDBID/{id}"})
    public HashMap<String, Optional<Variable>> VariableByDBID(HttpServletRequest request, @PathVariable Long id)
            throws IOException {
        Optional<Variable> var = this.Repository.findById(id);
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
        if ((var.get().getReader() != null && !var.get().getReader().isEmpty())
                || (var.get().getAuthor() != null && !var.get().getAuthor().isEmpty())) {
            prv = var.get().getReader().split(",");
            prv2 = var.get().getAuthor().split(",");
            for (String p : prv) {
                l2.add(p);
            }
            for (String p : prv2) {
                l2.add(p);
            }
        }
        if (this.global.valid(l2, l)) {
            LocalDate date = LocalDate.now();
            HashMap<String, Optional<Variable>> dataOut = new HashMap<String, Optional<Variable>>();
            dataOut.put("data", var);
            return dataOut;
        }
        return null;
    }

    /**
     * {@code GET  /VariableById/{Name},{alias}} : get Variable By id.<br> <br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to get Variable By id.
     * @param request request param.
     * @param alias alias param.
     * @param Name Name param.
     * @return dataOut.
     * @throws IOException IOException is thrown.
     */
 @PreAuthorize("hasAuthority('SUPLOCALADMIN') or hasAuthority('VarReader')  or hasAuthority('VarWriter')")
    @GetMapping({"/VariableById/{Name},{alias}"})
    public HashMap<String, Variable> VariableById(HttpServletRequest request, @PathVariable String Name,
                                                  @PathVariable String alias) throws IOException, ScriptException {
        Variable var = this.Service.getvariableById(Name, alias);
        String token = request.getHeader("Authorization");
        String body = this.global.DecodeJWT(token.substring(token.indexOf("ey")));
        String username = body.substring(body.indexOf("\":\"") + 3, body.indexOf("\","));
        String value=var.getValue();
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine nashorn = scriptEngineManager.getEngineByName("nashorn");
        String values = nashorn.eval(value).toString();
        var.setValue(values);

        String[] list = body.split("\"authority\":\"");
        List<String> l = new ArrayList<String>();
        for (int i = 1; i < list.length; ++i) {
            l.add(list[i].substring(0, body.split("\"authority\":\"")[i].length() - 4));
        }
        String[] prv = null;
        String[] prv2 = null;
        List<String> l2 = new ArrayList<String>();
        if ((var.getReader() != null && !var.getReader().isEmpty())
                || (var.getAuthor() != null && !var.getAuthor().isEmpty())) {
            prv = var.getReader().split(",");
            prv2 = var.getAuthor().split(",");
            for (String p : prv) {
                l2.add(p);
            }
            for (String p : prv2) {
                l2.add(p);
            }
        }
        if (this.global.valid(l2, l)) {
            LocalDate date = LocalDate.now();
            HashMap<String, Variable> dataOut = new HashMap<String, Variable>();

            dataOut.put("data", var);
            return dataOut;
        }
        return null;
    }

    /**
     * {@code POST  /variable} : add Variable.<br> <br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to add Variable.
     * @param request request param.
     * @param variable variable param.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of expands in body.
     * @throws IOException IOException is thrown.
     * @throws ScriptException ScriptException is thrown.
     * @throws InterruptedException InterruptedException is thrown.
     * @throws ExecutionException ExecutionException is thrown.
     */
  @PreAuthorize("hasAuthority('SUPLOCALADMIN') or hasAuthority('VarWriter') ")
    @PostMapping({"/variable"})
    public ResponseEntity<HashMap<String, Variable>> addVariable(HttpServletRequest request,
                                                                 @Valid @RequestBody Variable variable)
            throws IOException, ScriptException, InterruptedException, ExecutionException {
        String token = request.getHeader("Authorization");
        String body = this.global.DecodeJWT(token.substring(token.indexOf("ey")));
        String[] list = body.split("\"authority\":\"");
        String username = body.substring(body.indexOf("\":\"") + 3, body.indexOf("\","));
        List<String> l = new ArrayList<String>();
        for (int i = 1; i < list.length; ++i) {
            l.add(list[i].substring(0, body.split("\"authority\":\"")[i].length() - 4));
        }
        String varName = variable.getName();
        String alias = variable.getApplication().getAlias();
        this.var = this.Repository.findAll();
        int j = 0;
        while ((j < this.var.size()) && ((!this.var.get(j).getName().equals(varName))
                || (!this.var.get(j).getApplication().getAlias().equals(alias))))
            j++;

        if (j < this.var.size()) {
            return updateVariable(request, varName, alias, variable);
        } else {
            String A = variable.getAuthor();
            String R = variable.getReader();
            if (A == null) {
                A = "VarWriter,SUPLOCALADMIN";
            } else {
                String[] aa = A.split(",");
                Boolean z = false;
                Boolean e = false;
                for (String a : aa) {
                    if (a.equals("VarWriter")) {
                        z = true;
                    }
                    if (a.equals("SUPLOCALADMIN")) {
                        e = true;
                    }
                }
                if (!z) {
                    A += ",VarWriter";
                }
                if (!e) {
                    A += ",SUPLOCALADMIN";
                }
            }
            if (R == null) {
                R = "VarReader";
            } else {
                String[] aa = R.split(",");
                Boolean z = false;
                for (String a2 : aa) {
                    if (a2.equals("VarReader")) {
                        z = true;
                    }
                }
                if (!z) {
                    R += ",VarReader";
                }
            }
            LocalDate date = LocalDate.now();
            String name = variable.getName() + "=" + variable.getValue() + ";" + variable.getName();
            ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
            ScriptEngine nashorn = scriptEngineManager.getEngineByName("nashorn");
            nashorn.eval(name);
            variable.setCreatedby(username);
            variable.setAuthor(A);
            variable.setReader(R);
            variable.setApplication(appRep.findByAlias(alias).get());
            HashMap<String, Variable> result = this.JsonFormat(this.Repository.save(variable));
            // String req = this.newcreate(variable);
            //EsRepository.save(variable);
            return ResponseEntity.ok(result);
        }
    }

    /**
     * {@code PUT  /variable/{Name},{alias}} : update Variable.<br> <br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to update Variable.
     * @param request request param.
     * @param variable variable param.
     * @param alias alias param.
     * @param Name Name param.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of expands in body.
     * @throws IOException IOException is thrown.
     * @throws ScriptException ScriptException is thrown.
     * @throws InterruptedException InterruptedException is thrown.
     * @throws ExecutionException ExecutionException is thrown.
     */
  //  @PreAuthorize("hasAuthority('SUPLOCALADMIN') or hasAuthority('VarWriter') ")
    @PutMapping({"/variable/{Name},{alias}"})
    public ResponseEntity<HashMap<String, Variable>> updateVariable(HttpServletRequest request,
                                                                    @PathVariable String Name, @PathVariable String alias, @Valid @RequestBody Variable variable)
            throws IOException, InterruptedException, ExecutionException, ScriptException {
        String token = request.getHeader("Authorization");
        String body = this.global.DecodeJWT(token.substring(token.indexOf("ey")));
        String[] list = body.split("\"authority\":\"");
        String username = body.substring(body.indexOf("\":\"") + 3, body.indexOf("\","));
        List<String> l = new ArrayList<String>();
        for (int i = 1; i < list.length; ++i) {
            l.add(list[i].substring(0, body.split("\"authority\":\"")[i].length() - 4));
        }
        Variable var = this.Service.getvariableById(Name, alias);
        String[] prv = null;
        List<String> l2 = new ArrayList<String>();
        if (var.getAuthor() != null && !var.getAuthor().isEmpty()) {
            String[] split;
            prv = (split = var.getAuthor().split(","));
            for (String p : split) {
                l2.add(p);
            }
        }
        if (this.global.valid(l2, l)) {
            String name = variable.getName() + "=" + variable.getValue() + ";" + variable.getName();
            ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
            ScriptEngine nashorn = scriptEngineManager.getEngineByName("nashorn");
            LocalDate date = LocalDate.now();

            nashorn.eval(name);
            String A = variable.getAuthor();
            String R = variable.getReader();
            if (A == null) {
                A = "VarWriter,SUPLOCALADMIN";
            } else {
                String[] aa = A.split(",");
                Boolean z = false;
                Boolean e = false;
                for (String a : aa) {
                    if (a.equals("VarWriter")) {
                        z = true;
                    }
                    if (a.equals("SUPLOCALADMIN")) {
                        e = true;
                    }
                }
                if (!z) {
                    A += ",VarWriter";
                }
                if (!e) {
                    A += ",SUPLOCALADMIN";
                }
            }
            if (R == null) {
                R = "VarReader";
            } else {
                String[] aa = R.split(",");
                Boolean z = false;
                for (String a2 : aa) {
                    if (a2.equals("VarReader")) {
                        z = true;
                    }
                }
                if (!z) {
                    R += ",VarReader";
                }
            }
            var.setModifiedby(username);
            var.setApplication(appRep.findByAlias(alias).get());
            var.setAuthor(A);
            var.setReader(R);
            var.setComment(variable.getComment());
            var.setDateupdate(date);
            var.setDescription(variable.getDescription());
            var.setStatus(variable.isStatus());
            var.setTest(variable.isTest());
            var.setValue(variable.getValue());
            HashMap<String, Variable> result = this.JsonFormat(Repository.save(var));
            // String res = this.update(var.getId(), var);
            //EsRepository.save(var);
            return ResponseEntity.ok(result);

        }
        return null;
    }

    /**
     * {@code DELETE  /variable/{Name},{alias}} : delete Variable.<br> <br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to delete Variable.
     *
     * @param request request param.
     * @param alias   alias param.
     * @param Name    Name param.
     * @throws UnknownHostException  UnknownHostException is thrown.
     * @throws MalformedURLException MalformedURLException is thrown.
     */
  //  @PreAuthorize("hasAuthority('SUPLOCALADMIN') or hasAuthority('VarWriter') ")
    @DeleteMapping({"/variable/{alias},{Name}"})
    public void deleteVariable(HttpServletRequest request, @PathVariable String alias, @PathVariable String Name)
            throws UnknownHostException, MalformedURLException {
        String token = request.getHeader("Authorization");
        String body = this.global.DecodeJWT(token.substring(token.indexOf("ey")));
        String[] list = body.split("\"authority\":\"");
        String username = body.substring(body.indexOf("\":\"") + 3, body.indexOf("\","));
        List<String> l = new ArrayList<String>();
        for (int i = 1; i < list.length; ++i) {
            l.add(list[i].substring(0, body.split("\"authority\":\"")[i].length() - 4));
        }
        Variable std = this.Service.getvariableById(Name, alias);
        String[] prv = null;
        List l2 = new ArrayList();
        if (std.getAuthor() != null && !std.getAuthor().isEmpty()) {
            String[] split;
            prv = (split = std.getAuthor().split(","));
            for (String p : split) {
                l2.add(p);
            }
        }
        if (this.global.valid(l2, l)) {
            this.Repository.delete(std);
        }
    }

    /**
     * {@code GET  /getVarActive} : get Variable active.<br> <br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to get Variable active.
     * @param status status param.
     * @return size.
     */
    @GetMapping({"/getVarActive"})
    public int getVarActive(@RequestParam Boolean status) {
        return this.Repository.findAllByStatus(status).size();
    }

    /**
     * {@code GET  /getVarTest} : get Variable test.<br> <br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to get Variable test.
     * @param test test param.
     * @return size.
     */
    @GetMapping({"/getVarTest"})
    public int getVarTest(@RequestParam Boolean test) {
        return this.Repository.findAllByTest(test).size();
    }

    /**
     * {@code GET  /getVarTotal} : get Variable total.<br> <br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to get Variable total.
     * @return size.
     */
    @GetMapping({"/getVarTotal"})
    public int getVarTotal() {
        return this.Repository.findAll().size();
    }

    /**
     * {@code GET  /getVariableByApplication/{alias}} : get Profiles By Application.<br> <br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to get Profiles By Application.
     * @param alias alias param.
     * @return size.
     */
   // @PreAuthorize("hasAuthority('SUPLOCALADMIN') or hasAuthority('VarReader') ")
    @GetMapping({"/getVariableByApplication/{alias}"})
    public HashMap<String, List<Variable>> getProfilesByApplication(@PathVariable String alias) {
        List<Variable> variableList = this.Service.getVariablesByApplication(alias);
        HashMap<String, List<Variable>> dataOut = new HashMap<String, List<Variable>>();
        dataOut.put("data", variableList);
        return dataOut;
    }
}
