package com.example.gestionAchat.service.pm;

import com.example.gestionAchat.config.Device;
import com.example.gestionAchat.entities.Department;
import com.example.gestionAchat.entities.Dept_hierarchy;
import com.example.gestionAchat.repository.pm.DepRepository;
import com.example.gestionAchat.repository.pm.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Service
    @Transactional
    public class DepartmentService {
        @Autowired
        RoleRepository roleRepository;
        @Autowired
        ProfileService profileService;
        @Autowired
         private DepRepository Repository;



        @PersistenceContext(unitName = "externDSEmFactory")
        @Autowired
        private EntityManager em;

    public List<Dept_hierarchy> DepartementHierarchy() {

        Query q = this.em.createNativeQuery("SELECT * FROM dept_hierarchy", Dept_hierarchy.class);

        List<Dept_hierarchy> liste= (List<Dept_hierarchy>) q.getResultList();

        return  liste;
    }

    public HashMap<String, List<Department>> allDepartments(HttpServletRequest request, Device global) {

        List<Department> depList = this.Repository.findAll();
        List<Department> depp = new ArrayList<Department>();
        String token = request.getHeader("Authorization");
        String body = global.DecodeJWT(token.substring(token.indexOf("ey")));
        String[] list = body.split("\"authority\":\"");
        List<String> l = new ArrayList<String>();
        for (int i = 1; i < list.length; ++i) {
            l.add(list[i].substring(0, body.split("\"authority\":\"")[i].length() - 4));
        }
        for (Department dep : depList) {
            String[] prv = null;
            String[] prv2 = null;
            List<String> l2 = new ArrayList<String>();
            if ((dep.getReader() != null && !dep.getReader().isEmpty())
                    || (dep.getReader() != null && !dep.getReader().isEmpty())) {
                prv = dep.getReader().split(",");
                prv2 = dep.getAuthor().split(",");
                for (String p : prv) {
                    l2.add(p);
                }
                for (String p : prv2) {
                    l2.add(p);
                }
            }
            if (global.valid(l2, l)) {
                depp.add(dep);
            }
        }
        HashMap<String, List<Department>> dataOut = new HashMap<String, List<Department>>();
        dataOut.put("data", depList);
        return dataOut;


    }




}
