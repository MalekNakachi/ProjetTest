package com.example.gestionAchat.service.pm;

import com.example.gestionAchat.entities.listePrivillegeUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Collection;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserService userService;
    @Value("${userlocal.name}")
    private String localusername;
    @Value("${userlocal.role}")
    private String localrole;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if (username.equals(localusername)) {
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(localrole));
            //authorities.add(new SimpleGrantedAuthority(adminSysteme));
            return new User(username, "authorise", authorities);
        } else {
            listePrivillegeUser user = userService.getUser(username);
            Collection<GrantedAuthority> authorities = new ArrayList<>();

            authorities.add(new SimpleGrantedAuthority(user.getUser()));

            if (user.getProfiles() != null && !user.getProfiles().isEmpty()) {
                authorities.add(new SimpleGrantedAuthority(user.getProfiles()));
            }
            user.getRoles().forEach(role -> {
                authorities.add(new SimpleGrantedAuthority(role));
            });
            if (user.getOrgaPrivillege() != null) {
                user.getOrgaPrivillege().forEach(pvl -> {
                    authorities.add(new SimpleGrantedAuthority(pvl));
                });
            }

            return new User(user.getUser(), "authorise", authorities);
        }
    }

}
