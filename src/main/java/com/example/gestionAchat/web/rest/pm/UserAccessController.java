
package com.example.gestionAchat.web.rest.pm;

import com.example.gestionAchat.entities.GroupUser;
import com.example.gestionAchat.repository.pm.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
import com.example.gestionAchat.entities.UserAccess;
import com.example.gestionAchat.service.pm.ProfileService;
import com.example.gestionAchat.service.pm.UserAccessService;
import com.example.gestionAchat.service.pm.dto.UserAccessCriteria;
import com.example.gestionAchat.service.pm.query.UserAccessQueryService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.util.*;
import com.example.gestionAchat.entities.Application;

@CrossOrigin(origins = {"*"})
@RequestMapping({"/api"})
@RestController
public class UserAccessController {
    private static final Logger log;

    static {
        log = LoggerFactory.getLogger(UserAccessController.class);
    }

    private final Device global;
    /*	@Autowired
        Config config;
        Client client;*/

    @Autowired
    GroupUserRepository groupUserRepository;

    List<UserAccess> Useraccess;
    @Autowired
    private UserAccessRepository Repository;
    //	@Autowired
//	private UserAccessEsRepository EsRepository;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private UserRepository UserRepository;
    @Autowired
    private AppRepository appRep;
    @Autowired
    private UserAccessService Service;
    @Autowired
    private ProfileService profileService;

    @Autowired
    private UserAccessQueryService userAccessQueryService;

    public UserAccessController() {
        this.global = new Device();

    }

    public HashMap<String, UserAccess> JsonFormat(UserAccess Access) {
        HashMap<String, UserAccess> dataOut = new HashMap<String, UserAccess>();
        dataOut.put("data", Access);
        return dataOut;
    }


    /**
     * {@code GET  /user-accesses} : get all the userAccesses.<br><br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to get all the userAccesses.
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userAccesses in body.
     */
    @GetMapping("/user-accesses")
    public ResponseEntity<List<UserAccess>> getAllUserAccesses(UserAccessCriteria criteria, Pageable pageable) {
        log.debug("REST request to get UserAccesses by criteria: {}", criteria);
        Page<UserAccess> page = userAccessQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }


    /**
     * {@code GET  /user-accesses/count} : count all the userAccesses.<br><br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to count all the userAccesses.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/user-accesses/count")
    public ResponseEntity<Long> countUserAccesses(UserAccessCriteria criteria) {
        log.debug("REST request to count UserAccesses by criteria: {}", criteria);
        return ResponseEntity.ok().body(userAccessQueryService.countByCriteria(criteria));
    }

    @GetMapping("/userAccessesMM")
    public ArrayList <String> getAllAccessesMM() {
        List<UserAccess> list= Repository.findAll();
        ArrayList <String> arrayList= new ArrayList<>();

        for(int k=0; k<list.size(); k++)
            if(arrayList.indexOf(list.get(k).getUser().getDisplayname())==-1)
               arrayList.add(list.get(k).getUser().getDisplayname());



        List<GroupUser> groupUserList=groupUserRepository.findAll();
        for(int k=0; k<groupUserList.size(); k++)
            if(arrayList.indexOf(groupUserList.get(k).getUser().getDisplayname())==-1)
                arrayList.add(groupUserList.get(k).getUser().getDisplayname());


        return arrayList;

    }


    /**
     * {@code GET  /Access} : get all Access.<br><br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to count all the userAccesses.
     * @param request request param.
     * @return dataOut.
     */
 @PreAuthorize("hasAuthority('SUPLOCALADMIN') or hasAuthority('AccessReader')  or hasAuthority('AccessWriter')")
    @GetMapping({"/Access"})
    public HashMap<String, List<UserAccess>> allAccess(HttpServletRequest request) {
        List<UserAccess> privList = this.Repository.findAll();
        List<UserAccess> accees = new ArrayList<UserAccess>();
        String token = request.getHeader("Authorization");
        String body = this.global.DecodeJWT(token.substring(token.indexOf("ey")));
        String[] list = body.split("\"authority\":\"");
        List<String> l = new ArrayList<String>();
        for (int i = 1; i < list.length; ++i) {
            l.add(list[i].substring(0, body.split("\"authority\":\"")[i].length() - 4));
        }
        for (UserAccess r : privList) {
            String[] prv = null;
            String[] prv2 = null;
            List<String> l2 = new ArrayList<String>();
            if ((r.getReader() != null && !r.getReader().isEmpty())
                    || (r.getAuthor() != null && !r.getAuthor().isEmpty())) {
                prv = r.getReader().split(",");
                prv2 = r.getAuthor().split(",");
                for (String p : prv) {
                    l2.add(p);
                }
                for (String p : prv2) {
                    l2.add(p);
                }
            }
            if (this.global.valid(l2, l)) {
                accees.add(r);
            }
        }
        HashMap<String, List<UserAccess>> dataOut = new HashMap<String, List<UserAccess>>();
        dataOut.put("data", accees);
        return dataOut;
    }

    /**
     * {@code GET  /getAccessByUser} : get Access By User.<br><br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to get Access By User.
     * @param samaccountname samaccountname param.
     * @return dataOut.
     */
    @PreAuthorize("hasAuthority('SUPLOCALADMIN') or hasAuthority('AccessReader')  or hasAuthority('AccessWriter')")
    @GetMapping({"/getAccessByUser"})
    public HashMap<String, List<UserAccess>> getAccessByUser(@RequestParam String samaccountname) {
        List<UserAccess> privList = this.Service.getAccessByUser(samaccountname);
        HashMap<String, List<UserAccess>> dataOut = new HashMap<String, List<UserAccess>>();
        dataOut.put("data", privList);
        return dataOut;
    }

    /**
     * {@code GET  /AccessByDBID/{id}} : get Access By id.<br><br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to get Access By id.
     *
     * @param request request param.
     * @param id      id param.
     * @return dataOut.
     * @throws IOException IOException is thrown.
     */
   @PreAuthorize("hasAuthority('SUPLOCALADMIN') or hasAuthority('AccessReader') ")
    @GetMapping({"/AccessByDBID/{id}"})
    public HashMap<String, Optional<UserAccess>> AccessById(HttpServletRequest request, @PathVariable Long id)
            throws IOException {
        Optional<UserAccess> acces = this.Repository.findById(id);
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
        if ((acces.get().getReader() != null && !acces.get().getReader().isEmpty())
                || (acces.get().getAuthor() != null && !acces.get().getAuthor().isEmpty())) {
            prv = acces.get().getReader().split(",");
            prv2 = acces.get().getAuthor().split(",");
            for (String p : prv) {
                l2.add(p);
            }
            for (String p : prv2) {
                l2.add(p);
            }
        }
        if (this.global.valid(l2, l)) {
            HashMap<String, Optional<UserAccess>> dataOut = new HashMap<String, Optional<UserAccess>>();
            dataOut.put("data", acces);
            return dataOut;
        }
        return null;
    }

    /**
     * {@code GET  /AccessById/{profileName},{alias},{samaccountname}} : get Access By id.<br><br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to get Access By id.
     * @param request request param.
     * @param alias alias param.
     * @param samaccountname samaccountname param.
     * @param profileName profileName param.
     * @return dataOut.
     * @throws IOException IOException is thrown.
     */
   @PreAuthorize("hasAuthority('SUPLOCALADMIN') or hasAuthority('AccessReader')  or hasAuthority('AccessWriter')")
    @GetMapping({"/AccessById/{profileName},{alias},{samaccountname}"})
    public HashMap<String, UserAccess> AccessById(HttpServletRequest request, @PathVariable String profileName,
                                                  @PathVariable String alias, @PathVariable String samaccountname) throws IOException {
        UserAccess acces = this.Service.getAccessById1(profileName, alias, samaccountname);
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
        if ((acces.getReader() != null && !acces.getReader().isEmpty())
                || (acces.getAuthor() != null && !acces.getAuthor().isEmpty())) {
            prv = acces.getReader().split(",");
            prv2 = acces.getAuthor().split(",");
            for (String p : prv) {
                l2.add(p);
            }
            for (String p : prv2) {
                l2.add(p);
            }
        }
        if (this.global.valid(l2, l)) {
            HashMap<String, UserAccess> dataOut = new HashMap<String, UserAccess>();
            dataOut.put("data", acces);
            return dataOut;
        }
        return null;
    }

    /**
     * {@code GET  /AccessByUserApplication/{alias},{samaccountname}} : get Access By UserApplication.<br><br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to get Access By UserApplication.
     * @param request request param.
     * @param alias alias param.
     * @param samaccountname samaccountname param.
     * @return dataOut.
     * @throws IOException IOException is thrown.
     */
   @PreAuthorize("hasAuthority('SUPLOCALADMIN') or hasAuthority('AccessReader')  or hasAuthority('AccessWriter')")
    @GetMapping({"/AccessByUserApplication/{alias},{samaccountname}"})
    public HashMap<String, UserAccess> AccessByUserApplication(HttpServletRequest request, @PathVariable String alias, @PathVariable String samaccountname) throws IOException {
        UserAccess acces = this.Service.getAccessById(alias, samaccountname);
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
        if ((acces.getReader() != null && !acces.getReader().isEmpty())
                || (acces.getAuthor() != null && !acces.getAuthor().isEmpty())) {
            prv = acces.getReader().split(",");
            prv2 = acces.getAuthor().split(",");
            for (String p : prv) {
                l2.add(p);
            }
            for (String p : prv2) {
                l2.add(p);
            }
        }
        if (this.global.valid(l2, l)) {
            HashMap<String, UserAccess> dataOut = new HashMap<String, UserAccess>();
            dataOut.put("data", acces);
            return dataOut;
        }
        return null;
    }

    /**
     * {@code POST  /Access} : add Access.<br><br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to add Access.
     * @param request request param.
     * @param access access param.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of expands in body.
     * @throws IOException IOException is thrown.
     */
  @PreAuthorize("hasAuthority('SUPLOCALADMIN') or hasAuthority('AccessWriter') ")
    @PostMapping({"/Access"})
    public ResponseEntity<HashMap<String, UserAccess>> addAccess(HttpServletRequest request,
                                                                 @Valid @RequestBody UserAccess access) throws IOException {
        String token = request.getHeader("Authorization");
        String body = this.global.DecodeJWT(token.substring(token.indexOf("ey")));
        String[] list = body.split("\"authority\":\"");
        String username = body.substring(body.indexOf("\":\"") + 3, body.indexOf("\","));
        List<String> l = new ArrayList<String>();
        for (int i = 1; i < list.length; ++i) {
            l.add(list[i].substring(0, body.split("\"authority\":\"")[i].length() - 4));
        }
        String profileName = access.getProfile().getProfilename();
        String alias = access.getApplication().getAlias();
        String samaccountname = access.getUser().getSamaccountname();
        this.Useraccess = this.Repository.findAll();
        int j;
        for (j = 0; j < this.Useraccess.size()
                && (!this.Useraccess.get(j).getApplication().getAlias().equals(alias)
                || !this.Useraccess.get(j).getUser().getSamaccountname().equals(samaccountname)); ++j) {
        }
        if (j < this.Useraccess.size()) {
            return updateAccess(request, profileName, alias, samaccountname, access);
        }

        HashMap<String, UserAccess> result = this.JsonFormat(this.Service.addAccess(username, access));
        //String res = this.newcreate(access);
        //EsRepository.save(access);

        return ResponseEntity.ok(result);
    }

    /**
     * {@code PUT  /Access/{profileName},{alias},{samaccountname}} : update Access.<br><br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to update Access.
     * @param request request param.
     * @param access access param.
     * @param alias alias param.
     * @param profileName profileName param.
     * @param samaccountname samaccountname param.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of expands in body.
     * @throws IOException IOException is thrown.
     */
  @PreAuthorize("hasAuthority('SUPLOCALADMIN') or hasAuthority('AccessWriter') ")
    @PutMapping({"/Access/{profileName},{alias},{samaccountname}"})
    public ResponseEntity<HashMap<String, UserAccess>> updateAccess(HttpServletRequest request,
                                                                    @PathVariable String profileName, @PathVariable String alias, @PathVariable String samaccountname,
                                                                    @Valid @RequestBody UserAccess access) throws IOException {
        String token = request.getHeader("Authorization");
        String body = this.global.DecodeJWT(token.substring(token.indexOf("ey")));
        String[] list = body.split("\"authority\":\"");
        String username = body.substring(body.indexOf("\":\"") + 3, body.indexOf("\","));
        List<String> l = new ArrayList<String>();
        for (int i = 1; i < list.length; ++i) {
            l.add(list[i].substring(0, body.split("\"authority\":\"")[i].length() - 4));
        }
        UserAccess acces = this.Service.getAccessById(alias, samaccountname);
        String[] prv = null;
        List<String> l2 = new ArrayList<String>();
        if (acces.getAuthor() != null && !acces.getAuthor().isEmpty()) {
            prv = acces.getAuthor().split(",");
        }
        for (String p : prv) {
            l2.add(p);
        }
        if (this.global.valid(l2, l)) {
            LocalDate date = LocalDate.now();

            Profile p2 = this.profileRepository.findByProfilename(profileName);
            Application app = this.appRep.findByAlias(alias).get();
            String A = access.getAuthor();
            String R = access.getReader();
            if (A == null) {
                A = "AccessWriter,SUPLOCALADMIN";
            } else {
                String[] aa = A.split(",");
                Boolean z = false;
                Boolean e = false;
                for (String a : aa) {
                    if (a.equals("AccessWriter")) {
                        z = true;
                    }
                    if (a.equals("SUPLOCALADMIN")) {
                        e = true;
                    }
                }
                if (!z) {
                    A += ",AccessWriter";
                }
                if (!e) {
                    A += ",SUPLOCALADMIN";
                }
            }
            if (R == null) {
                R = "AccessReader";
            } else {
                String[] aa = R.split(",");
                Boolean z = false;
                for (String a2 : aa) {
                    if (a2.equals("AccessReader")) {
                        z = true;
                    }
                }
                if (!z) {
                    R += ",AccessReader";
                }
            }
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
            String json1 = gson.toJson(acces);
            String json2 = gson.toJson(access);
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<HashMap<String, Object>> type = new TypeReference<HashMap<String, Object>>() {
            };
            Map<String, Object> leftMap = mapper.readValue(json1, type);
            Map<String, Object> rightMap = mapper.readValue(json2, type);
            MapDifference<String, Object> difference = Maps.difference(leftMap,
                    rightMap);
            Map<String, MapDifference.ValueDifference<Object>> dd = difference
                    .entriesDiffering();

            acces.setProfile(p2);
            acces.setComment(access.getComment());
            acces.setDateupdate(date);
            acces.setStatus(access.isStatus());
            acces.setTest(access.isTest());
            acces.setApplication(app);
            acces.setAuthor(A);
            acces.setReader(R);
            acces.setModifiedby(username);
            HashMap<String, UserAccess> result = this.JsonFormat(this.Repository.save(acces));
            //String res = this.update(acces.getId(), acces);
            //EsRepository.save(acces);
            return ResponseEntity.ok(result);
        }
        return null;
    }

//	public String newcreate(@RequestBody UserAccess access) throws IOException {
//		this.client = this.config.client();
//		IndexResponse response = (IndexResponse) this.client
//				.prepareIndex("pm.user_access", "user_access", String.valueOf(access.getId()))
//				.setSource(XContentFactory.jsonBuilder().startObject().field("id", access.getId())
//						.field("samaccountname", access.getUser().getSamaccountname())
//						.field("profilename", access.getProfile().getProfilename())
//						.field("alias", access.getApplication().getAlias()).field("comment", access.getComment())
//						.field("status", access.isStatus()).field("test", access.isTest())
//						.field("datecreate", access.getDatecreate()).field("dateupdate", access.getDateupdate())
//						.field("reader", access.getReader()).field("author", access.getAuthor()).endObject())
//				.get();
//		return response.getResult().toString();
//	}
//
//	public String update(@PathVariable int id, @RequestBody UserAccess access) throws IOException {
//		this.client = this.config.client();
//		UpdateRequest updateRequest = new UpdateRequest();
//		((UpdateRequest) updateRequest.index("pm.user_access")).type("user_access").id(String.valueOf(id))
//				.doc(XContentFactory.jsonBuilder().startObject()
//						.field("profilename", access.getProfile().getProfilename())
//						.field("comment", access.getComment()).field("status", access.isStatus())
//						.field("test", access.isTest()).field("dateupdate", access.getDateupdate())
//						.field("reader", access.getReader()).field("author", access.getAuthor()).endObject());
//		try {
//			UpdateResponse updateResponse = (UpdateResponse) this.client.update(updateRequest).get();
//			
//			return updateResponse.status().toString();
//		} catch (InterruptedException | ExecutionException ex2) {
//			Exception ex = null;
//			Exception e = ex;
//			
//			return "Exception";
//		}
//	}
//
//	public String delete(@PathVariable int id) throws MalformedURLException {
//		this.client = this.config.client();
//		DeleteResponse deleteResponse = (DeleteResponse) this.client
//				.prepareDelete("pm.user_access", "user_access", String.valueOf(id)).get();
//		return deleteResponse.getResult().toString();
//	}
//
//	@PostMapping({ "/createUserAccessIndex" })
//	public String create() throws IOException {
//		this.client = this.config.client();
//		this.Useraccess = (List<UserAccess>) this.Repository.findAll();
//		IndexResponse response = null;
//		for (int i = 0; i < this.Useraccess.size(); ++i) {
//			Date DU = new Date(this.Useraccess.get(i).getDateupdate().getYear(),
//					this.Useraccess.get(i).getDateupdate().getMonth(), this.Useraccess.get(i).getDateupdate().getDate(),
//					this.Useraccess.get(i).getDateupdate().getHours(),
//					this.Useraccess.get(i).getDateupdate().getMinutes(),
//					this.Useraccess.get(i).getDateupdate().getSeconds());
//			Date DC = new Date(this.Useraccess.get(i).getDatecreate().getYear(),
//					this.Useraccess.get(i).getDatecreate().getMonth(), this.Useraccess.get(i).getDatecreate().getDate(),
//					this.Useraccess.get(i).getDatecreate().getHours(),
//					this.Useraccess.get(i).getDatecreate().getMinutes(),
//					this.Useraccess.get(i).getDatecreate().getSeconds());
//			response = (IndexResponse) this.client
//					.prepareIndex("pm.user_access", "user_access", String.valueOf(this.Useraccess.get(i).getId()))
//					.setSource(XContentFactory.jsonBuilder().startObject().field("id", this.Useraccess.get(i).getId())
//							.field("samaccountname", this.Useraccess.get(i).getUser().getSamaccountname())
//							.field("profilename", this.Useraccess.get(i).getProfile().getProfilename())
//							.field("alias", this.Useraccess.get(i).getApplication().getAlias())
//							.field("comment", this.Useraccess.get(i).getComment())
//							.field("status", this.Useraccess.get(i).isStatus())
//							.field("test", this.Useraccess.get(i).isTest()).field("datecreate", DC)
//							.field("dateupdate", DU).field("reader", this.Useraccess.get(i).getReader())
//							.field("author", this.Useraccess.get(i).getAuthor()).endObject())
//					.get();
//		}
//		return response.getResult().toString();
//	}




    /**
     * {@code DELETE  /Access/{profileName},{alias},{samaccountname}} : delete Access.<br><br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to delete Access.
     * @param request request param.
     * @param alias alias param.
     * @param profileName profileName param.
     * @param samaccountname samaccountname param.
     * @return msg.
     * @throws UnknownHostException UnknownHostException is thrown.
     * @throws MalformedURLException MalformedURLException is thrown.
     */
    @PreAuthorize("hasAuthority('SUPLOCALADMIN') or hasAuthority('AccessWriter') ")
    @DeleteMapping({"/Access/{profileName},{alias},{samaccountname}"})
    public String deleteAccess(HttpServletRequest request, @PathVariable String profileName, @PathVariable String alias,
                               @PathVariable String samaccountname) throws UnknownHostException, MalformedURLException {
        String token = request.getHeader("Authorization");
        String body = this.global.DecodeJWT(token.substring(token.indexOf("ey")));
        String[] list = body.split("\"authority\":\"");
        String username = body.substring(body.indexOf("\":\"") + 3, body.indexOf("\","));
        List<String> l = new ArrayList<String>();
        for (int i = 1; i < list.length; ++i) {
            l.add(list[i].substring(0, body.split("\"authority\":\"")[i].length() - 4));
        }
        UserAccess std = this.Service.getAccessById1(profileName, alias, samaccountname);
        String[] prv = null;
        List<String> l2 = new ArrayList<String>();
        if (std.getAuthor() != null && !std.getAuthor().isEmpty()) {
            prv = std.getAuthor().split(",");
        }
        for (String p : prv) {
            l2.add(p);
        }
        if (this.global.valid(l2, l)) {
            LocalDate date = LocalDate.now();
            //String res = this.delete(std.getId());
            this.Repository.delete(std);
            //EsRepository.delete(std);
            return "Document Deleted";
        }
        return null;
    }
}
