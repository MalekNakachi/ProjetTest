package com.example.gestionAchat.web.rest.pm;

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
import com.example.gestionAchat.entities.ConfigLdap;
import com.example.gestionAchat.repository.pm.LdapConfigRepository;
import com.example.gestionAchat.service.pm.dto.ConfigLdapCriteria;
import com.example.gestionAchat.service.pm.query.ConfigLdapQueryService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping({"/api"})
public class LdapConfigController {
	private static final Logger log;

	static {
		log = LoggerFactory.getLogger(LdapConfigController.class);
	}

	private final Device global;
	@Autowired
	private LdapConfigRepository Repository;

	@Autowired
	private ConfigLdapQueryService configLdapQueryService;

	public LdapConfigController() {
		this.global = new Device();

	}

	/**
	 * {@code GET  /config-ldaps} : get all the configLdaps.<br> <br>
	 * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to get all the configLdaps.
	 * @param pageable the pagination information.
	 * @param criteria the criteria which the requested entities should match.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of configLdaps in body.
	 */
	@GetMapping("/config-ldaps")
	public ResponseEntity<List<ConfigLdap>> getAllConfigLdaps(ConfigLdapCriteria criteria, Pageable pageable) {
		log.debug("REST request to get ConfigLdaps by criteria: {}", criteria);
		Page<ConfigLdap> page = configLdapQueryService.findByCriteria(criteria, pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * {@code GET  /config-ldaps/count} : count all the configLdaps.<br> <br>
	 * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to count all the configLdaps.
	 * @param criteria the criteria which the requested entities should match.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
	 */
	@GetMapping("/config-ldaps/count")
	public ResponseEntity<Long> countConfigLdaps(ConfigLdapCriteria criteria) {
		log.debug("REST request to count ConfigLdaps by criteria: {}", criteria);
		return ResponseEntity.ok().body(configLdapQueryService.countByCriteria(criteria));
	}

	/**
	 * {@code GET  /ConfigLdap} : all Config Ldap.<br> <br>
	 * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to all Config Ldap.
	 * @param request request param.
	 * @return List of ConfigLdap.
	 */
	@PreAuthorize("hasAuthority('SUPLOCALADMIN') or hasAuthority('SysReader') or hasAuthority('SysWriter')")

	@GetMapping({"/ConfigLdap"})
	public List<ConfigLdap> allConfigLdap(HttpServletRequest request) {
		List<ConfigLdap> all = Repository.findAll();
		List<ConfigLdap> ll = new ArrayList<>();
		String token = request.getHeader("Authorization");
		String body = global.DecodeJWT(token.substring(token.indexOf("ey")));
		String[] list = body.split("\"authority\":\"");
		List<String> l = new ArrayList<String>();
		for (int i = 1; i < list.length; ++i) {
			l.add(list[i].substring(0, body.split("\"authority\":\"")[i].length() - 4));
		}
		String[] prv = null;
		String[] prv2 = null;
		List<String> l2 = new ArrayList<String>();
		for (ConfigLdap u : all) {
			if ((u.getReader() != null && !u.getReader().isEmpty())
					|| (u.getAuthor() != null && !u.getAuthor().isEmpty())) {
				prv = u.getReader().split(",");
				prv2 = u.getAuthor().split(",");
			}
			for (String p : prv) {
				l2.add(p);
			}
			for (String p : prv2) {
				l2.add(p);
			}
			if (this.global.valid(l2, l)) {
				ll.add(u);
			}
		}
		return ll;
	}

	/**
	 * {@code GET  /ConfigLdap/{id}} : ConfigLdap by id.<br> <br>
	 * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to ConfigLdap by id.
	 * @param request request param.
	 * @param id id param.
	 * @return ConfigLdap.
	 */
	@PreAuthorize("hasAuthority('SUPLOCALADMIN') or hasAuthority('SysReader') or hasAuthority('SysWriter')")

	@GetMapping({"/ConfigLdap/{id}"})
	public ConfigLdap ConfigLdapbyid(HttpServletRequest request, @PathVariable int id) {
		String token = request.getHeader("Authorization");
		String body = this.global.DecodeJWT(token.substring(token.indexOf("ey")));
		String[] list = body.split("\"authority\":\"");
		String username = body.substring(body.indexOf("\":\"") + 3, body.indexOf("\","));
		List<String> l = new ArrayList<>();
		for (int i = 1; i < list.length; ++i) {
			l.add(list[i].substring(0, body.split("\"authority\":\"")[i].length() - 4));
		}

		Optional<ConfigLdap> config = Repository.findById((long) id);

		String[] prv = null;
		String[] prv2 = null;
		List<String> l2 = new ArrayList<>();
		if ((config.get().getReader() != null && !config.get().getReader().isEmpty())
				|| (config.get().getAuthor() != null && !config.get().getAuthor().isEmpty())) {
			prv = config.get().getReader().split(",");
			prv2 = config.get().getAuthor().split(",");
			for (String p : prv) {
				l2.add(p);
			}
			for (String p : prv2) {
				l2.add(p);
			}
		}
		if (this.global.valid(l2, l)) {
			return config.get();
		}
		return null;
	}

	/**
	 * {@code POST  /ConfigLdap} : add ConfigLdap.<br> <br>
	 * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to add ConfigLdap.
	 * @param request request param.
	 * @param configLdap configLdap param.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of expands in body.
	 * @throws IOException IOException is thrown.
	 */
	@PreAuthorize("hasAuthority('SUPLOCALADMIN') or hasAuthority('SysWriter') ")

	@PostMapping({"/ConfigLdap"})
	public ResponseEntity<ConfigLdap> addConfigLdap(HttpServletRequest request, @RequestBody ConfigLdap configLdap)
			throws IOException {

		String token = request.getHeader("Authorization");
		String body = this.global.DecodeJWT(token.substring(token.indexOf("ey")));
		String[] list = body.split("\"authority\":\"");
		String username = body.substring(body.indexOf("\":\"") + 3, body.indexOf("\","));
		List<String> l = new ArrayList<String>();
		for (int i = 1; i < list.length; ++i) {
			l.add(list[i].substring(0, body.split("\"authority\":\"")[i].length() - 4));
		}
//		List<ConfigLdap> all = Repository.findAll();
//		int j;
//		for (j = 0; j < all.size() && !all.get(j).getName().equals(configLdap.getName()); ++j) {
//		}
//		if (j < all.size()) {
//			Long id = Repository.findByName(configLdap.getName()).getId();
//			return upConfigLdap(request, id.intValue(), configLdap);
//		}
		String A = configLdap.getAuthor();
		String R = configLdap.getReader();
		if (A == null) {
			A = "SysWriter,SUPLOCALADMIN";
		} else {
			String[] aa = A.split(",");
			Boolean z = false;
			Boolean e = false;
			for (String a : aa) {
				if (a.equals("SysWriter")) {
					z = true;
				}
				if (a.equals("SUPLOCALADMIN")) {
					e = true;
				}
			}
			if (!z) {
				A += ",SysWriter";
			}
			if (!e) {
				A += ",SUPLOCALADMIN";
			}
		}
		if (R == null) {
			R = "SysReader";
		} else {
			String[] aa = R.split(",");
			Boolean z = false;
			for (String a2 : aa) {
				if (a2.equals("SysReader")) {
					z = true;
				}
			}
			if (!z) {
				R += ",SysReader";
			}
		}
		configLdap.setAuthor(A);
		configLdap.setReader(R);

		ConfigLdap result = Repository.save(configLdap);
		LocalDate date = LocalDate.now();

		return ResponseEntity.ok(result);
	}

	/**
	 * {@code PUT  /ConfigLdap/{id}} : upConfigLdap.<br> <br>
	 * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to upConfigLdap.
	 * @param request request param.
	 * @param configLdap configLdap param.
	 * @param id id param.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of expands in body.
	 * @throws IOException IOException is thrown.
	 */
	@PreAuthorize("hasAuthority('SUPLOCALADMIN') or hasAuthority('SysWriter')")
	@PutMapping({"/ConfigLdap/{id}"})
	public ResponseEntity<ConfigLdap> upConfigLdap(HttpServletRequest request, @PathVariable int id,
												   @RequestBody ConfigLdap configLdap) throws IOException {
		String token = request.getHeader("Authorization");
		String body = this.global.DecodeJWT(token.substring(token.indexOf("ey")));
		String[] list = body.split("\"authority\":\"");
		String username = body.substring(body.indexOf("\":\"") + 3, body.indexOf("\","));
		List<String> l = new ArrayList<String>();
		for (int i = 1; i < list.length; ++i) {
			l.add(list[i].substring(0, body.split("\"authority\":\"")[i].length() - 4));
		}

		Optional<ConfigLdap> std = this.Repository.findById((long) id);
		ConfigLdap ldap = std.get();
		String[] prv = null;
		List<String> l2 = new ArrayList<String>();
		if (ldap.getAuthor() != null && !ldap.getAuthor().isEmpty()) {
			prv = ldap.getAuthor().split(",");
		}
		for (String p : prv) {
			l2.add(p);
		}
		if (this.global.valid(l2, l)) {
			LocalDate date = LocalDate.now();
			String A = configLdap.getAuthor();
			String R = configLdap.getReader();
			if (A == null) {
				A = "SysWriter,SUPLOCALADMIN";
			} else {
				String[] aa = A.split(",");
				Boolean z = false;
				Boolean e = false;
				for (String a : aa) {
					if (a.equals("SysWriter")) {
						z = true;
					}
					if (a.equals("SUPLOCALADMIN")) {
						e = true;
					}
				}
				if (!z) {
					A += ",SysWriter";
				}
				if (!e) {
					A += ",SUPLOCALADMIN";
				}
			}
			if (R == null) {
				R = "SysReader";
			} else {
				String[] aa = R.split(",");
				Boolean z = false;
				for (String a2 : aa) {
					if (a2.equals("SysReader")) {
						z = true;
					}
				}
				if (!z) {
					R += ",SysReader";
				}
			}
			ldap.setAuthor(A);
			ldap.setReader(R);
			ldap.setBase(configLdap.getBase());
			ldap.setPassword(configLdap.getPassword());
			ldap.setUrl(configLdap.getUrl());
			ldap.setUserDn(configLdap.getUserDn());
			ConfigLdap result = Repository.save(ldap);

			return ResponseEntity.ok(result);

		}
		return (ResponseEntity<ConfigLdap>) ResponseEntity.badRequest();
	}

	/**
	 * {@code DELETE  /ConfigLdap/{id}} : delete Config Ldap By id.<br> <br>
	 * <b>AUTHORIZATIONS : </b> <br> <br> ******************** : is authorized to delete Config Ldap By id.
	 * @param request request param.
	 * @param id request param.
	 * @throws UnknownHostException UnknownHostException is thrown.
	 */
@PreAuthorize("hasAuthority('SUPLOCALADMIN') or hasAuthority('SysWriter')")
	@DeleteMapping({"/ConfigLdap/{id}"})
	public void deleteConfigLdap(HttpServletRequest request, @PathVariable int id) throws UnknownHostException {
		String token = request.getHeader("Authorization");
		String body = this.global.DecodeJWT(token.substring(token.indexOf("ey")));
		String[] list = body.split("\"authority\":\"");
		String username = body.substring(body.indexOf("\":\"") + 3, body.indexOf("\","));
		List<String> l = new ArrayList<String>();
		for (int i = 1; i < list.length; ++i) {
			l.add(list[i].substring(0, body.split("\"authority\":\"")[i].length() - 4));
		}
		ConfigLdap std = this.Repository.findById((long) id).get();

		String[] prv = null;
		List<String> l2 = new ArrayList<String>();
		if (std.getAuthor() != null && !std.getAuthor().isEmpty()) {
			prv = std.getAuthor().split(",");
		}
		for (String p : prv) {
			l2.add(p);
		}
		if (this.global.valid(l2, l)) {
			this.Repository.deleteById((long) id);
		}
	}
}
