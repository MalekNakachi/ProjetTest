
package com.example.gestionAchat.web.rest.pm;

import com.example.gestionAchat.entities.Dept_hierarchy;
import com.example.gestionAchat.entities.User;
import com.example.gestionAchat.repository.pm.UserRepository;
import com.example.gestionAchat.service.pm.DepartmentService;
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
import com.example.gestionAchat.config.Device;
import com.example.gestionAchat.entities.ConfigLdap;
import com.example.gestionAchat.entities.Department;
import com.example.gestionAchat.repository.pm.DepRepository;
import com.example.gestionAchat.repository.pm.LdapConfigRepository;
import com.example.gestionAchat.service.pm.dto.DepartmentCriteria;
import com.example.gestionAchat.service.pm.query.DepartmentQueryService;

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

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping({"/api"})
public class DepController {
    private final Logger log = LoggerFactory.getLogger(DepController.class);
    private final Device global;
    //Client client;
    List<Department> dep;
    @Autowired
    private DepRepository Repository;
   	@Autowired
	private UserRepository userRepository;



    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private DepartmentQueryService departmentQueryService;
    /*	@Autowired
        Config config;*/
    @Autowired
    private LdapConfigRepository ldapConfigRepository;

    public DepController() {
        this.global = new Device();

    }

    public HashMap<String, Department> JsonFormat(Department department) {
        HashMap<String, Department> dataOut = new HashMap<String, Department>();
        dataOut.put("data", department);
        return dataOut;
    }

    /**
     * {@code GET  /departments} : get all the departments.<br><br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to get all the departments.
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     * of departments in body.
     */
    @GetMapping("/departments")
    public ResponseEntity<List<Department>> getAllDepartments(DepartmentCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Departments by criteria: {}", criteria);
        Page<Department> page = departmentQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil
                .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /departments/count} : count all the departments.<br><br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to count all the departments.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count
     * in body.
     */
    @GetMapping("/departments/count")
    public ResponseEntity<Long> countDepartments(DepartmentCriteria criteria) {
        log.debug("REST request to count Departments by criteria: {}", criteria);
        return ResponseEntity.ok().body(departmentQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /Department} : get all departments.<br><br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to get all departments.
     * @param request request param.
     * @return dataOut.
     */
//  @PreAuthorize("hasAuthority('SUPLOCALADMIN') or hasAuthority('DepReader') or hasAuthority('DepWriter')")
 // @PreAuthorize("hasAuthority('DepReader') or hasAuthority('DepWriter')")
    @GetMapping({"/Department"})
    public HashMap<String, List<Department>> allDepartements(HttpServletRequest request) {

      return this.departmentService.allDepartments(request,this.global);

    }

 //   String manager = dep.get().getManagedby();




    /**
     * {@code GET  /DepByDBID/{id}} : get Department By DBID.<br><br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to get Department By DBID.
     *
     * @param request request param.
     * @param id      id param.
     * @return dataOut.
     * @throws IOException IOException is thrown.
     */
 // @PreAuthorize("hasAuthority('SUPLOCALADMIN') or hasAuthority('DepReader') or hasAuthority('DepWriter')")
  //  @PreAuthorize("hasAuthority('MMAdmin') or hasAuthority('DepReader') or hasAuthority('DepWriter')")
    @GetMapping({"/DepByDBID/{id}"})
    public HashMap<String, Optional<Department>> DepByDBID(HttpServletRequest request, @PathVariable Long id)
            throws IOException {
        Optional<Department> dep = this.Repository.findById(id);
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
        if ((dep.get().getReader() != null && !dep.get().getReader().isEmpty())
                || (dep.get().getAuthor() != null && !dep.get().getAuthor().isEmpty())) {
            prv = dep.get().getReader().split(",");
            prv2 = dep.get().getAuthor().split(",");
            for (String p : prv) {
                l2.add(p);
            }
            for (String p : prv2) {
                l2.add(p);
            }
        }
        if (this.global.valid(l2, l)) {
            LocalDate date = LocalDate.now();
            HashMap<String, Optional<Department>> dataOut = new HashMap<String, Optional<Department>>();
            dataOut.put("data", dep);
            return dataOut;
        }
        return null;
    }

    /**
     * {@code GET  /DepByDBID/{name}} : get Department By id.<br><br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to get Department By id.
     * @param request request param.
     * @param name name param.
     * @return dataOut.
     * @throws IOException IOException is thrown.
     */
  //@PreAuthorize("hasAuthority('SUPLOCALADMIN') or hasAuthority('DepReader') or hasAuthority('DepWriter')")
  //  @PreAuthorize("hasAuthority('MMAdmin') or hasAuthority('DepReader') or hasAuthority('DepWriter')")
    @GetMapping({"/DepById/{name}"})
    public HashMap<String, Optional<Department>> DepById(HttpServletRequest request, @PathVariable String name)
            throws IOException {
        Optional<Department> dep = this.Repository.findByName(name);
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
        if ((dep.get().getReader() != null && !dep.get().getReader().isEmpty())
                || (dep.get().getAuthor() != null && !dep.get().getAuthor().isEmpty())) {
            prv = dep.get().getReader().split(",");
            prv2 = dep.get().getAuthor().split(",");
            for (String p : prv) {
                l2.add(p);
            }
            for (String p : prv2) {
                l2.add(p);
            }
        }



        if((dep.get().getDept_superieur()==null) && (!dep.get().getName().equals(this.Repository.findAll().get(0).getName())))
        {
            if((dep.get().getDistinguishedname()!=null) )
                if(dep.get().getDistinguishedname().indexOf("OU=")!=-1)
                {
                    String chaine=dep.get().getDistinguishedname().substring
                            (dep.get().getDistinguishedname().indexOf("OU=") + 3);

                    String app= chaine.substring(0, chaine.indexOf(","));
                    dep.get().setDept_superieur(app);
                }
        }
        Department depMAJ=  this.Repository.save(dep.get());

        if (this.global.valid(l2, l)) {
            LocalDate date = LocalDate.now();
            HashMap<String, Optional<Department>> dataOut = new HashMap<String, Optional<Department>>();
            dataOut.put("data", Optional.of(depMAJ));
            return dataOut;
        }
        return null;
    }

  //@PreAuthorize("hasAuthority('SUPLOCALADMIN') or hasAuthority('DepReader') or hasAuthority('DepWriter')")
 // @PreAuthorize("hasAuthority('MMAdmin') or hasAuthority('DepReader') or hasAuthority('DepWriter')")
    @GetMapping({"/DepByName/{name}"})
    public Department DepByName(HttpServletRequest request, @PathVariable String name)
            throws IOException {
        Optional<Department> dep = this.Repository.findByName(name);
        if(dep.isPresent())
            return dep.get();
        else
        return null;
    }
    /**
     * {@code GET  /getManagerOfDep/{name}} : get ManagerOf.<br><br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to get ManagerOf.
     * @param request request param.
     * @param name name param.
     * @return dataOut.
     */
//   @PreAuthorize("hasAuthority('SUPLOCALADMIN') or hasAuthority('DepReader') or hasAuthority('DepWriter') ")
    //  @PreAuthorize("hasAuthority('MMAdmin') or hasAuthority('DepReader') or hasAuthority('DepWriter') ")
    @GetMapping({"/getManagerOfDep/{name}"})
    public String getManagerOf(HttpServletRequest request, @PathVariable String name) {
        String token = request.getHeader("Authorization");
        String body = this.global.DecodeJWT(token.substring(token.indexOf("ey")));
        String[] list = body.split("\"authority\":\"");
        List<String> l = new ArrayList<String>();
        for (int i = 1; i < list.length; ++i) {
            l.add(list[i].substring(0, body.split("\"authority\":\"")[i].length() - 4));
        }
        Optional<Department> dep = this.Repository.findByName(name);
        String[] prv = null;
        String[] prv2 = null;
        List<String> l2 = new ArrayList<String>();
        if ((dep.get().getReader() != null && !dep.get().getReader().isEmpty())
                || (dep.get().getAuthor() != null && !dep.get().getAuthor().isEmpty())) {
            prv = dep.get().getReader().split(",");
            prv2 = dep.get().getAuthor().split(",");
            for (String p : prv) {
                l2.add(p);
            }
            for (String p : prv2) {
                l2.add(p);
            }
        }
        if (this.global.valid(l2, l)) {
            String distinguishedname = dep.get().getDistinguishedname();
            String[] result = distinguishedname.split(",OU=");
            return result[1];
        }
        return null;
    }

    /**
     * {@code POST  /Department} : add Departement.<br><br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to add Departement.
     * @param request request param.
     * @param department department param.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     * @throws IOException IOException is thrown.
     */
// @PreAuthorize("hasAuthority('SUPLOCALADMIN') or hasAuthority('DepWriter') ")
    //  @PreAuthorize("hasAuthority('MMAdmin') or hasAuthority('DepWriter') ")
    @PostMapping({"/Department"})
    public ResponseEntity<HashMap<String, Department>> addDepartement(HttpServletRequest request,
                                                                      @Valid @RequestBody Department department) throws IOException {
        String token = request.getHeader("Authorization");
        String body = this.global.DecodeJWT(token.substring(token.indexOf("ey")));
        String[] list = body.split("\"authority\":\"");
        String username = body.substring(body.indexOf("\":\"") + 3, body.indexOf("\","));
        List<String> l = new ArrayList<String>();
        for (int i = 1; i < list.length; ++i) {
            l.add(list[i].substring(0, body.split("\"authority\":\"")[i].length() - 4));
        }
        String name = department.getName();
//        this.dep = this.Repository.findAll();
//        int j;
//        for (j = 0; j < this.dep.size() && !this.dep.get(j).getName().equals(name); ++j) {
//        }
//        if (j < this.dep.size()) {
//            return updateDep(request, name, department);
//        }
        String A = department.getAuthor();
        String R = department.getReader();
        if (A == null) {
            A = "DepWriter,SUPLOCALADMIN";
        } else {
            String[] aa = A.split(",");
            Boolean z = false;
            Boolean e = false;
            for (String a : aa) {
                if (a.equals("DepWriter")) {
                    z = true;
                }
                if (a.equals("SUPLOCALADMIN")) {
                    e = true;
                }
            }
            if (!z) {
                A += ",DepWriter";
            }
            if (!e) {
                A += ",SUPLOCALADMIN";
            }
        }
        if (R == null) {
            R = "DepReader";
        } else {
            String[] aa = R.split(",");
            Boolean z = false;
            for (String a2 : aa) {
                if (a2.equals("DepReader")) {
                    z = true;
                }
            }
            if (!z) {
                R += ",DepReader";
            }
        }

        List<ConfigLdap> Ldaps = ldapConfigRepository.findAll();
//            int i = 0;
//            Boolean test = false;
//            while (i < Ldaps.size() && (!test))
//            {
        ConfigLdap c = Ldaps.get(0);
        if  (department.getDistinguishedname().contains(c.getBase())){

            //  test = true;
            department.setConfigLdap(c);
        } else {
            department.setConfigLdap(null);
        }

        System.out.println("old::"+department.getAssistante()+"##"+ department.getManagedby()+"##"+department.getInterim());



      if(department.getManagedby()!=null) {
          User manager = userRepository.findByDisplayname(department.getManagedby());
          department.setManagedby(manager.getDistinguishedname());
      }
      if(department.getAssistante()!=null) {
          department.setAssistante(department.getAssistante());
      }
      if(department.getInterim()!=null)
      {
          department.setInterim(department.getInterim());
      }

        LocalDate date = LocalDate.now();
        department.setCreatedby(username);
        department.setAuthor(A);
        department.setReader(R);
        department.setDatecreate(date);
        department.setDateupdate(date);
        String deptSup=department.getDistinguishedname().substring(department.getDistinguishedname().indexOf("OU")+3);
       // department.setDept_superieur(deptSup.substring(0, deptSup.indexOf(",")));
       department.setDept_superieur(department.getDept_superieur());
        HashMap<String, Department> result = this.JsonFormat(this.Repository.save(department));

     //   System.out.println("dept saved/altered::"+result.get("data"));  // String res = this.newcreate(department);
        // esRepository.save(department);

        return ResponseEntity.ok(result);
    }



    @GetMapping("/departmentsHierarchy")
    public List<Dept_hierarchy> getDepartmentsHierarchy()
            {

        return this.departmentService.DepartementHierarchy();

    }






    /**
     * {@code PUT  /Department/{name}} : update Departement.<br><br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to update Departement.
     * @param request request param.
     * @param department department param.
     * @param name name param.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     * @throws IOException IOException is thrown.
     */
 // @PreAuthorize("hasAuthority('SUPLOCALADMIN') or hasAuthority('DepWriter') ")
  // @PreAuthorize("hasAuthority('MMAdmin') or hasAuthority('DepWriter') ")
    @PutMapping({"/Department/{name}"})
    public ResponseEntity<HashMap<String, Department>> updateDep(HttpServletRequest request, @PathVariable String name,
                                                                 @Valid @RequestBody Department department) throws IOException {
        String token = request.getHeader("Authorization");
        String body = this.global.DecodeJWT(token.substring(token.indexOf("ey")));
        String[] list = body.split("\"authority\":\"");
        String username = body.substring(body.indexOf("\":\"") + 3, body.indexOf("\","));
        Department dep = this.Repository.findByName(name).get();

        List<String> l = new ArrayList<String>();
        for (int i = 1; i < list.length; ++i) {
            l.add(list[i].substring(0, body.split("\"authority\":\"")[i].length() - 4));
        }
        String[] prv = null;
        List<String> l2 = new ArrayList<String>();
        if (dep.getAuthor() != null && !dep.getAuthor().isEmpty()) {
            prv = dep.getAuthor().split(",");
        }
        if (prv != null) {
            for (String p : prv) {
                l2.add(p);
            }
        }
        if (this.global.valid(l2, l)) {
            String A = department.getAuthor();
            String R = department.getReader();
            if (A == null) {
                A = "DepWriter,SUPLOCALADMIN";
            } else {
                String[] aa = A.split(",");
                Boolean z = false;
                Boolean e = false;
                for (String a : aa) {
                    if (a.equals("DepWriter")) {
                        z = true;
                    }
                    if (a.equals("SUPLOCALADMIN")) {
                        e = true;
                    }
                }
                if (!z) {
                    A += ",DepWriter";
                }
                if (!e) {
                    A += ",SUPLOCALADMIN";
                }
            }
            if (R == null) {
                R = "DepReader";
            } else {
                String[] aa = R.split(",");
                Boolean z = false;
                for (String a2 : aa) {
                    if (a2.equals("DepReader")) {
                        z = true;
                    }
                }
                if (!z) {
                    R += ",DepReader";
                }
            }
            LocalDate date = LocalDate.now();
            List<ConfigLdap> Ldaps = ldapConfigRepository.findAll();
            int i = 0;
            Boolean test = false;
            while (i < Ldaps.size() && !test) {
                ConfigLdap c = Ldaps.get(i);
                if ((department.getDistinguishedname() != null) && (department.getDistinguishedname().contains(c.getBase()))) {
                    System.out.println(department.getName() + "    " + c.getName());
                    test = true;
                    dep.setConfigLdap(c);
                } else {
                    dep.setConfigLdap(null);
                }
                i++;
            }
            dep.setModifiedby(username);
            dep.setComment(department.getComment());
            dep.setDatecreate(department.getDatecreate());
            dep.setDateupdate(date);
            dep.setDescription(department.getDescription());
            dep.setDistinguishedname(department.getDistinguishedname());
            dep.setManagedby(department.getManagedby());
            dep.setTest(department.isTest());
            dep.setStatus(department.isStatus());
            dep.setAuthor(A);
            dep.setReader(R);
            HashMap<String, Department> result = this.JsonFormat(this.Repository.save(dep));
            // String res = this.update(std.get().getId(), dep);
            // esRepository.save(dep);
            return ResponseEntity.ok(result);

        }
        return null;
    }

    /**
     * {@code DELETE  /Department/{name}} : delete Departement By name.<br><br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to delete Departement By name.
     * @param request request param.
     * @param name name param.
     * @return msg.
     * @throws UnknownHostException UnknownHostException is thrown.
     * @throws MalformedURLException MalformedURLException is thrown.
     */
 // @PreAuthorize("hasAuthority('SUPLOCALADMIN') or hasAuthority('DepWriter') ")
  //  @PreAuthorize("hasAuthority('MMAdmin') or hasAuthority('DepWriter') ")
    @DeleteMapping({"/Department/{name}"})
    public String deleteDep(HttpServletRequest request, @PathVariable String name)
            throws UnknownHostException, MalformedURLException {
        String token = request.getHeader("Authorization");
        String body = this.global.DecodeJWT(token.substring(token.indexOf("ey")));
        String[] list = body.split("\"authority\":\"");
        String username = body.substring(body.indexOf("\":\"") + 3, body.indexOf("\","));
        List<String> l = new ArrayList<String>();
        for (int i = 1; i < list.length; ++i) {
            l.add(list[i].substring(0, body.split("\"authority\":\"")[i].length() - 4));
        }
        Optional<Department> std = this.Repository.findByName(name);
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
            // String res = this.delete(std.get().getId());
            // esRepository.delete(std.get());
            this.Repository.delete(std.get());
            return "Document Deleted";
        }
        return null;
    }

    /**
     * {@code GET  /getDepActive} : get Departement active.<br><br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to get Departement active.
     * @param status status param.
     * @return msg.
     */
    @GetMapping({"/getDepActive"})
    public int getDepActive(@RequestParam Boolean status) {
        return this.Repository.findAllByStatus(status).size();
    }

    /**
     * {@code GET  /getDepTest} : get Departement active.<br><br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to get Departement active.
     * @param test test param.
     * @return size.
     */
    @GetMapping({"/getDepTest"})
    public int getDepTest(@RequestParam Boolean test) {
        return this.Repository.findAllByTest(test).size();
    }

    /**
     * {@code GET  /getDepTotal} : get Departement total.<br><br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to get Departement total.
     * @return size.
     */
    @GetMapping({"/getDepTotal"})
    public int getDepTotal() {
        return this.Repository.findAll().size();
    }

    @GetMapping({"/getDeptNames"})
    public List<String> getDepNames() {
        List<String> listenames= new ArrayList<>();
        List<Department> depep=Repository.findAll();
        for(int i=0; i<depep.size(); i++)
        {
            if(listenames.indexOf(depep.get(i).getName())==-1)
            listenames.add(depep.get(i).getName());
        }
        return listenames;
    }
}
