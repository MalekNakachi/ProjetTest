package com.example.gestionAchat.web.rest;
import com.example.gestionAchat.entities.Department;
import com.example.gestionAchat.repository.pm.DepRepository;
import com.example.gestionAchat.repository.pm.UserRepository;
import com.example.gestionAchat.service.DocumentService;
import com.example.gestionAchat.service.MyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.*;

import static com.example.gestionAchat.web.rest.AuthentificationResource.varGA;
import static com.example.gestionAchat.web.rest.AuthentificationResource.tokenRoot;
//import static com.example.MaiManager.web.rest.AuthentificationResource.tokenAuth;

@PreAuthorize("isAuthenticated()")
@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class UserRessource {
    ArrayList <String> usersApplicationList= new ArrayList<String>();
    ArrayList <String> usersApplicationListfinal= new ArrayList<String>();


    @Autowired
    private UserRepository userRepository;

    @Value("${spring.ProfileUsername}")
    private String AdminProfileUsername;

    @Value("${spring.ProfilePassword}")
    private String AdminProfilePassword;

    @Value("${spring.AdminProfileApplication}")
    private String AdminProfileApplication;


    @Value("${spring.ProfileManagerURL}")
    private String ProfileManagerURL;

    @Autowired
    private  com.example.gestionAchat.repository.pm.DepRepository deptRepository;

    @Autowired
    MyService myService;
    @Autowired DocumentService documentService;

    //  public static ArrayList<String> varGA = new ArrayList<String>();
    @RequestMapping(value = "/UsersMM", method = RequestMethod.GET)

    public Map<String, List> getListUser() throws org.json.JSONException {



//      MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

//      body.add("username", AdminProfileUsername);

//      body.add("password", AdminProfilePassword);

//      body.add("application", AdminProfileApplication);

//      HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, new HttpHeaders());

//      String url = ProfileManagerURL + "login";

        RestTemplate restTemplate = new RestTemplate();

//        ResponseEntity<String> result1 = restTemplate.postForEntity(url, requestEntity, String.class);

//        String token = result1.getHeaders().getFirst("authorization");
        String url3 = ProfileManagerURL + "/api/Users";
        HttpHeaders headers = new HttpHeaders();

        headers.set("Authorization", tokenRoot);

        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        headers.set("Authorization", tokenRoot);

        HttpEntity<?> entity3 = new HttpEntity<>(headers);

        ResponseEntity<String> c3 = restTemplate.exchange(url3, HttpMethod.GET, entity3, String.class);

        ObjectMapper mapper3 = new ObjectMapper();

        String cc = c3.getBody();


        Map<String, List> map2 = new HashMap<String, List>();
        try {
            map2 = mapper3.readValue(cc, Map.class);
           // System.out.println("list user" + map2);
        } catch (Exception e) {
        }
        return map2;
    }


    @RequestMapping(value = "/getMyAllList", method = RequestMethod.GET)
    JSONObject   getMyAllList(@RequestParam("samaccountname") String samaccountname) {
         return documentService.getMy(samaccountname);

    }

  @RequestMapping(value = "/utilisateursByRole", method = RequestMethod.GET)
  List<String> utilisateursByRole(@RequestParam("role") String role) {
    return documentService.getUsersByRole(role);

  }

  @RequestMapping(value = "/getAlldepartements", method = RequestMethod.GET)
  public List<String> getAlldepartements() {
    List<String> departements = new ArrayList<>();
    List<com.example.gestionAchat.entities.Department> departementsTemp =  deptRepository.findAll();
    for(com.example.gestionAchat.entities.Department departementTemp : departementsTemp){
      departements.add(departementTemp.getName());
    }
    return departements;

  }

    //"************************************************** Only Application Users **************************************************
    @RequestMapping(value = "/User-accessesMM", method = RequestMethod.GET)

    public  ArrayList <String> getListAppUser() throws org.json.JSONException {
        usersApplicationListfinal=new ArrayList<>();
        // List<List<String>> AllUser= new ArrayList<List<String>>();

//        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
//
//        body.add("username", AdminProfileUsername);
//
//        body.add("password", AdminProfilePassword);
//
//        body.add("application", AdminProfileApplication);
//
//        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, new HttpHeaders());
//i
//        String url = ProfileManagerURL + "login";

//
         RestTemplate restTemplate = new RestTemplate();
//
//        ResponseEntity<String> result1 = restTemplate.postForEntity(url, requestEntity, String.class);

//        String token = result1.getHeaders().getFirst("authorization");

        HttpHeaders headers = new HttpHeaders();

        //headers.set("Authorization", token);

//        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

//        headers.set("Authorization", token);

        String url03 = ProfileManagerURL + "/api/user-Accesses";

        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

     	headers.set("Authorization", tokenRoot);

        HttpEntity<?> entity03 = new HttpEntity<>(headers);

        ResponseEntity<ArrayList> c03 = restTemplate.exchange(url03, HttpMethod.GET, entity03, ArrayList.class);
        String chaineIntermid="";
        String ligne="";

        System.out.println("*****************************************");
        System.out.println(c03.getBody());
        System.out.println("*****************************************");

        for (int s = 0; s < c03.getBody().size(); s++) {
          // if  (c03.getBody().get(s).toString().indexOf("mm8")!=-1) {
                 ligne= c03.getBody().get(s).toString();

                chaineIntermid = c03.getBody().get(s).toString().substring(c03.getBody().get(s).toString().indexOf("samaccountname=") +15);
                String samaccountname = chaineIntermid.substring(0, chaineIntermid.indexOf(", mail"));

                if(usersApplicationList.indexOf(samaccountname)==-1)
                usersApplicationList.add(samaccountname);
     //  }
        }
        String displayname="";
        for(int i=0; i<usersApplicationList.size();i++) {
            displayname = userRepository.findBySamaccountname(usersApplicationList.get(i)).get().getDisplayname();
            usersApplicationListfinal.add(displayname);
        }


        return usersApplicationListfinal;

    }



    @RequestMapping(value = "/DepartementNameChef", method = RequestMethod.GET)

    public List DepartementNameChef() throws org.json.JSONException {

        List<String> Retour = new ArrayList<>();
        List<Department> depList = this.deptRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
        String s = "";
        for (int i = 0; i < depList.size(); i++) {

            if (depList.get(i).getManagedby() == null)
                s = (depList.get(i).getName());
            else {
                String manager = depList.get(i).getManagedby();


                if(manager.indexOf("OU=")==-1) {
                    if(!depList.get(i).getName().equals(depList.get(i).getManagedby()))
                      s = depList.get(i).getName() + " (" + depList.get(i).getManagedby() + ")";
                    else
                      s = depList.get(i).getName();

                }
                else {

                    if(!depList.get(i).getName().equals(manager.substring((manager.indexOf("CN=") + 3), manager.indexOf("OU") - 1)))
                    s = depList.get(i).getName() + " (" + manager.substring((manager.indexOf("CN=") + 3), manager.indexOf("OU") - 1) + ")";
                    else
                    s = depList.get(i).getName();
                }

            }

            if (Retour.indexOf(s) == -1)
                Retour.add(s);

        }

        return Retour;
    }

  //  ByOrderBySeatNumberAsc()

    @RequestMapping(value = "/departement", method = RequestMethod.GET)

    public Map<String, List> getListdepartement() throws org.json.JSONException {

       RestTemplate restTemplate = new RestTemplate();

        String url4 = ProfileManagerURL + "/api/Department";

        HttpHeaders headers = new HttpHeaders();

        headers.set("Authorization", tokenRoot);

        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<?> entity3 = new HttpEntity<>(headers);

        ResponseEntity<String> c3 = restTemplate.exchange(url4, HttpMethod.GET, entity3, String.class);

        ObjectMapper mapper3 = new ObjectMapper();

        String cc = c3.getBody();


        Map<String, List> map2 = new HashMap<String, List>();
        try {

            map2 = mapper3.readValue(cc, Map.class);
            System.out.println("list user" + map2);
        } catch (Exception e) {
        }
        return map2;


    }


    @RequestMapping(value = "/role", method = RequestMethod.GET)

    public Map<String, List> getListRole() throws org.json.JSONException {



        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

//       body.add("username", AdminProfileUsername);

//       body.add("password", AdminProfilePassword);

//       body.add("application", AdminProfileApplication);

//       HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, new HttpHeaders());

//       String url = ProfileManagerURL + "login";

//       RestTemplate restTemplate = new RestTemplate();

//       ResponseEntity<String> result1 = restTemplate.postForEntity(url, requestEntity, String.class);

//       String token = result1.getHeaders().getFirst("authorization");


        RestTemplate restTemplate2 = new RestTemplate();

        String url2 = ProfileManagerURL + "api/Role";

        HttpHeaders headers = new HttpHeaders();

        headers.set("Authorization", tokenRoot);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<String> c = restTemplate2.exchange(url2, HttpMethod.GET, entity, String.class);

        ObjectMapper mapper = new ObjectMapper();

        Map<String, List> map = new HashMap<String, List>();

        try {

            map = mapper.readValue(c.getBody(), Map.class);
        } catch (Exception e) {
        }

        return map;

    }

    @RequestMapping(value = "/getMyMM", method = RequestMethod.GET)

    public Map<String, List> getMy(String token) {
        RestTemplate restTemplate2 = new RestTemplate();

        String url2 = ProfileManagerURL + "api/getMy";

        HttpHeaders headers = new HttpHeaders();

        headers.set("Authorization", token);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<String> c = restTemplate2.exchange(url2, HttpMethod.GET, entity, String.class);


        ObjectMapper mapper = new ObjectMapper();

        Map<String, List> map = new HashMap<String, List>();

        try {

            map = mapper.readValue(c.getBody(), Map.class);
        } catch (Exception e) {
        }

        return map;
    }


    @RequestMapping(value = "/nameByLogin", method = RequestMethod.GET)

    public String ssacount(@RequestParam String login) {

        int index = varGA.indexOf(login);


        return varGA.get(index + 1);
    }

    @RequestMapping(value = "/loginByName", method = RequestMethod.GET)

    public String loginByname(@RequestParam String ssacount) {

        int index = varGA.indexOf(ssacount);


        return varGA.get(index - 1);
    }


    @RequestMapping(value = "/getEffectMyUserNameListMM", method = RequestMethod.GET)
    public Map<String, List> getRoleByToken(@RequestParam String token) {

        RestTemplate restTemplate2 = new RestTemplate();





       //tring displayname= myService.findBySamaccountnamMM(MyListAutorities.get(MyListAutorities.size()-1)).getDisplayname();

        //System.out.println("received token=="+token);

        String url2 = ProfileManagerURL + "api/getEffectMyUserNameList/mm8";

        HttpHeaders headers = new HttpHeaders();

        headers.set("Authorization", token);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<String> c = restTemplate2.exchange(url2, HttpMethod.GET, entity, String.class);


        ObjectMapper mapper = new ObjectMapper();

        Map<String, List> map = new HashMap<String, List>();

        try {

            map = mapper.readValue(c.getBody(), Map.class);
        } catch (Exception e) {
        }

        return map;


    }






}

