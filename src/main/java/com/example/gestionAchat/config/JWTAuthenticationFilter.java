package com.example.gestionAchat.config;

import com.example.gestionAchat.entities.dto.AuthUser;
import com.example.gestionAchat.service.pm.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private UserService userService;
    private AuthenticationManager authenticationManager;
    private String user;

    public String getUser() {
        return user;
    }

	public void setUser(String user) {
		this.user = user;
	}

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager, ApplicationContext ctx) {
		super();
		this.authenticationManager = authenticationManager;
		this.userService = ctx.getBean(UserService.class);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		AuthUser appUser = new AuthUser();
		try {
			appUser.setUsername(request.getParameter("username"));
			appUser.setPassword(request.getParameter("password"));
			appUser.setApplication(request.getParameter("application"));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		setUser(appUser.getUsername());

		userService.setapp(appUser.getApplication());
		Boolean authenticated = false;
		try {
			authenticated = userService.authentify(appUser.getUsername(), appUser.getPassword());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String access = null;
		if (authenticated == true)
			access = "authorise";

		return authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(appUser.getUsername(), access));
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		User springUser = (User) authResult.getPrincipal();
		String jwtT = Jwts.builder().setSubject(springUser.getUsername())
				.setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS256, SecurityConstants.SECRET)
				.claim("roles", springUser.getAuthorities()).compact();
		response.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOCKEN_PREFIX + jwtT);
	}
}
