
package com.example.gestionAchat.web.rest.pm;

import com.example.gestionAchat.domain.GroupUserPK;
import com.example.gestionAchat.domain.UsersActifs;
import com.example.gestionAchat.domain.UsersNonActifs;
import com.example.gestionAchat.entities.*;
import com.example.gestionAchat.repository.pm.*;
import com.example.gestionAchat.service.pm.UserAccessService;
import io.github.jhipster.web.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.example.gestionAchat.config.Device;
import com.example.gestionAchat.web.rest.errors.BadRequestAlertException;
import com.example.gestionAchat.entities.dto.Mail;
import com.example.gestionAchat.entities.dto.PasswordForgotDto;
import com.example.gestionAchat.entities.dto.PasswordResetDto;
import com.example.gestionAchat.service.pm.EmailService;
import com.example.gestionAchat.service.pm.UserService;
import com.example.gestionAchat.service.pm.VariableServices;
import com.example.gestionAchat.service.pm.dto.UsersCriteria;
import com.example.gestionAchat.service.pm.query.UsersQueryService;

import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.script.ScriptException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.*;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping({"/api"})
public class UserController {
    private static final Logger log;

    static {
        log = LoggerFactory.getLogger(UserController.class);
    }
    List<UserAccess> Useraccess;
    @Autowired
    GroupUserRepository groupUserRepository;
    @Autowired
    private AppRepository appRep;
    private final Device global;
    @Autowired
    private UserService Service;
    private List<User> users;
    private PasswordEncoder encoder;
    @Autowired
    private UserRepository Repository;

    @Autowired
    private UserAccessRepository accessRepository;
    @Autowired
    private LdapConfigRepository ldapConfigRepository;
    @Autowired
    private UsersQueryService usersQueryService;
    @Autowired
    private PasswordTokenRepository passwordTokenRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    VariableServices variableServices;
    @Autowired
    private UserAccessService userAccessService;

    @Autowired
    GroupRepository groupRepository;

    @PersistenceContext(unitName = "externDSEmFactory")
    @Autowired
    private EntityManager em;

    @Autowired
    private ProfileRepository profileRepository;

    @Value("${userlocal.name}")
    private String localusername;
    @Value("${user.photo.path}")
    private String photo_Path;
    @Value("${support.email}")
    private String from;

    public UserController() {
        this.global = new Device();

    }
    @Value("${spring.datasourceextern.url}")
    private String url;

    @Value("${spring.datasourceextern.username}")
    private String user;

    @Value("${spring.datasourceextern.password}")
    private String password;

    private Connection newConnection() throws SQLException
    {
        return DriverManager.getConnection(url, user, password);
    }


    BCryptPasswordEncoder PasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public HashMap<String, User> JsonFormat(User User) {
        HashMap<String, User> dataOut = new HashMap<String, User>();
        dataOut.put("data", User);
        return dataOut;
    }

    @GetMapping({"/getBuild"})
    public String Build() {
        return "5.77";
    }

    /**
     * {@code GET  /users} : get all the users.<br><br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to get all the users.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     * of users in body.
     */
    //@Cacheable(value = "users")
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers(UsersCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Users by criteria: {}", criteria);
        Page<User> page = usersQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil
                .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /users/count} : count all the users.<br><br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to count all the users.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count
     * in body.
     */
    //@Cacheable(value = "users")
    @GetMapping("/users/count")
    public ResponseEntity<Long> countUsers(UsersCriteria criteria) {
        log.debug("REST request to count Users by criteria: {}", criteria);
        return ResponseEntity.ok().body(usersQueryService.countByCriteria(criteria));
    }

    /**
     * {@code PUT  /changeMp/{samaccountname}} : update Password.<br><br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to update Password.
     *
     * @param newPW          newPW param.
     * @param oldPW          oldPW param.
     * @param request        request param.
     * @param samaccountname samaccountname param.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count
     * in body.
     * @throws UnknownHostException UnknownHostException is thrown.
     */
  @PreAuthorize("hasAuthority('SUPLOCALADMIN') or hasAuthority('UserWriter')  ")
    @PutMapping({"/changeMp/{samaccountname}"})
    public ResponseEntity<User> updatePassword(HttpServletRequest request, @PathVariable String samaccountname,
                                               @RequestParam String oldPW, @RequestParam String newPW) throws UnknownHostException {
        String token = request.getHeader("Authorization");
        String body = this.global.DecodeJWT(token.substring(token.indexOf("ey")));
        String[] list = body.split("\"authority\":\"");
        String username = body.substring(body.indexOf("\":\"") + 3, body.indexOf("\","));
        List<String> l = new ArrayList<String>();
        for (int i = 1; i < list.length; ++i) {
            l.add(list[i].substring(0, body.split("\"authority\":\"")[i].length() - 4));
        }
        Optional<User> std = this.Repository.findBySamaccountname(samaccountname);
        String[] prv = null;
        List<String> l2 = new ArrayList<String>();
        if (std.get().getAuthor() != null && !std.get().getAuthor().isEmpty()) {
            prv = std.get().getAuthor().split(",");
        }
        for (String p : prv) {
            l2.add(p);
        }
        if (this.global.valid(l2, l)) {

            this.encoder = this.PasswordEncoder();
            User temp = this.Repository.findBySamaccountname(samaccountname).get();

            if (temp.getConfigLdap() == null) {

                String exisMP = temp.getPassword();
                if (this.encoder.matches(oldPW, exisMP)) {

                    temp.setModifiedby(username);
                    temp.setPassword(this.encoder.encode(newPW));
                    User result = Repository.save(temp);
                    // EsRepository.save(temp);
                    return ResponseEntity.ok(result);

                }
            }
        }
        return (ResponseEntity<User>) ResponseEntity.badRequest();
    }

    @PreAuthorize("hasAuthority('SUPLOCALADMIN') or hasAuthority('UserWriter')  ")
    @PutMapping({"/changeMpUser/{samaccountname}"})
    public ResponseEntity<User> updatePasswordUser(HttpServletRequest request, @PathVariable String samaccountname,
                                              @RequestParam String newPW) throws UnknownHostException {
        String token = request.getHeader("Authorization");
        String body = this.global.DecodeJWT(token.substring(token.indexOf("ey")));
        String[] list = body.split("\"authority\":\"");
        String username = body.substring(body.indexOf("\":\"") + 3, body.indexOf("\","));
        List<String> l = new ArrayList<String>();
        for (int i = 1; i < list.length; ++i) {
            l.add(list[i].substring(0, body.split("\"authority\":\"")[i].length() - 4));
        }
        Optional<User> std = this.Repository.findBySamaccountname(samaccountname);
        String[] prv = null;
        List<String> l2 = new ArrayList<String>();
        if (std.get().getAuthor() != null && !std.get().getAuthor().isEmpty()) {
            prv = std.get().getAuthor().split(",");
        }
        for (String p : prv) {
            l2.add(p);
        }
        if (this.global.valid(l2, l)) {

            this.encoder = this.PasswordEncoder();
            User temp = this.Repository.findBySamaccountname(samaccountname).get();

            if (temp.getConfigLdap() == null) {




                    temp.setModifiedby(username);
                    temp.setPassword(this.encoder.encode(newPW));
                    User result = Repository.save(temp);
                    // EsRepository.save(temp);
                    return ResponseEntity.ok(result);

                }}


        return (ResponseEntity<User>) ResponseEntity.badRequest();
    }




    @PutMapping({"/changeMpUser"})
    public ResponseEntity<User> updatePsdUser(HttpServletRequest request,
                                                   @RequestParam String newPW) throws UnknownHostException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User temp = this.Repository.findBySamaccountname(authentication.getPrincipal().toString()).get();

            this.encoder = this.PasswordEncoder();

                temp.setModifiedby(authentication.getPrincipal().toString());
                temp.setPassword(this.encoder.encode(newPW));
                User result = Repository.save(temp);
                // EsRepository.save(temp);
                return ResponseEntity.ok(result);

    }


    @GetMapping("/ListSamaacounts")
    public List<String>  getAllSamaAccountsString() {

        List<User>  liste= Repository.findAll();

        List<String> usersstring = new ArrayList<>();

        for(int k=0; k<liste.size(); k++)
            usersstring.add(liste.get(k).getSamaccountname());

        if(liste.isEmpty())
            return null;
        else
            return usersstring;
    }



    /**
     * {@code PUT  /updatePasswordByAdmin/{samaccountname}} : update Password By Admin.<br><br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to update Password By Admin.
     *
     * @param newPW          newPW param.
     * @param request        request param.
     * @param samaccountname samaccountname param.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count
     * in body.
     * @throws UnknownHostException UnknownHostException is thrown.
     */
@PreAuthorize("hasAuthority('SUPLOCALADMIN') or hasAuthority('UserWriter')or hasAuthority('SysWriter') ")

    @PutMapping({"/updatePasswordByAdmin/{samaccountname}"})
    public ResponseEntity<User> updatePasswordByAdmin(HttpServletRequest request, @PathVariable String samaccountname,
                                                      @RequestParam String newPW) throws UnknownHostException {
        this.encoder = this.PasswordEncoder();

        String token = request.getHeader("Authorization");
        String body = this.global.DecodeJWT(token.substring(token.indexOf("ey")));
        String[] list = body.split("\"authority\":\"");
        String username = body.substring(body.indexOf("\":\"") + 3, body.indexOf("\","));
        List<String> l = new ArrayList<String>();
        for (int i = 1; i < list.length; ++i) {
            l.add(list[i].substring(0, body.split("\"authority\":\"")[i].length() - 4));
        }
        Optional<User> std = this.Repository.findBySamaccountname(samaccountname);
        String[] prv = null;
        List<String> l2 = new ArrayList<String>();
        if (std.get().getAuthor() != null && !std.get().getAuthor().isEmpty()) {
            prv = std.get().getAuthor().split(",");
        }
        for (String p : prv) {
            l2.add(p);
        }
        if (this.global.valid(l2, l)) {
            // if (std.get().getConfigLdap() != null) {
            if (std.isPresent()) {
                User u = std.get();
                u.setModifiedby(username);
                u.setPassword(this.encoder.encode(newPW));

                User result = this.Repository.save(u);
                // EsRepository.save(u);
                return ResponseEntity.ok(result);

            }
            // }
        }
        return (ResponseEntity<User>) ResponseEntity.badRequest();
    }

    /**
     * {@code GET  /getUserPicture} : get UserPicture.<br><br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to get UserPicture.
     *
     * @param directory directory param.
     * @param response  response param.
     * @throws IOException IOException is thrown.
     */
    @RequestMapping(value = {"/getUserPicture"}, method = {RequestMethod.GET}, produces = {"image/png"})
    public void getUserPicture(HttpServletResponse response, @RequestParam(name = "directory", defaultValue = "userdefault.png") String directory) throws IOException, ScriptException {
        if (directory.isEmpty()) directory = "userdefault.png";
        String filepath= photo_Path + directory;
        System.out.println(filepath);
        File file = new File(filepath);
       //  File file = new File("imen.bouaziz.png");
        BufferedImage image = null;
        try {
            image = ImageIO.read(file);
        } catch (Exception e) {
            image = ImageIO.read(new File(filepath));
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        baos.flush();
        byte[] imageInByte = baos.toByteArray();
        baos.close();
        response.addHeader("Content-Type", "image/png");
        response.getOutputStream().write(imageInByte);

    }

    /**
     * {@code GET  /Users} : get all Users.<br><br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to get all Users.
     *
     * @param request request param.
     * @return dataOut.
     */
   @PreAuthorize("hasAuthority('SUPLOCALADMIN') or hasAuthority('UserReader') or hasAuthority('UserWriter') ")

    //@Cacheable(value = "users")
    @GetMapping({"/Users"})
    public HashMap<String, List<User>> allUsers(HttpServletRequest request) {
       return this.Service.allUsers(request, this.global);
    }

    @PreAuthorize("hasAuthority('SUPLOCALADMIN') or hasAuthority('UserReader') or hasAuthority('UserWriter') ")

    @GetMapping({"/CountUsersActifs"})
    public BigInteger CountallUsersActifs()  {
        BigInteger count = this.Service.countallUsersActifsLazy();
        return count;
    }

    @GetMapping({"/CountUsersNonActifs"})
    public BigInteger CountallUsersNonActifs()  {
        BigInteger count = this.Service.countallUsersNonActifsLazy();
        return count;
    }


    @PreAuthorize("hasAuthority('SUPLOCALADMIN') or hasAuthority('UserReader') or hasAuthority('UserWriter') ")

    @GetMapping({"/UsersActifs"})
    public List<UsersActifs> allUsersActifs(int skip, int take, String filter, String orderBy)  {

//        String viewUsersActifs = "SELECT * from `usersview`";
//
//        Query q = (Query) em.createNativeQuery(viewUsersActifs, UsersActifs.class);


        List<UsersActifs> liste = this.Service.allUsersActifsLazy(skip, take, filter, orderBy);

        return liste;
    }


    @PreAuthorize("hasAuthority('SUPLOCALADMIN') or hasAuthority('UserReader') or hasAuthority('UserWriter') ")

    @GetMapping({"/UsersNonActifs"})
    public List<UsersNonActifs> allUsersNonActifs(int skip, int take, String filter, String orderBy){

    List<UsersNonActifs> liste = this.Service.allUsersNonActifsLazy(skip, take, filter, orderBy);

        return liste;
    }
    /**
     * {@code GET  /UserByDisplayname/{displayname}} : get User By Displayname.<br><br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to get User By Displayname.
     *
     * @param request     request param.
     * @param displayname displayname param.
     * @return dataOut.
     * @throws IOException IOException is thrown.
     */
   @PreAuthorize("hasAuthority('SUPLOCALADMIN') or hasAuthority('UserReader') or hasAuthority('UserWriter') ")
    //@Cacheable(value = "users")
    @GetMapping({"/UserByDisplayname/{displayname}"})
    public HashMap<String, User> UserByDisplayname(HttpServletRequest request, @PathVariable String displayname)
            throws IOException {
        User user = this.Repository.findByDisplayname(displayname);
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
        if ((user.getReader() != null && !user.getReader().isEmpty())
                || (user.getAuthor() != null && !user.getAuthor().isEmpty())) {
            prv = user.getReader().split(",");
            prv2 = user.getAuthor().split(",");
            for (String p : prv) {
                l2.add(p);
            }
            for (String p : prv2) {
                l2.add(p);
            }
        }
        HashMap<String, User> dataOut = new HashMap<String, User>();

        if (this.global.valid(l2, l)) {
            LocalDate date = LocalDate.now();
            dataOut.put("data", user);
        }
        return dataOut;
    }

    /**
     * {@code GET  /UserByProfile/{profilename}} : get User By Profile.<br><br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to get User By Profile.
     *
     * @param request     request param.
     * @param profilename profilename param.
     * @return dataOut.
     * @throws IOException IOException is thrown.
     */
 @PreAuthorize("hasAuthority('SUPLOCALADMIN') or hasAuthority('UserReader') or hasAuthority('UserWriter') ")
    //@Cacheable(value = "users")
    @GetMapping({"/UserByProfile/{profilename}"})
    public HashMap<String, List<User>> UserByProfile(HttpServletRequest request, @PathVariable String profilename)
            throws IOException {
        List<User> u = this.Service.getUserByProfile(profilename);
        HashMap<String, List<User>> dataOut = new HashMap<String, List<User>>();
        dataOut.put("data", u);
        return dataOut;
    }

    /**
     * {@code GET /UserByDBID/{id}} : get User By DBID.<br><br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to get User By DBID.
     *
     * @param request request param.
     * @param id      id param.
     * @return dataOut.
     * @throws IOException IOException is thrown.
     */
  @PreAuthorize("hasAuthority('SUPLOCALADMIN') or hasAuthority('UserReader') or hasAuthority('UserWriter') ")
    //@Cacheable(value = "users")
    @GetMapping({"/UserByDBID/{id}"})
    public HashMap<String, Optional<User>> UserByDBID(HttpServletRequest request, @PathVariable Long id)
            throws IOException {
        Optional<User> user = this.Repository.findById(id);
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
        if ((user.get().getReader() != null && !user.get().getReader().isEmpty())
                || (user.get().getAuthor() != null && !user.get().getAuthor().isEmpty())) {
            prv = user.get().getReader().split(",");
            prv2 = user.get().getAuthor().split(",");
            for (String p : prv) {
                l2.add(p);
            }
            for (String p : prv2) {
                l2.add(p);
            }
        }
        HashMap<String, Optional<User>> dataOut = new HashMap<String, Optional<User>>();

        if (this.global.valid(l2, l)) {
            LocalDate date = LocalDate.now();
            dataOut.put("data", user);
        }
        return dataOut;
    }

    /**
     * {@code GET /getMy} : get My.<br><br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to get My.
     *
     * @param request request param.
     * @return dataOut.
     * @throws IOException IOException is thrown.
     */
    @GetMapping({"/getMy"})
    public HashMap<String, User> getMy(HttpServletRequest request) throws IOException {
        String token = request.getHeader("Authorization");
        String body = this.global.DecodeJWT(token.substring(token.indexOf("ey")));
        String[] list = body.split("\"authority\":\"");
        List<String> l = new ArrayList<String>();
        for (int i = 1; i < list.length; ++i) {
            l.add(list[i].substring(0, body.split("\"authority\":\"")[i].length() - 4));
        }
        String samaccountname = body.substring(body.indexOf("\":\"") + 3, body.indexOf("\","));

        User user;
        if (!samaccountname.equals(localusername)) {
            user = Repository.findBySamaccountname(samaccountname).get();
        } else {
            user = new User(localusername, "SUPLOCALADMIN", "SUPLOCALADMIN");
        }
        HashMap<String, User> dataOut = new HashMap<String, User>();

        dataOut.put("data", user);

        return dataOut;
    }

    /**
     * {@code GET /UserById/{samaccountname}} : get User By Id.<br><br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to get User By Id.
     *
     * @param request        request param.
     * @param samaccountname samaccountname param.
     * @return dataOut.
     * @throws IOException IOException is thrown.
     */
   @PreAuthorize("hasAuthority('SUPLOCALADMIN') or hasAuthority('UserReader')  or hasAuthority('UserWriter')")
    //@Cacheable(value = "users")
    @GetMapping({"/UserById/{samaccountname}"})
    public HashMap<String, Optional<User>> UserById(HttpServletRequest request, @PathVariable String samaccountname)
            throws IOException {
        Optional<User> user = this.Repository.findBySamaccountname(samaccountname);
       if(user.get().getAppartenance()==null)
       {
           if(user.get().getDistinguishedname()!=null)
               if(user.get().getDistinguishedname().indexOf("OU=")!=-1)
               {
                    String chaine=user.get().getDistinguishedname().substring
                            (user.get().getDistinguishedname().indexOf("OU=") + 3);

                     String app= chaine.substring(0, chaine.indexOf(","));
                   user.get().setAppartenance(app);
               }
       }
        User usersaved=  this.Repository.save(user.get());

       HashMap<String, Optional<User>> dataOut = new HashMap<String, Optional<User>>();
//        if (this.global.valid(l2, l)) {
            LocalDate date = LocalDate.now();
            dataOut.put("data",Optional.of(usersaved));
      //  }
        return dataOut;
    }

    /**
     * {@code GET /getManagerOfUser/{samaccountname}} : get ManagerOf User.<br><br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to get ManagerOf User.
     *
     * @param samaccountname samaccountname param.
     * @return String.
     */
    //@Cacheable(value = "users")
   @PreAuthorize("hasAuthority('SUPLOCALADMIN') or hasAuthority('UserReader') or hasAuthority('UserWriter') ")
    @GetMapping({"/getManagerOfUser/{samaccountname}"})
    public String getManagerOfUser(@PathVariable String samaccountname) {
        return this.Repository.getManagerOfUser(samaccountname);
    }

    /**
     * {@code GET /getUserByApplication/{application}} : get User By Application.<br><br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to get User By Application.
     *
     * @param application application param.
     * @return dataOut.
     */
    //@Cacheable(value = "users")
    @PreAuthorize("hasAuthority('SUPLOCALADMIN') or hasAuthority('UserReader') or hasAuthority('UserWriter') ")
    @GetMapping({"/getUserByApplication/{application}"})
    public HashMap<String, List<User>> getUserByApplication(@PathVariable String application) {
        List<User> u = Service.getUserByApplication(application);

        HashMap<String, List<User>> dataOut = new HashMap<String, List<User>>();
        dataOut.put("data", u);
        return dataOut;
    }

    /**
     * {@code POST /getUserByApplication/{application}} : add User.<br><br>
     * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to add User.
     *
     * @param request request param.
     * @param user    user param.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of expands in body.
     * @throws IOException IOException is thrown.
     */
    //@Cacheable(value = "users")
    @PreAuthorize("hasAuthority('SUPLOCALADMIN') or hasAuthority('UserWriter') ")
    @PostMapping({"/User"})
    public ResponseEntity<HashMap<String, User>> addUser(HttpServletRequest request, @Valid @RequestBody User user)
            throws IOException {

       // System.out.println("111");
        String token = request.getHeader("Authorization");
        String body = this.global.DecodeJWT(token.substring(token.indexOf("ey")));
       // System.out.println("body::"+body);
        String[] list = body.split("\"authority\":\"");
        System.out.println(list.length);
        String username = body.substring(body.indexOf("\":\"") + 3, body.indexOf("\","));
     //   System.out.println("username::"+username);

        List<String> l = new ArrayList<String>();
        for (int i = 1; i < list.length; ++i) {
            l.add(list[i].substring(0, body.split("\"authority\":\"")[i].length() - 4));
        }
        String samaccountname = user.getSamaccountname();
        if (!samaccountname.equals(localusername)) {
            this.users = this.Repository.findAll();

            String A = user.getAuthor();
            String R = user.getReader();
            if (A == null) {
                A = "UserWriter,SUPLOCALADMIN";
            } else {
                String[] aa = A.split(",");
                Boolean z = false;
                Boolean e = false;
                for (String a : aa) {
                    if (a.equals("UserWriter")) {
                        z = true;
                    }
                    if (a.equals("SUPLOCALADMIN")) {
                        e = true;
                    }
                }
                if (!z) {
                    A += ",UserWriter";
                }
                if (!e) {
                    A += ",SUPLOCALADMIN";
                }
            }
            if (R == null) {
                R = "UserReader";
            } else {
                String[] aa = R.split(",");
                Boolean z = false;
                for (String a2 : aa) {
                    if (a2.equals("UserReader")) {
                        z = true;
                    }
                }
                if (!z) {
                    R += ",UserReader";
                }
            }
            this.encoder = this.PasswordEncoder();
            LocalDate date = LocalDate.now();

            if (user.getPassword() != null)
                user.setPassword(this.encoder.encode(user.getPassword()));
            List<ConfigLdap> Ldaps = ldapConfigRepository.findAll();
            int i = 0;
            Boolean test = false;
            while (i < Ldaps.size() && !test) {
                ConfigLdap c = Ldaps.get(i);
                if ((user.getDistinguishedname() != null) && (user.getDistinguishedname().contains(c.getBase()))) {
                    System.out.println(user.getSamaccountname() + "    " + c.getName());
                    test = true;
                    user.setConfigLdap(c);
                } else {
                    user.setConfigLdap(null);
                }
                i++;
            }
            user.setCreatedby(username);
            user.setAuthor(A);
            user.setReader(R);
            user.setDatecreate(date);
            user.setDateupdate(date);
            HashMap<String, User> result = this.JsonFormat(this.Repository.save(user));
            // String res = this.newcreate(user);
            // EsRepository.save(user);
            return ResponseEntity.ok(result);
        }
        return (ResponseEntity<HashMap<String, User>>) ResponseEntity.badRequest();
    }

        @PostMapping({"/UserGroup"})
        public ResponseEntity<HashMap<String, User>> addUserGroup(HttpServletRequest request, @Valid @RequestBody User user, @RequestParam String groupe, @RequestParam String profile)
                throws IOException, SQLException {
            LocalDate date = LocalDate.now();
            System.out.println("profile:::"+profile);
            if(profile.equals("null"))
                profile="";
            if(groupe.equals("null"))
                groupe="";
        System.out.println("111");
        String token = request.getHeader("Authorization");
        String body = this.global.DecodeJWT(token.substring(token.indexOf("ey")));
        System.out.println("body::" + body);
        String[] list = body.split("\"authority\":\"");
        System.out.println(list.length);
        String username = body.substring(body.indexOf("\":\"") + 3, body.indexOf("\","));
       // System.out.println("username::" + username);

        List<String> l = new ArrayList<String>();
        for (int i = 1; i < list.length; ++i) {
            l.add(list[i].substring(0, body.split("\"authority\":\"")[i].length() - 4));
        }
        String samaccountname = user.getSamaccountname();

      // System.out.println("samaccountname::" + samaccountname);

        Optional<User> utilisateur=null;


        if (!samaccountname.equals(localusername)) {
            utilisateur = this.Repository.findBySamaccountname(user.getSamaccountname());
            String A = user.getAuthor();
            String R = user.getReader();
            if (A == null) {
                A = "UserWriter,SUPLOCALADMIN";
            } else {
                String[] aa = A.split(",");
                Boolean z = false;
                Boolean e = false;
                for (String a : aa) {
                    if (a.equals("UserWriter")) {
                        z = true;
                    }
                    if (a.equals("SUPLOCALADMIN")) {
                        e = true;
                    }
                }
                if (!z) {
                    A += ",UserWriter";
                }
                if (!e) {
                    A += ",SUPLOCALADMIN";
                }
            }
            if (R == null) {
                R = "UserReader";
            } else {
                String[] aa = R.split(",");
                Boolean z = false;
                for (String a2 : aa) {
                    if (a2.equals("UserReader")) {
                        z = true;
                    }
                }
                if (!z) {
                    R += ",UserReader";
                }
            }
           //
            if (!utilisateur.isPresent()) {

                System.out.println("usernotPresent::");



                if (user.getId()==null)
                {
                    this.encoder = this.PasswordEncoder();
                   user.setPassword(this.encoder.encode(user.getPassword()));}

                List<ConfigLdap> Ldaps = ldapConfigRepository.findAll();
//            int i = 0;
//            Boolean test = false;
//            while (i < Ldaps.size() && (!test))
//            {
                ConfigLdap c = Ldaps.get(0);
                if  (user.getAuthentication_mode().equals("Ad")){
                    System.out.println(user.getSamaccountname() + "    " + c.getName());
                    //  test = true;
                    user.setConfigLdap(c);
                } else {
                    user.setConfigLdap(null);
                }

                //   }


                user.setCreatedby(username);
                user.setAuthor(A);
                user.setReader(R);
                user.setDatecreate(date);
                user.setDateupdate(date);


            } else
            //MAJ
            {
            //  System.out.println("userPasswordB::"+user.getPassword());
                user.setId(utilisateur.get().getId());
                user.setModifiedby(username);

              //  user.setPassword((this.encoder.encode(user.getPassword())));

                user.setComment(user.getComment());
                user.setDateupdate(date);
                user.setTest(user.isTest());
                user.setStatus(user.isStatus());
                user.setDescription(user.getDescription());
                user.setDisplayname(user.getDisplayname());
                user.setDistinguishedname(user.getDistinguishedname());
                user.setMail(user.getMail());
                user.setManager(user.getManager());
                user.setAuthor(A);
                user.setReader(R);
                user.setMobile(user.getMobile());
                user.setEmployeetype(user.getEmployeetype());
                user.setEmployeeid(user.getEmployeeid());


            }


       //     System.out.println("Enregistrement User::");
            HashMap<String, User> result = this.JsonFormat(this.Repository.save(user));
           // System.out.println("userPasswordA::"+result.get("data").getPassword());
            if(!groupe.equals(""))
            {

                Group groupeOfUser = groupRepository.findByName(groupe);

                if (groupeOfUser != null) {
                    //System.out.println("Ids::" + groupeOfUser.getId() + "::" + user.getId());


                    GroupUserPK groupUserPK = new GroupUserPK(groupeOfUser.getId(), user.getId());
                    GroupUser groupUser = new GroupUser(groupUserPK, user, groupeOfUser);


                    //     Optional<GroupUser> gr = groupUserRepository.findById(groupUserPK);


  //                  System.out.println("Beforegroup::" + groupUser.getGroupUserPK().toString());
//            List<GroupUser> groupUserList = new ArrayList<GroupUser>();
//            groupUserList.add(groupUser);
//            Set<GroupUser> targetSet = new HashSet<>(groupUserList);

//            user.setGroupUserSet(targetSet);

                    groupUser.setGroupUserPK(groupUserPK);
               //     System.out.println("group Of user::" + groupeOfUser.getName());

                    Optional<GroupUser> verifGroupUser = groupUserRepository.findByUser_Id(result.get("data").getId());

                    if (!verifGroupUser.isPresent())
                        groupUser = this.groupUserRepository.save(groupUser);
                    else
                    {
                        //delete all users for group
                     //  String ch = "delete from pm_group_users where group_id= " + groupUser.getGroup().getId()));

                        //NativeQuery to change group of user


                        String ch = "update pm_group_users set group_id= " + groupUser.getGroup().getId() + " where user_id =" + result.get("data").getId();


                        Connection conn = newConnection();
                        Statement st = conn.createStatement();
                        st.executeUpdate(ch);

                        conn.close();

                    }
                }

            }
            else
            {


                Optional<GroupUser> verifGroupUser = groupUserRepository.findByUser_Id(result.get("data").getId());
                if(verifGroupUser.isPresent())
                groupUserRepository.delete(verifGroupUser.get());




            }

       //     System.out.println("user to fetch::" + result.get("data").getSamaccountname());
            List<UserAccess> listeaccess = this.userAccessService.getAccessByUser(result.get("data").getSamaccountname());


            if(!profile.equals("") ) {
                System.out.println("Size::" + listeaccess.size());
                UserAccess access = new UserAccess();
                if (listeaccess.size() != 0) {
                    access = listeaccess.get(0);
                    if (profileRepository.findByProfilename(profile) != null)
                        access.setProfile(profileRepository.findByProfilename(profile));
                }
                //   updateAccess(request, profilename, "mm8", samaccountname, access);

                Application app = this.appRep.findByAlias("mm8").get();
                Profile p2 = this.profileRepository.findByProfilename(profile);
                if(p2!= null)
                access.setProfile(p2);
                access.setComment(access.getComment());
                access.setDateupdate(date);
                access.setStatus(access.isStatus());
                access.setTest(access.isTest());
                access.setApplication(app);
                access.setAuthor(A);
                access.setReader(R);
                access.setModifiedby(username);
                access.setUser(result.get("data"));
                access = this.accessRepository.save(access);
                System.out.println("savedAccess::" + access);

            }

            else {

                List<UserAccess> accessuser = this.userAccessService.getAccessByUser(result.get("data").getSamaccountname());
                UserAccess access = new UserAccess();
                if (accessuser.size() != 0) {
                    access = accessuser.get(0);
                }

                this.accessRepository.delete(access);

            }



            return ResponseEntity.ok(result);
        }
        return (ResponseEntity<HashMap<String, User>>) ResponseEntity.badRequest();
    }

    @PreAuthorize("hasAuthority('SUPLOCALADMIN') or hasAuthority('UserWriter') ")
        @PutMapping({"/User/{samaccountname}"})
        public ResponseEntity<HashMap<String, User>> updateUser(HttpServletRequest request,
                @PathVariable String samaccountname, @Valid @RequestBody User user) throws IOException {
            String token = request.getHeader("Authorization");
            String body = this.global.DecodeJWT(token.substring(token.indexOf("ey")));
            String[] list = body.split("\"authority\":\"");
            String username = body.substring(body.indexOf("\":\"") + 3, body.indexOf("\","));
            List<String> l = new ArrayList<String>();
            for (int i = 1; i < list.length; ++i) {
                l.add(list[i].substring(0, body.split("\"authority\":\"")[i].length() - 4));
            }
            Optional<User> std = this.Repository.findBySamaccountname(samaccountname);
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
                    User u = std.get();
                    String A = user.getAuthor();
                    String R = user.getReader();
                    if (A == null) {
                        A = "UserWriter,SUPLOCALADMIN";
                    } else {
                        String[] aa = A.split(",");
                        Boolean z = false;
                        Boolean e = false;
                        for (String a : aa) {
                            if (a.equals("UserWriter")) {
                                z = true;
                            }
                            if (a.equals("SUPLOCALADMIN")) {
                                e = true;
                            }
                        }
                        if (!z) {
                            A += ",UserWriter";
                        }
                        if (!e) {
                            A += ",SUPLOCALADMIN";
                        }
                    }
                    if (R == null) {
                        R = "UserReader";
                    } else {
                        String[] aa = R.split(",");
                        Boolean z = false;
                        for (String a2 : aa) {
                            if (a2.equals("UserReader")) {
                                z = true;
                            }
                        }
                        if (!z) {
                            R += ",UserReader";
                        }
                    }
                    List<ConfigLdap> Ldaps = ldapConfigRepository.findAll();
                    int i = 0;
                    Boolean test = false;
                    while (i < Ldaps.size() && !test) {
                        ConfigLdap c = Ldaps.get(i);
                        if ((user.getDistinguishedname() != null) && (user.getDistinguishedname().contains(c.getBase()))) {
                            System.out.println(user.getSamaccountname() + "    " + c.getName());
                            test = true;
                            u.setConfigLdap(c);
                        } else {
                            u.setConfigLdap(null);
                        }
                        i++;
                    }

                    u.setModifiedby(username);
                    if (user.getConfigLdap() != null) {
                        u.setPassword(null);
                    }
                    u.setPassword(u.getPassword());
                    u.setComment(user.getComment());
                    u.setDateupdate(date);
                    u.setTest(user.isTest());
                    u.setStatus(user.isStatus());
                    u.setDescription(user.getDescription());
                    u.setDisplayname(user.getDisplayname());
                    u.setDistinguishedname(user.getDistinguishedname());
                    u.setMail(user.getMail());
                    u.setManager(user.getManager());
                    u.setAuthor(A);
                    u.setReader(R);
                    u.setMobile(user.getMobile());
                    u.setEmployeetype(user.getEmployeetype());
                    u.setEmployeeid(user.getEmployeeid());
                    HashMap<String, User> result = this.JsonFormat(this.Repository.save(u));
                    // String res = this.update(u.getId(), u);
                    // EsRepository.save(u);
                    return ResponseEntity.ok(result);
                }
            }
            return null;

        }



        /**
         * {@code DELETE /User/{samaccountname}} : delete User.<br><br>
         * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to delete User.
         *
         * @param request        request param.
         * @param samaccountname samaccountname param.
         * @return msg.
         * @throws UnknownHostException  UnknownHostException is thrown.
         * @throws MalformedURLException MalformedURLException is thrown.
         */
   @PreAuthorize("hasAuthority('SUPLOCALADMIN') or hasAuthority('UserWriter') ")
        @DeleteMapping({"/User/{samaccountname}"})
        public String deleteUser(HttpServletRequest request, @PathVariable String samaccountname)
                throws UnknownHostException, MalformedURLException, SQLException {
            String token = request.getHeader("Authorization");
            String body = this.global.DecodeJWT(token.substring(token.indexOf("ey")));
            String[] list = body.split("\"authority\":\"");
            String username = body.substring(body.indexOf("\":\"") + 3, body.indexOf("\","));
            List<String> l = new ArrayList<String>();
            for (int i = 1; i < list.length; ++i) {
                l.add(list[i].substring(0, body.split("\"authority\":\"")[i].length() - 4));
            }
            Optional<User> std = this.Repository.findBySamaccountname(samaccountname);
            System.out.println((std.get().getId()));

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

                //Supression de l'enregistrement en relation avec ce user de la table GroupUser
                String ch= "Delete FROM pm_group_users WHERE user_id="+std.get().getId();



                Connection conn = newConnection();
                Statement st = conn.createStatement();
                st.executeUpdate(ch);
                System.out.println("SuppressionUserGroup");





                //Supression de l'enregistrement en relation avec ce user de la table UserAccess
                System.out.println("SuppressionUserAccess");
                Optional<UserAccess> usr = this.accessRepository.findUserAccessById("mm8", std.get().getSamaccountname());
                if(usr.isPresent())
                  this.accessRepository.delete(usr.get());

                System.out.println("SuppressionUser");
               // this.Repository.delete(std.get());

                String ch2= "Delete FROM pm_users WHERE id="+std.get().getId();
                st.executeUpdate(ch2);

                conn.close();






                return "Document Deleted";
            }
            return null;
        }

        /**
         * {@code GET /getMembreActive} : get Membre Active.<br><br>
         * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to get Membre Active.
         *
         * @param status status param.
         * @return size.
         */
        @GetMapping({"/getMembreActive"})
        public int getMembreActive(@RequestParam Boolean status) {
            return this.Repository.findAllByStatus(status).size();
        }

        /**
         * {@code GET /getMembreTest} : get Membre Test.<br><br>
         * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to get Membre Test.
         *
         * @param test test param.
         * @return size.
         */
        @GetMapping({"/getMembreTest"})
        public int getMembreTest(@RequestParam Boolean test) {
            return this.Repository.findAllByTest(test).size();
        }

        /**
         * {@code GET /getMembreTotal} : get Membre Total.<br><br>
         * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to get Membre Total.
         *
         * @return size.
         */
        @GetMapping({"/getMembreTotal"})
        public int getMembreTotal() {
            return this.Repository.findAll().size();
        }

        /**
         * {@code GET /getMembreHasNoAccess} : get Membre Has No Access.<br><br>
         * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to get Membre Has No Access.
         *
         * @return Object.
         */
        @GetMapping({"/getMembreHasNoAccess"})
        public Object getMembreHasNoAccess() {
            return this.Service.getMembreHasNoAccess().get(0);
        }

        //   @GetMapping({"/reset-password"})
        public PasswordResetToken displayResetPasswordPage(@RequestParam String token) {

            PasswordResetToken resetToken = passwordTokenRepository.findByToken(token);
            if (resetToken == null) {
                // return "Could not find password reset token.";
                throw new BadRequestAlertException("Could not find password reset token", "reset pw", "Could not find password reset token");
            } else if (resetToken.isExpired()) {
                //return "Token has expired, please request a new password reset.";
                throw new BadRequestAlertException("Token has expired, please request a new password reset", "reset pw", "Token has expired, please request a new password reset");

            } else {
                return resetToken;
            }
        }




        @PostMapping({"/forgot-password"})
        public String processForgotPasswordForm(@RequestBody @Valid PasswordForgotDto fg,
                BindingResult result,
                HttpServletRequest request) {

            if (result.hasErrors()) {
                return "forgot-password";
            }
            Optional<User> user = this.Repository.findUserByMail(fg.getEmail());
            if (!user.isPresent()) {
                result.rejectValue("email", null, "We could not find an account for that e-mail address.");
                return "forgot-password";
            }
            User u = user.get();
            PasswordResetToken token = new PasswordResetToken();
            token.setToken(UUID.randomUUID().toString());
            token.setUseriD(u);
            token.setExpiryDate(30);
            passwordTokenRepository.save(token);

            Mail mail = new Mail();
            mail.setFrom(from);
            mail.setTo(fg.getEmail());
            mail.setSubject("Password reset request");
            Map<String, Object> model = new HashMap<>();
            model.put("token", token);
            model.put("user", u);
            model.put("signature", "www.picosoft.biz");
            model.put("resetUrl", getAppUrl(request) + "/reset-password?token=" + token.getToken());
            mail.setModel(model);
            emailService.sendEmail(mail);
            return "mailer send";

        }

        private String getAppUrl(HttpServletRequest request) {
            return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        }

        @PostMapping({"/reset-password"})
        @Transactional
        public String handlePasswordReset(@RequestBody @Valid PasswordResetDto form) {
            this.encoder = this.PasswordEncoder();

            PasswordResetToken token = passwordTokenRepository.findByToken(form.getToken());
            User user = token.getUseriD();
            String updatedPassword = this.encoder.encode(form.getPassword());
            Service.updatePassword(updatedPassword, user.getId());
            passwordTokenRepository.delete(token);

            return "resetSuccess";
        }

        @GetMapping("/getuser/{samaccountname}")
        public boolean getUserBySamaccountname(@PathVariable String samaccountname) {
            Optional<User> u = this.Repository.findBySamaccountname(samaccountname);
            return u.isPresent();
        }
    }



