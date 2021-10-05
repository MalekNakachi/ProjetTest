package com.example.gestionAchat;

import com.example.gestionAchat.entities.*;
//import com.example.MaiManager.service.DocumentService;
import com.example.gestionAchat.service.pm.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.crypto.NoSuchPaddingException;
import java.security.NoSuchAlgorithmException;
import java.util.*;


@SpringBootApplication
@EnableScheduling

public class PurchasingManagerApplication extends SpringBootServletInitializer {

	@Value("${spring.ProfileManagerURL}")
	private  String ProfileManagerURL;

	@Value("${spring.ProfileUsername}")
	private  String ProfileUsername;

	@Value("${spring.ProfilePassword}")
	private  String ProfilePassword;

	@Value("${spring.ProfileApplication}")
	private  String ProfileApplication;



	public static ArrayList<String> DepDistinguishednames= new ArrayList<String>();
	//////////////////////////////////////////////////////////////////////////
	public static List ListeDepartements;

	public static ArrayList<String> listAuthorities = new ArrayList<String>();

	public static List usersList;


//
//	public static KeyGenerator keygenerator;
//	public static SecretKey desKey;
//	public static Cipher desCipher;


	@Bean
	public WebMvcConfigurer corsConfigurer() {

		return new WebMvcConfigurerAdapter(){
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("*");
			}
		};
	}


	public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException {
//		 keygenerator = KeyGenerator.getInstance("DES");
//		 desKey = keygenerator.generateKey();
//		desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
		SpringApplication.run(PurchasingManagerApplication.class, args);
	}


    @Bean
	CorsConfigurationSource corsConfigurationSource() {

		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("*"));
		//configuration.applyPermitDefaultValues();

		configuration.setAllowCredentials(true);
		configuration.addAllowedMethod("GET");
		configuration.addAllowedMethod("OPTIONS");
		configuration.addAllowedMethod("POST");
		configuration.addAllowedMethod("PUT");
		configuration.addAllowedMethod("DELETE");
		configuration.addAllowedMethod("PATCH");
		configuration.addAllowedHeader("Access-Control-Allow-Origin");
		configuration.addAllowedHeader("Authorization");
		configuration.addAllowedHeader("Content-type");
		configuration.setAllowedHeaders(Arrays.asList("Authorization","Access-Control-Allow-Origin"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration.applyPermitDefaultValues());

		return source;
	}


	@Bean
	@Order(Ordered.HIGHEST_PRECEDENCE)
	CharacterEncodingFilter characterEncodingFilter() {
		CharacterEncodingFilter filter = new CharacterEncodingFilter();
		filter.setEncoding("UTF-8");
		filter.setForceEncoding(true);
		return filter;
	}

/****************************************************************************************************/





	private ApplicationService applicationService;

	@Autowired
	PurchasingManagerApplication(ApplicationService applicationService) {
		this.applicationService = applicationService;

	}
	@Autowired
	private RoleService roleService;
	PurchasingManagerApplication(RoleService roleService) {
		this.roleService = roleService;

	}
	@Autowired
	private ProfileService profileService;
	@Autowired
	private UserService userService;
	@Autowired
	private UserAccessService userAccessService;
	@Value("${localapp}")
	private String alias;

	private final Logger log = LoggerFactory.getLogger(PurchasingManagerApplication.class);




//	@Override
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//		return application.sources(PurchasingManagerApplication.class);
//	}


    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
      return application.sources(SpringBootApplication.class);
    }

	//@Bean
	public void execut() {
		try {
			User u = userService.autosave("UserTalend");
			Application app = applicationService.autosave(alias);
			Role r1 = roleService.autosave("AppReader", app);
			Role r2 = roleService.autosave("AppWriter", app);
			Role r3 = roleService.autosave("AppUI", app);
			Role r4 = roleService.autosave("DepReader", app);
			Role r5 = roleService.autosave("DepWriter", app);
			Role r6 = roleService.autosave("DepUI", app);
			Role r7 = roleService.autosave("ProfileReader", app);
			Role r8 = roleService.autosave("ProfileWriter", app);
			Role r9 = roleService.autosave("ProfileUI", app);
			Role r10 = roleService.autosave("RoleReader", app);
			Role r11 = roleService.autosave("RoleWriter", app);
			Role r12 = roleService.autosave("RoleUI", app);
			Role r13 = roleService.autosave("UserReader", app);
			Role r14 = roleService.autosave("UserWriter", app);
			Role r15 = roleService.autosave("UserUI", app);
			Role r16 = roleService.autosave("AccessReader", app);
			Role r17 = roleService.autosave("AccessWriter", app);
			Role r18 = roleService.autosave("AccessUI", app);
			Role r19 = roleService.autosave("VarReader", app);
			Role r20 = roleService.autosave("VarWriter", app);
			Role r21 = roleService.autosave("VarUI", app);
			Role r22 = roleService.autosave("SysWriter", app);
			Role r23 = roleService.autosave("SysReader", app);
			Role r24 = roleService.autosave("SysUI", app);

			List<Role> liste1 = new ArrayList<>();
			liste1.add(r1);
			liste1.add(r2);
			liste1.add(r3);
			liste1.add(r4);
			liste1.add(r5);
			liste1.add(r6);
			liste1.add(r7);
			liste1.add(r8);
			liste1.add(r9);
			liste1.add(r10);
			liste1.add(r11);
			liste1.add(r12);
			liste1.add(r13);
			liste1.add(r14);
			liste1.add(r15);
			liste1.add(r16);
			liste1.add(r17);
			liste1.add(r18);
			liste1.add(r19);
			liste1.add(r20);
			liste1.add(r21);
			liste1.add(r22);
			liste1.add(r23);
			liste1.add(r24);

			List<Role> liste2 = new ArrayList<>();
			liste2.add(r1);
			liste2.add(r6);
			liste2.add(r16);
			liste2.add(r21);
			liste2.add(r7);
			liste2.add(r12);
			liste2.add(r3);
			liste2.add(r13);
			liste2.add(r18);
			liste2.add(r4);
			liste2.add(r9);
			liste2.add(r14);
			liste2.add(r19);
			liste2.add(r5);
			liste2.add(r10);
			liste2.add(r15);

			List<Role> liste3 = new ArrayList<>();
			liste3.add(r1);
			liste3.add(r16);
			liste3.add(r4);
			liste3.add(r19);
			liste3.add(r7);
			liste3.add(r10);
			liste3.add(r13);

			List<Role> liste4 = new ArrayList<>();
			liste4.add(r14);
			liste4.add(r5);

			List<Role> liste5 = new ArrayList<>();
			liste5.add(r2);
			liste5.add(r17);
			liste5.add(r5);
			liste5.add(r20);
			liste5.add(r8);
			liste5.add(r23);
			liste5.add(r11);
			liste5.add(r14);

			Profile p1 = profileService.autosave("TalendUser", app, liste4);
			profileService.autosave("Admin", app, liste1);
			profileService.autosave("UserAdmin", app, liste2);
			profileService.autosave("StandardUser", app, liste3);
			profileService.autosave("UserBack", app, liste5);

			userAccessService.autosave(p1, app, u);

		} catch (Exception e) {
			log.info("Error while creating default users");
		}
	}
}
