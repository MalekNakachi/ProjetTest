package com.example.gestionAchat.config;


import com.example.gestionAchat.repository.AclSidRepository;

import com.example.gestionAchat.repository.pm.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import static com.example.gestionAchat.web.rest.AuthentificationResource.varGA;
public class  JWTAutorizationFilter extends OncePerRequestFilter {

       @Autowired
       UserRepository userRepository;
        @Autowired
        AclSidRepository aclSidRepository;

      @Override
        protected void doFilterInternal(
                HttpServletRequest request,
                HttpServletResponse response,
                FilterChain chain) throws ServletException, IOException {

            response.addHeader("Access-Control-Allow-Origin", "*");
            response.addHeader("Access-Control-Allow-Methods", "GET,POST,PATCH,DELETE,PUT,OPTIONS");
            response.addHeader("Access-Control-Allow-Headers",
                    "Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Contol-Request-Headers,authorization");
          response.addHeader("Access-Control-Expose-Headers","Access-Controle-Allow-Origin, Access-Controle-Allow-Credentials, authorization");

          response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");


            if (request.getMethod().equals("OPTIONS")) {

                response.setStatus(HttpServletResponse.SC_OK);
            }


            String jwt = request.getHeader(SecurityConstants.HEADER_STRING);


            if (jwt == null || !jwt.startsWith(SecurityConstants.TOCKEN_PREFIX))
            {


                chain.doFilter(request, response);

                return;
            }

            Claims claims = Jwts.parser()
                    .setSigningKey(SecurityConstants.SECRET)
                    .parseClaimsJws(jwt.replace(SecurityConstants.TOCKEN_PREFIX, ""))
                    .getBody();

            String listeRoles = claims.get("roles").toString();

        String username = claims.getSubject().toString();
          //      String username= listeRoles.substring(listeRoles.lastIndexOf("authority")+10,listeRoles.lastIndexOf("}") );
          //  System.out.println("username  " + username);


            ArrayList<Map<String, String>> roles = (ArrayList<Map<String, String>>) claims.get("roles");
            Collection<GrantedAuthority> authorities = new ArrayList<>();

//            if(!username.equals("SUPLOCALADMIN")) {
//                System.out.println("username  " + username);
//                this.myService.find(username);
//            }


 //         System.out.println("VarGA"+varGA);

        if(!username.equals("root") && (!username.equals("UserTalendMailbox")))
            {int index= varGA.indexOf(username.toLowerCase());

              //  System.out.println("DisplayName of username=="+varGA.get(index+1));


            authorities.add(new SimpleGrantedAuthority(varGA.get(index+1)));
                }




         /* User user= userRepository.findBySamaccountname(authentication.getName()).get();
            .setAuthenticated();authorities.add(new SimpleGrantedAuthority(user.getDisplayname()));*/
       //   System.out.println(userRepository.findAll().size());
            authorities.add(new SimpleGrantedAuthority("Admin_Acl"));

            roles.forEach((r -> {
               authorities.add(new SimpleGrantedAuthority(r.get("authority")));

            }));




//
//            System.out.println("******************My authorities******************");
//            for(int j=0; j<authorities.size(); j++)
//            {
//                System.out.println(((ArrayList<GrantedAuthority>) authorities).get(j));
//            }
//            System.out.println("**************************************************");

            UsernamePasswordAuthenticationToken authenticatedUser = new UsernamePasswordAuthenticationToken(username, null, authorities);

            SecurityContextHolder.getContext().setAuthentication(authenticatedUser);


           chain.doFilter(request, response);



        }
    }



