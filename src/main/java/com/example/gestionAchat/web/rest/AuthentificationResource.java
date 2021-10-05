package com.example.gestionAchat.web.rest;

import com.example.gestionAchat.domain.AclSid;
import com.example.gestionAchat.domain.Auth;
import com.example.gestionAchat.domain.UserIdentifiant;
import com.example.gestionAchat.repository.AclClassRepository;
import com.example.gestionAchat.repository.AclEntryRepository;
import com.example.gestionAchat.repository.AclSidRepository;
import com.example.gestionAchat.repository.UserIdentifiantRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * REST controller for managing ACL.
 */
@RestController
@RequestMapping("/api")
public class AuthentificationResource {


    @Autowired
    UserIdentifiantRepository userIdentifiantRepository;
    @Autowired
    private UserDetailsService userDetailsService;
    public static String tokenAuth = "";

    @Value("${spring.ProfileManagerURL}")
    private  String ProfileManagerURL;

    @Autowired
    AclEntryRepository aclEntryRepository;


    @Autowired
    AclSidRepository aclSidRepository;

    @Autowired
    AclClassRepository aclClassRepository;
    public void SaveSid(AclSid aclSid) {

        aclSidRepository.save(aclSid);
    }
    public static ArrayList<String> DepDistinguishednames= new ArrayList<String>();
    //////////////////////////////////////////////////////////////////////////
    public static List ListeDepartements;


    @Value("${userlocal.role}")
    private String localrole;
    @Value("${userlocal.name}")
    private String localusername;
    @Value("${userlocal.password}")
    private String localpassword;

    public static String tokenRoot = "";
    public static ArrayList<String> varGA = new ArrayList<String>();
    public static List usersList;




    public  List<List<String>>eexcecute () throws org.json.JSONException {

        List<List<String>> Allsids= new ArrayList<List<String>>();

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();


        body.add("username", "root");

        body.add("password", "PicoSoft2019");

        body.add("application", "PM");
//
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, new HttpHeaders());

        String url = ProfileManagerURL+"login";
        //	System.out.println("url>>"+ url);
        //	System.out.println("body>>"+requestEntity.getBody());
        RestTemplate restTemplate = new RestTemplate();
//
        ResponseEntity<String> result1 = restTemplate.postForEntity(url, requestEntity, String.class);

        String token = result1.getHeaders().getFirst("authorization");


        System.out.println("************************************************** roles  **************************************************");

        RestTemplate restTemplate2 = new RestTemplate();
//
//        String url2 = ProfileManagerURL + "api/Role";
//
        HttpHeaders headers = new HttpHeaders();
//
//        headers.set("Authorization", token);
//
//        HttpEntity<?> entity = new HttpEntity<>(headers);
//
//        ResponseEntity<String> c = restTemplate2.exchange(url2, HttpMethod.GET, entity, String.class);
//
//        ObjectMapper mapper = new ObjectMapper();
//
//        Map<String, List> map = new HashMap<String, List>();
//
//        try {
//
//            map = mapper.readValue(c.getBody(), Map.class);
//        } catch (Exception e) {
//        }
//
//
//        ArrayList<String> roles = new ArrayList<String>();
//
//        String intermediaire = "";
//
//        for (int i = 0; i < map.get("data").size(); i++) {
//
//            if (map.get("data").get(i).toString().indexOf("alias=mm8") != -1)
//
//            {
//                int index = map.get("data").get(i).toString().indexOf("rolename=");
//
//                intermediaire = map.get("data").get(i).toString().substring(index);
//
//
//                String verifnotnull= intermediaire.substring(9, intermediaire.indexOf(","));
//                if((verifnotnull.indexOf("null")==-1))
//                    roles.add(verifnotnull);
//            }
//        }

        System.out.println("************************************************** users  **************************************************");

        String url3 = ProfileManagerURL + "/api/Users";


        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        headers.set("Authorization", token);

        HttpEntity<?> entity3 = new HttpEntity<>(headers);

        ResponseEntity<String> c3 = restTemplate2.exchange(url3, HttpMethod.GET, entity3, String.class);

        ObjectMapper mapper3 = new ObjectMapper();

        String cc = c3.getBody();

        ArrayList<String> users = new ArrayList<String>();
        ArrayList<String> samaccountnames = new ArrayList<String>();

        String intermediaireofUsers, intermediaireosamafUsers = "";

        Map<String, List> map2 = new HashMap<String, List>();
        try {

            map2 = mapper3.readValue(cc, Map.class);
        } catch (Exception e) {
        }


        usersList= map2.get("data");
        //System.out.println(usersList);

        for (int i = 0; i < map2.get("data").size(); i++) {


            int index = map2.get("data").get(i).toString().indexOf("displayname=");
            int indexsama = map2.get("data").get(i).toString().indexOf("samaccountname=");


            intermediaireosamafUsers = map2.get("data").get(i).toString().substring(indexsama);

            intermediaireofUsers = map2.get("data").get(i).toString().substring(index);

            samaccountnames.add(intermediaireosamafUsers.substring(15, intermediaireosamafUsers.indexOf(",")));
            String verifnotnull=intermediaireofUsers.substring(12, intermediaireofUsers.indexOf(","));
            if((verifnotnull.indexOf("null")==-1))
                users.add(verifnotnull);




            if(intermediaireosamafUsers.substring(15, intermediaireosamafUsers.indexOf(","))!=null)
                varGA.add((intermediaireosamafUsers.substring(15, intermediaireosamafUsers.indexOf(","))).toLowerCase());
            if(intermediaireofUsers.substring(12, intermediaireofUsers.indexOf(","))!=null)
                varGA.add(intermediaireofUsers.substring(12, intermediaireofUsers.indexOf(",")));


        }
//		System.out.println("varGA  ");
//		for (int j = 0; j < varGA.size(); j++) {
//			System.out.println(varGA.get(j));
//		}

      /*  System.out.println("************************************************** all departements  **************************************************");

        String url4 = ProfileManagerURL + "/api/Department";

        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        headers.set("Authorization", token);

        HttpEntity<?> entity4 = new HttpEntity<>(headers);

        ResponseEntity<String> c4 = restTemplate2.exchange(url4, HttpMethod.GET, entity4, String.class);

        ObjectMapper mapper4 = new ObjectMapper();

        String ccdep = c4.getBody();

        ArrayList<String> departements = new ArrayList<String>();

        String intermediaireofdepartements = "";

        Map<String, List> map4 = new HashMap<String, List>();
        try {

            map4 = mapper4.readValue(ccdep, Map.class);
        } catch (Exception e) {
        }


        for (int i = 0; i < map4.get("data").size(); i++) {



            int index = map4.get("data").get(i).toString().indexOf("name=");
            intermediaireofdepartements = map4.get("data").get(i).toString().substring(index);

            String verifnotnull=intermediaireofdepartements.substring(5, intermediaireofdepartements.indexOf(","));
            if((verifnotnull.indexOf("null")==-1))
                departements.add(verifnotnull);


            int index2 = map4.get("data").get(i).toString().indexOf("distinguishedname=");
            intermediaireofdepartements = map4.get("data").get(i).toString().substring(index2);
            verifnotnull=intermediaireofdepartements.substring(18, intermediaireofdepartements.indexOf(", description"));

            if((verifnotnull.indexOf("null")==-1))
                DepDistinguishednames.add(verifnotnull);
        }
        ListeDepartements=map4.get("data");



        Allsids.add(roles);*/

        System.out.println("*************************users");
//        for(int f=0;f<users.size(); f++)
//            System.out.println(users.get(f));
        Allsids.add(users);
      //  Allsids.add(departements);





        return  Allsids;


    }

    public  void eexcecuteView (String samaccount) throws org.json.JSONException {
        List<List<String>> Allsids= new ArrayList<List<String>>();
        ArrayList<String> users = new ArrayList<String>();
       // List<UserIdentifiant>  userIdentifiants= (List<UserIdentifiant>) userIdentifiantRepository.findAll();
        UserIdentifiant  userIdentifiant= (UserIdentifiant) userIdentifiantRepository.findBySamaccountname(samaccount);

       // for(int k=0; k< userIdentifiants.size();k++)
     //   {
            varGA.add(userIdentifiant.getSamaccountname().toLowerCase());
            varGA.add(userIdentifiant.getDisplayname());
           // users.add(userIdentifiants.get(k).getDisplayname());
   //     }




      // Allsids.add(users);






      //  return  Allsids;


    }

    @RequestMapping(value = "/ExecuteACL", method = RequestMethod.POST)
    public void execute() {

        List<List<String>> allsids = eexcecute();

//				if(aclServices.AllSid().size()==0)
//				{
        for (int j = 0; j < allsids.get(0).size(); j++) {

            try {

                AclSid aclSid = new AclSid(false, allsids.get(0).get(j));
                SaveSid(aclSid);
            } catch (Exception e) {

            }

        }

      for (int j = 2; j < allsids.get(1).size(); j++) {

            try {
                AclSid aclSid = new AclSid(false, allsids.get(1).get(j));
                SaveSid(aclSid);
            } catch (Exception e) {

            }
        }


        for (int j = 0; j < allsids.get(2).size(); j++) {
            try {
                AclSid aclSid = new AclSid(false, allsids.get(2).get(j));
                SaveSid(aclSid);
            } catch (Exception e) {

            }
        }


    }


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String authentifier(@RequestBody Auth auth) {


        String decodedString = auth.getPassword();
      //  System.out.println(decodedString);
//        List<List<String>> allsids = eexcecute();
       // List<List<String>> allsids = eexcecuteView(auth.getUsername());
        if(!auth.getUsername().equals(localusername))
             eexcecuteView(auth.getUsername());

//				if(aclServices.AllSid().size()==0)
//				{
  /*      for (int j = 0; j < allsids.get(0).size(); j++)
        {

            try {

                AclSid aclSid = new AclSid(true, allsids.get(0).get(j));
                SaveSid(aclSid);
            }catch (Exception e){

            }

        }

   */
//
//        for (int j = 2; j < allsids.get(0).size(); j++) {
//
//            //   if(allsids.get(1).get(j).indexOf(aclSid)==-1)
//            {
//
//
//                try {
//                    AclSid aclSid = new AclSid(false, allsids.get(1).get(j));
//                    SaveSid(aclSid);
//                } catch (Exception e) {
//
//                }
//            }
//        }
/*

        for (int j = 0; j < allsids.get(2).size(); j++)
        {
            try{
                AclSid aclSid = new AclSid(false, allsids.get(2).get(j));
                SaveSid(aclSid);
            }
            catch (Exception e){

            }
        }
*/


        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

        body.add("username", auth.getUsername());

        if(!auth.getUsername().equals(localusername))
            body.add("password", decodedString);
        else
            body.add("password", auth.getPassword());
        body.add("application", auth.getApplication());

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, new HttpHeaders());

        String url = ProfileManagerURL+"login";


        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> result1 = restTemplate.postForEntity(url, requestEntity, String.class);

        String token = result1.getHeaders().getFirst("authorization");


        MultiValueMap<String, Object> body2 = new LinkedMultiValueMap<>();




        body2.add("username", localusername);

        body2.add("password", localpassword);

        body2.add("application", "PM");
//
        HttpEntity<MultiValueMap<String, Object>> requestEntity2 = new HttpEntity<>(body2, new HttpHeaders());

        String url2 = ProfileManagerURL + "login";
//

//
        ResponseEntity<String> result2 = restTemplate.postForEntity(url2, requestEntity2, String.class);

         tokenRoot = result2.getHeaders().getFirst("authorization");

        return token;
    }


    /**************************************************************************/

   }
