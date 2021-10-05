package com.example.gestionAchat.service;
//
//import com.mailmanagement.mailmanagement.dto.UserDto;
//import com.mailmanagement.mailmanagement.entities.Group;
//import com.mailmanagement.mailmanagement.entities.Role;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.ldap.core.AttributesMapper;
//import org.springframework.ldap.core.LdapTemplate;
//import org.springframework.ldap.filter.AndFilter;
//import org.springframework.ldap.filter.EqualsFilter;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.naming.directory.Attributes;
//import java.util.*;
//import java.util.stream.Collectors;
//
//import static org.springframework.ldap.query.LdapQueryBuilder.query;
//
//@Service
//@Transactional
//public class UserService {
//
//    @Autowired
//    LdapTemplate ldapTemplate;
//
//    @Autowired
//    RoleService roleService;
//
//    @Autowired
//    GroupService groupService;
//
//    public boolean authentify(String username, String password) {
//        AndFilter filter = new AndFilter();
//        filter.and(new EqualsFilter("sAMAccountName", username));
//        return ldapTemplate.authenticate("", filter.encode(), password);
//    }
//
//    public List<String> getAllUsers() {
//        return ldapTemplate.search(
//                query().where("objectclass").is("person"),
//                new AttributesMapper<String>() {
//                    public String mapFromAttributes(Attributes attributes) throws javax.naming.NamingException {
//                        return attributes.get("name").get().toString();
//                    }
//                });
//    }
//
////  prepare tous les information d'utilisateur
// /*   public UserDto getUser(String username) {
//       //groupService.fetchGroupData();
//        List<String> listUserName =  ldapTemplate.search(
//                query().where("objectclass").is("person").and(
//                        query().where("sAMAccountName").is(username)
//                                .or(query().where("mailNickname").is(username))),
//                new AttributesMapper<String>() {
//                    public String mapFromAttributes(Attributes attributes) throws javax.naming.NamingException {
//                        return attributes.get("name").get(0).toString();
//                    }
//                });
//        String userName = listUserName.get(0);
//        String login = username;
//        List<String> groups = userGroups(username);
//        List<Group> userGroups = new ArrayList<>();
//        Collection<Role> roles = new ArrayList<>();
//        groups.forEach(grp -> {
////            userGroups.add(groupService.getGroupByName(grp));
//            Group group = groupService.getGroupByName(grp);
//            if(group != null) userGroups.add(group);
//            roles.addAll(roleService.getGroupRolesByName(grp));
//        });
//        UserDto userDto = new UserDto(userName, login, userGroups, roles);
//        return userDto;
//    }
//*/
////    retourner list des groups aux quels il appartient
//    public List<String> userGroups(String username) {
//
//        List<String> resultVal = new ArrayList<>();
//        List<String> distinguishedNames = ldapTemplate.search(
//                query().where("objectclass").is("person").and(
//                        query().where("sAMAccountName").is(username)
//                                .or(query().where("mailNickname").is(username))
//                ),
//                (AttributesMapper<String>) attrs -> attrs.get("distinguishedName").get().toString()
//        );
//
//        if (distinguishedNames.isEmpty()) {
//            throw new UsernameNotFoundException("User not recognized in LDAP");
//        }
//
//        if(distinguishedNames.get(0) != null) {
//            List<String> arrOfvalue = Arrays.asList(distinguishedNames.get(0).split(","));
//            resultVal = arrOfvalue.stream().filter( item -> item.startsWith("OU")).map( str -> str.replace("OU=","")).collect(Collectors.toList());
//        }
//        return resultVal;
//    }
//
//    //  get all departement that his the responsible on
//    public List<String> getChefGroups(String userCN) {
//
//        List<String> resultVal = new ArrayList<>();
//
//        List<String> distinguishedNames = this.ldapTemplate.search(
//                query().where("objectclass").is("person").and(
//                        query().where("sAMAccountName").is(userCN)
//                                .or(query().where("mailNickname").is(userCN))),
//                (AttributesMapper<String>) attrs -> {
//
//                    Boolean attrExists;
//
//                    if(attrs.get("managedObjects") != null) attrExists = true;
//                    else attrExists = false;
//
//                    if(attrExists) {
//                        return attrs.get("managedObjects").get().toString();
//                    }
//                    else return null;
//                }
//        );
//
//        if (distinguishedNames.isEmpty()) {
//            throw new UsernameNotFoundException("User not recognized in LDAP");
//        }
//
//        if(distinguishedNames.get(0) != null) {
//            List<String> arrOfvalue = Arrays.asList(distinguishedNames.get(0).split(","));
//            resultVal = arrOfvalue.stream().filter( item -> item.startsWith("OU")).map( str -> str.replace("OU=","")).collect(Collectors.toList());
//        }
//
//        return resultVal;
//    }
//
//
//
//
//
//    public  Map<String, Object> Mail(String userCN) {
//        Map<String, Object> Userproperties = new HashMap<String, Object>();
//
//        List<String> mails= ldapTemplate.search(
//                query().where("objectclass").is("person").and(
//                        query().where("sAMAccountName").is(userCN)
//                                .or(query().where("mailNickname").is(userCN))),
//                (AttributesMapper<String>) attrs -> {
//                    return attrs.get("mail").get().toString();
//                });
//        Userproperties.put("email", mails.get(0));
//        return  Userproperties;
//    }
//
//
//
//
//
//}
//
import com.example.gestionAchat.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class MyService {

     @PersistenceContext(unitName = "externDSEmFactory")
    @Autowired
    private EntityManager em;

   public User findBySamaccountnamMM(GrantedAuthority samaaccountname){
       System.out.println("finalQuery==>");

       String  querySelect= "SELECT * FROM `pm_users` where samaccountname = '"+ samaaccountname+"'";

       System.out.println("finalQuery==>"+ querySelect);
        Query q1 = em.createNativeQuery(querySelect, User.class);
         return (User) q1.getResultList().get(0);

    }


    public String ListGroupsRoles() {
        String GroupRol = "";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        ArrayList<GrantedAuthority> newListAutorities = new ArrayList<>(authorities);
        System.out.println("newListAutorities:::::: "+newListAutorities);

        for (int k = 0; k < newListAutorities.size(); k++)
            GroupRol += "'" + newListAutorities.get(k).getAuthority() + "', ";
        GroupRol = GroupRol.substring(0, GroupRol.length() - 2);
        return GroupRol;

    }




}
